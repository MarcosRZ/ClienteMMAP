package com.marcosrz.mmap;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.command.CommandExecuttor;
import com.marcosrz.mmap.command.commands.GetEstadoCmd;
import com.marcosrz.mmap.command.commands.ActualizarEstadoCmd;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.entities.enums.EstadoMaquina;
import com.marcosrz.mmap.gui.Menu;
import com.marcosrz.mmap.log.MMAPLogger;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;

public class App {

	private CommandExecuttor executtor;
	private Maquina estado;
	private ReadWriteLock rwlock;
	private MMAPLogger logger;
	private Menu menu;
	private ThreadActualizarEstado actualizarEstado;

	public App() {

		this.logger = MMAPLogger.getInstance();
		this.executtor = new CommandExecuttor();
		this.rwlock = new ReentrantReadWriteLock();
		this.menu = new Menu(System.in, System.out);

	}

	public static void main(String[] args) {
		new App().run();
	}

	private void run() {

		try {

			iniciar();

			buclePrincipal();

			apagar();

		} catch (IOException e) {
			System.err
					.println("Algo ha ido mal. Mas info en el fichero de log.");
			logger.error("Error al cargar el fichero de configuración. "
					+ e.getMessage());
		} catch (MMAPSecurityException e) {
			System.err
					.println("Algo ha ido mal. Mas info en el fichero de log.");
			logger.error("Error al crear el Client SSL. " + e.getMessage());

		} catch (Exception e) {
			System.err
					.println("Algo ha ido mal. Mas info en el fichero de log.");
			logger.error("Error interno. " + e);
		}

	}

	private void iniciar() throws IOException, MMAPSecurityException {

		// Recuperamos el estado de MMAP
		estado = executtor.execute(new GetEstadoCmd());

		// Activamos la maquina
		estado.Estado = EstadoMaquina.ACTIVO;

		// Lanzar hilo, actualizar cada 3s
		this.actualizarEstado = new ThreadActualizarEstado(30000);

		actualizarEstado.start();

		logger.info("MMAP Machine Client v1.0 iniciado");

	}

	/**
	 * Leer el siguiente comando de consola y ejecutar.
	 * 
	 * @throws IOException
	 * @throws MMAPSecurityException
	 */
	private void buclePrincipal() throws IOException, MMAPSecurityException {

		do {
			
			menu.printHeader();
			
			Command cmd = menu.getCommand(this.estado);
			
			setEstado(executtor.execute(cmd));

		} while (getEstado().Estado != EstadoMaquina.APAGADO);
		
	}

	/**
	 * Configura el estado de la máquina a 1 (KO) y envía señal de STOP al hilo
	 * de actualización de estado
	 */
	private void apagar() {
		this.actualizarEstado.stopSignal();
		menu.print("Apagado.");
		logger.info("\nApagado controlado del sistema.");
	}

	/**
	 * Escritura concurrentemente segura del estado
	 * 
	 * @param estado
	 *            Estado de la máquina
	 */
	private void setEstado(Maquina estado) {
		rwlock.writeLock().lock();
		this.estado = estado;
		rwlock.writeLock().unlock();
	}

	/**
	 * Lectura concurrentemente segura del estado
	 * 
	 * @return Estado actual de la máquina
	 */
	private Maquina getEstado() {

		rwlock.readLock().lock();
		Maquina estadoActual = estado;
		rwlock.readLock().unlock();

		return estadoActual;
	}

	/**
	 * Hilo que envía el estado de la máquina a la plataforma MMAP de forma
	 * periódica durante la ejecución
	 * 
	 * @author Marcos
	 */
	private class ThreadActualizarEstado extends Thread {

		private long period;
		private boolean stopSignal;

		/**
		 * @param period
		 *            Período en milisegundos de envío del estado
		 */
		public ThreadActualizarEstado(long period) {
			this.period = period;
		}

		/**
		 * Método que detiene de forma segura el Hilo. Activa el flag de parada
		 * provocando el fin del bucle principal (ver método run())
		 * Adicionalmente, envía la señal interrupt para despertar el hilo del
		 * Sleep().
		 */
		public void stopSignal() {
			this.stopSignal = true;
			this.interrupt();
		}

		@Override
		public void run() {
			logger.info("Hilo de actualizacion iniciado con período " + period
					+ " ms.");
			while (!stopSignal) {

				try {
					actualizarEstado();
					logger.info("Acutalizacion de estado satisfactoria.");
					sleep(period);
				} catch (InterruptedException e) {
					logger.info("El hilo de actualizacion ha recibido la señal de parada. Enviando el estado al servidor para terminar.");
					actualizarEstado();
				}

			}
			logger.info("El hilo de actualizacion ha terminado correctamente.");
		}

		/**
		 * Crea y envía al executtor el comando de actualizar el estado (Enviar
		 * el estado al servidor)
		 */
		private void actualizarEstado() {
			try {
				executtor.execute(new ActualizarEstadoCmd(getEstado()));
				logger.info("Acutalizacion de estado satisfactoria.");
			} catch (IOException e) {
				logger.warning("Imposible actualizar estado. " + e.getMessage());
				actualizarEstado();
			} catch (MMAPSecurityException e) {
				logger.error("Error de SSL. " + e.getMessage());
				actualizarEstado();
			}
		}
	}

}
