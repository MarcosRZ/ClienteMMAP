package com.marcosrz.mmap.command;

import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.log.MMAPLogger;

public class CommandExecuttor {

	private MMAPLogger logger;
	
	public CommandExecuttor(){
		this.logger = MMAPLogger.getInstance();
	}
	
	public Maquina execute(Command c){
		logger.info("Ejecutando comando: " + c);
		Maquina estado = c.execute();
		logger.info("Fin del comando: " + c);
		return estado;
	}
}
