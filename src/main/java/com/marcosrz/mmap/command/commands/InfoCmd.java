package com.marcosrz.mmap.command.commands;

import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.gui.Menu;

/**
 * Imprime el estado de actual de la máquina
 * 
 * @author Marcos
 *
 */
public class InfoCmd implements Command{

	private Maquina estado;
	private Menu menu;
	
	public InfoCmd(Maquina estado){
		this.estado = estado;
		this.menu = Menu.getInstance();
	}
	
	@Override
	public Maquina execute() {		
		
		menu.print("--------------------------------------");		
		menu.print("      INFORMACIÓN DE LA MÁQUINA"       );
		menu.print("--------------------------------------");
		menu.print("\tNombre: " + estado.Nombre);
		menu.print("\tEntradas: " + estado.Entradas + " €");
		menu.print("\tSalidas: " + estado.Salidas + " €");
		menu.print("\tCambio: " + estado.Cambio + " €");
		menu.print("\tEstado: " + estado.Estado);
		menu.print("--------------------------------------");
		
		return estado;
	}

}
