package com.marcosrz.mmap.command.commands;

import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.gui.Menu;

public class AyudaCmd implements Command{

	public Maquina estado;
	public Menu menu;
	
	public AyudaCmd(Maquina estado){
		this.estado = estado;
		this.menu = Menu.getInstance();
	}
	
	@Override
	public Maquina execute() {
		
		menu.print("Ayuda - Opciones:\n");
		menu.print("Juego:	Inicia una instancia de juego.");
		menu.print("Averia: Simula una avería en la máquina.");
		menu.print("Info:	Muestra la informacion de estado de la máquina.");
		menu.print("Salir:	Desactiva de forma segura la máquina.");
		menu.print("Ayuda:	Muestra esta lista.");
		
		return estado;
	}

}
