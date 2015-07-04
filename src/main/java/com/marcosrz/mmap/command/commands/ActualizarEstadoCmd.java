package com.marcosrz.mmap.command.commands;

import java.io.IOException;

import com.marcosrz.mmap.api.MMAPApi;
import com.marcosrz.mmap.api.impl.MMAPApiImpl;
import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;

public class ActualizarEstadoCmd implements Command {

	private MMAPApi api;
	private Maquina estado;

	public ActualizarEstadoCmd(Maquina estado) throws IOException,
			MMAPSecurityException {
		this.api = MMAPApiImpl.getInstance();
		this.estado = estado;
	}

	@Override
	public Maquina execute() {
		api.updateMaquina(estado);
		return estado;
	}

	@Override
	public String toString() {
		return "ActualizarEstadoCmd";
	}

}
