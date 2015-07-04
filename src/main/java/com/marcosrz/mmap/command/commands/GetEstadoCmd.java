package com.marcosrz.mmap.command.commands;

import java.io.IOException;

import com.marcosrz.mmap.api.MMAPApi;
import com.marcosrz.mmap.api.impl.MMAPApiImpl;
import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;

public class GetEstadoCmd implements Command{

	MMAPApi api;
	
	public GetEstadoCmd() throws IOException, MMAPSecurityException {
		this.api = MMAPApiImpl.getInstance();
	}
	
	@Override
	public Maquina execute() {
		return api.getEstado();
	}
	
	@Override
	public String toString() {
		return "GetEstadoCmd";
	}

}
