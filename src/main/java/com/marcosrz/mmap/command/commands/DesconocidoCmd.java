package com.marcosrz.mmap.command.commands;

import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.gui.Menu;

/**
 * @author Marcos
 *
 *         Comando generado cuando se introduce una opcion que no está en la
 *         lista.
 * 
 *         Retorna el estado sin variacion.
 * 
 */
public class DesconocidoCmd implements Command {

	private Maquina estado;
	private Menu menu;

	public DesconocidoCmd(Maquina estado) {
		this.estado = estado;
		this.menu = Menu.getInstance();
	}

	@Override
	public Maquina execute() {
		menu.print("Unknown command!");
		// El estado permanece igual
		return this.estado;
	}

	@Override
	public String toString() {
		return "DesconocidoCmd";
	}

}
