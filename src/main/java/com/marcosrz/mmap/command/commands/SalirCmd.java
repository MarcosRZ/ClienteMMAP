package com.marcosrz.mmap.command.commands;

import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.entities.enums.EstadoMaquina;
import com.marcosrz.mmap.gui.Menu;

public class SalirCmd implements Command{

	public Maquina estado;
	public Menu menu;
	
	public SalirCmd(Maquina estado){
		this.estado = estado;
		this.menu = Menu.getInstance();
	}
	
	@Override
	public Maquina execute() {
		
		this.estado.Estado = EstadoMaquina.APAGADO;
		
		menu.print("Terminando todos los procesos...");
		
		return this.estado;
	}
	
	

}
