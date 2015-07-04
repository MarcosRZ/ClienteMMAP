package com.marcosrz.mmap.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.marcosrz.mmap.command.Command;
import com.marcosrz.mmap.command.commands.*;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.log.MMAPLogger;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;

public class Menu {

	private BufferedReader in;
	private PrintStream out;
	private static Menu instance;
	private MMAPLogger logger;

	public static Menu getInstance() {
		if (instance == null)
			instance = new Menu(System.in, System.out);
		return instance;
	}

	public Menu(InputStream in, PrintStream out) {

		this.logger = MMAPLogger.getInstance();
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = out;

	}

	public Command getCommand(Maquina estado) throws IOException,
			MMAPSecurityException {

		String strCommand = requireData("command");

		switch (strCommand) {

		case "juego":
			return new JuegoCmd(estado);

		case "averia":
			return new AveriaCmd(estado);
			
		case "info":
			return new InfoCmd(estado);
			
		case "ayuda":
			return new AyudaCmd(estado);

		case "salir":
			return new SalirCmd(estado);

		default:
			return new DesconocidoCmd(estado);

		}
	}

	public String requireData(String text) {

		String data = null;

		try {

			out.print("Insert " + text + ": ");
			data = in.readLine();

		} catch (IOException e) {
			this.logger.error("IO Error requiring data. " + e.getMessage());
		}

		return data;

	}

	public void print(String data) {
		out.println(data);
	}
	
	public void printHeader(){
		this.print("--------------------------------------");
		this.print("     MMAP Client v1.0 - Main menu     ");
		this.print("--------------------------------------");
	}
}
