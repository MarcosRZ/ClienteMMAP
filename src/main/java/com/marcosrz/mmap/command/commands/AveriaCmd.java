package com.marcosrz.mmap.command.commands;

import java.io.IOException;
import java.util.Date;

import com.marcosrz.mmap.api.MMAPApi;
import com.marcosrz.mmap.api.impl.MMAPApiImpl;
import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.entities.Notificacion;
import com.marcosrz.mmap.entities.enums.EstadoMaquina;
import com.marcosrz.mmap.gui.Menu;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;

public class AveriaCmd implements Command {

	private Menu menu;
	private Maquina estado;
	private MMAPApi api;

	public AveriaCmd(Maquina estado) throws IOException, MMAPSecurityException {
		this.estado = estado;
		this.api = MMAPApiImpl.getInstance();
		this.menu = Menu.getInstance();

	}

	@Override
	public Maquina execute() {

		this.estado.Estado = EstadoMaquina.AVERIA;

		Notificacion n = new Notificacion();

		n.Descripcion = this.menu.requireData("description");
		n.Fecha = new Date();

		api.createNotificacion(n);

		api.updateMaquina(estado);

		return estado;
	}
	
	public String toString(){
		return "AveríaCmd";
	}

}
