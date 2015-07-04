package com.marcosrz.mmap.command.commands;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.marcosrz.mmap.api.MMAPApi;
import com.marcosrz.mmap.api.impl.MMAPApiImpl;
import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Actividad;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.gui.Menu;
import com.marcosrz.mmap.log.MMAPLogger;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;

public class JuegoCmd implements Command {

	private Menu menu;
	private MMAPLogger logger;
	private MMAPApi api;
	private Maquina estado;

	public JuegoCmd(Maquina estado) throws IOException, MMAPSecurityException {
		this.logger = MMAPLogger.getInstance();
		this.estado = estado;
		this.menu = Menu.getInstance();
		this.api = MMAPApiImpl.getInstance();
	}

	public Maquina execute() {

		if (isAveriada()) {
			menu.print("La maquina esta averiada :(");
			return estado;
		}

		int partidas = Integer.parseInt(menu.requireData("partidas"));

		// Jugamos y el juego crea una actividad
		Actividad actividad = jugar(estado, partidas);

		// Actualizamos el estado con los valores de la actividad
		estado.Entradas = estado.Entradas.add(actividad.Entradas);
		estado.Salidas = estado.Salidas.add(actividad.Salidas);

		// Enviamos la actividad a MMAP
		api.createActividad(actividad);
		api.updateMaquina(estado);
		
		menu.print("Actividad creada: " + actividad + "\n");
		
		return estado;

	}

	private boolean isAveriada() {
		return this.estado.Estado == 1;
	}

	// Lógica de juego. Implementacion sencilla para simular.
	private Actividad jugar(Maquina estado, int partidas) {

		Actividad actividad = new Actividad();

		actividad.setInicio(new Date());

		double entradas = Math.random() * partidas;
		double salidas = 0;

		logger.info("Juego comenzado!");

		for (int i = 0; i < partidas; i++) {

			long x = Math.round(Math.random() * 8);
			long y = Math.round(Math.random() * 8);
			long z = Math.round(Math.random() * 8);

			System.out.print("[Partida #" + i + "]");

			if (x == y && y == z) {
				salidas += Math.random() * 50;
				System.out
						.println("[" + x + "][" + y + "][" + z + "] PREMIO! ");
			} else {
				System.out.println("[" + x + "][" + y + "][" + z
						+ "] FRACASO! ");
			}
		}

		logger.info("Juego finalizado!");

		actividad.setFin(new Date());
		actividad.Entradas = new BigDecimal(entradas);
		actividad.Salidas = new BigDecimal(salidas);

		return actividad;
	}

	@Override
	public String toString() {
		return "JuegoCmd";
	}

}
