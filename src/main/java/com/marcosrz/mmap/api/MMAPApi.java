package com.marcosrz.mmap.api;

import com.marcosrz.mmap.entities.Actividad;
import com.marcosrz.mmap.entities.Maquina;
import com.marcosrz.mmap.entities.Notificacion;

public interface MMAPApi {

	// Actividades
	public Actividad getActividad(long id);
	public void createActividad(Actividad actividad);
	
	// Notificaciones
	public Notificacion getNotificacion(long id);
	public void createNotificacion(Notificacion notificacion);
	
	// Maquinas
	public Maquina getEstado();
	public void updateMaquina(Maquina maquina);
}
