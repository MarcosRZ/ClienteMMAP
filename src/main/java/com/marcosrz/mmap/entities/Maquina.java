package com.marcosrz.mmap.entities;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Maquina {
	
	private long ID;
    public int Estado;
    public String Nombre;
    public BigDecimal Entradas;
    public BigDecimal Salidas;
    public BigDecimal Cambio;
    
    public Maquina(){}
    
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public int getEstado() {
		return Estado;
	}
	public void setEstado(int estado) {
		Estado = estado;
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
	public BigDecimal getCambio() {
		return Cambio;
	}
	public void setCambio(BigDecimal cambio) {
		Cambio = cambio;
	}
	@Override
	public String toString() {
		return "Maquina [ID=" + ID + ", Estado=" + Estado + ", Entradas="
				+ Entradas + ", Salidas=" + Salidas + ", Cambio=" + Cambio
				+ "]";
	}
    
}
