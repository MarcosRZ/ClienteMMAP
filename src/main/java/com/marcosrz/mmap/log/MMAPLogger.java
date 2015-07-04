package com.marcosrz.mmap.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

public class MMAPLogger {

	private Properties properties;
	private static String CONF_FILE = "config.properties";
	private static MMAPLogger logger;
	private String logfile;

	public static MMAPLogger getInstance() {
		if (logger == null) {
			logger = new MMAPLogger();
		}
		return logger;
	}

	private MMAPLogger() {
		try {
			// Buscamos la ruta al fichero de log en el properties
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(CONF_FILE);
			properties = new Properties();

			properties.load(inputStream);
			
			// Obtenemos la ruta del log a partir del properties.
			logfile = properties.getProperty("LOG_FILE");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void info(String message) {
		write("INFO", message);
	}

	public void warning(String message) {
		write("WARNING", message);
	}

	public void error(String message) {
		write("ERROR", message);
	}

	private synchronized void write(String level, String message) {

		FileWriter out = null;
		PrintWriter pw = null;
		try {
			out = new FileWriter(this.logfile, true);
			pw = new PrintWriter(out);
			pw.append("[" + level + "]\t[" + new Date() + "]: " + message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (out != null) {
					out.close();
				}
				
				if (pw != null){
					pw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
