package com.marcosrz.mmap.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notificacion {
	
	private long ID;
	public Date Fecha;
	public String Descripcion;
	
	public Notificacion(){}
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "Notificacion [ID=" + ID + ", Fecha=" + Fecha + ", Descripcion="
				+ Descripcion + "]";
	}

}
