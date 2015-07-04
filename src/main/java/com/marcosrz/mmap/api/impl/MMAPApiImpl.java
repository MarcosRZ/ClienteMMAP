package com.marcosrz.mmap.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import com.marcosrz.mmap.api.MMAPApi;
import com.marcosrz.mmap.entities.Actividad;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.entities.Notificacion;
import com.marcosrz.mmap.log.MMAPLogger;
import com.marcosrz.mmap.security.SSLManager;
import com.marcosrz.mmap.security.exception.MMAPSecurityException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MMAPApiImpl implements MMAPApi {

	private Properties config;
	private Client client;
	private MMAPLogger logger;
	private String apiKey;

	private static MMAPApi instance;

	public static MMAPApi getInstance() throws IOException,
			MMAPSecurityException {
		if (instance == null) {
			instance = new MMAPApiImpl("config.properties");
		}
		return instance;
	}

	public MMAPApiImpl(String configPath) throws IOException,
			MMAPSecurityException {

		logger = MMAPLogger.getInstance();

		this.config = new Properties();

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(configPath);
		config.load(inputStream);

		this.apiKey = this.config.getProperty("API_KEY");

		this.client = SSLManager.getInstance().getSSLRestClient();

	}

	// GET api/ActividadesREST/:id
	public Actividad getActividad(long id) {

		logger.info("Get Actividad");

		WebResource webResource = client.resource(this.config
				.getProperty("API_URL")
				+ this.config.getProperty("API_RESOURCE_ACTIVIDAD") + id);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.header("ApiKey", this.apiKey).get(ClientResponse.class);

		handleResponse(response);

		Actividad result = response.getEntity(Actividad.class);
		response.close();

		return result;

	}

	// POST: api/ActividadesREST/
	public void createActividad(Actividad actividad) {

		// Client client = Client.create();
		logger.info("Create Actividad");

		WebResource webResource = client.resource(this.config
				.getProperty("API_URL")
				+ this.config.getProperty("API_RESOURCE_ACTIVIDAD"));

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.header("ApiKey", this.apiKey)
				.post(ClientResponse.class, actividad);

		handleResponse(response);

		// System.out.println("Actividad created successfully ! {" + actividad +
		// "}");

		response.close();

	}

	public Notificacion getNotificacion(long id) {

		// Client client = Client.create();
		logger.info("Get Notificacion");

		WebResource webResource = client.resource(this.config
				.getProperty("API_URL")
				+ this.config.getProperty("API_RESOURCE_NOTIFICACION") + id);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.header("ApiKey", this.apiKey).get(ClientResponse.class);

		handleResponse(response);

		Notificacion result = response.getEntity(Notificacion.class);

		response.close();

		return result;
	}

	public void createNotificacion(Notificacion notificacion) {

		// Client client = Client.create();
		logger.info("Create Notificacion");

		WebResource webResource = client.resource(this.config
				.getProperty("API_URL")
				+ this.config.getProperty("API_RESOURCE_NOTIFICACION"));

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.header("ApiKey", this.apiKey)
				.post(ClientResponse.class, notificacion);

		handleResponse(response);

		response.close();

		// System.out.println("Notificacion created successfully ! {" +
		// notificacion + "}");

	}

	// GET: api/MaquinasREST/apiKey:string
	public Maquina getEstado() {

		logger.info("Get Estado");

		String apiKey = this.apiKey;

		WebResource webResource = client.resource(this.config
				.getProperty("API_URL")
				+ this.config.getProperty("API_RESOURCE_MAQUINA") + apiKey);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.header("ApiKey", this.apiKey).get(ClientResponse.class);

		handleResponse(response);

		Maquina estado = response.getEntity(Maquina.class);

		response.close();
		
		return estado;

	}

	// GET: api/MaquinasREST
	public Maquina getMaquina(long id) {

		logger.info("Get Maquina");

		WebResource webResource = client.resource(this.config
				.getProperty("API_URL")
				+ this.config.getProperty("API_RESOURCE_MAQUINA") + id);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.header("ApiKey", this.apiKey).get(ClientResponse.class);

		handleResponse(response);

		Maquina result = response.getEntity(Maquina.class);

		response.close();

		return result;
	}

	// PUT: api/MaquinasREST/
	public void updateMaquina(Maquina maquina) {

		try {
			WebResource webResource = client.resource(this.config
					.getProperty("API_URL")
					+ this.config.getProperty("API_RESOURCE_MAQUINA") + maquina.getID());

			ClientResponse response = webResource
					.type(MediaType.APPLICATION_JSON)
					.header("ApiKey", this.apiKey)
					.put(ClientResponse.class, maquina);

			handleResponse(response);

			response.close();

		} catch (Exception e) {
			logger.warning("No se ha podido actualizar el estado de la máquina!");
		}

	}

	private void handleResponse(ClientResponse response) {

		if (response.getStatus() >= 500) {
			throw new RuntimeException("Server error: HTTP error code : "
					+ response.getStatus());
		}

		if (response.getStatus() >= 400) {
			throw new RuntimeException("Client error: HTTP error code : "
					+ response.getStatus());
		}
	}

}
