package com.marcosrz.mmap.entities;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Actividad {

	private long ID;
	public Date Inicio;
	public Date Fin;
	public BigDecimal Entradas;
	public BigDecimal Salidas;
	
	public Actividad () {}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public Date getInicio() {
		return Inicio;
	}

	public void setInicio(Date inicio) {
		Inicio = inicio;
	}

	public Date getFin() {
		return Fin;
	}

	public void setFin(Date fin) {
		Fin = fin;
	}

	public BigDecimal getEntradas() {
		return Entradas;
	}

	public void setEntradas(BigDecimal entradas) {
		Entradas = entradas;
	}

	public BigDecimal getSalidas() {
		return Salidas;
	}

	public void setSalidas(BigDecimal salidas) {
		Salidas = salidas;
	}

	@Override
	public String toString() {
		return "Actividad [Id=" + ID + ", Inicio=" + Inicio + ", Fin=" + Fin
				+ ", Entradas=" + Entradas + ", Salidas=" + Salidas + "]";
	}


}
