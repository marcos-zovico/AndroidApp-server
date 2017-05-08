package br.edu.devmedia.util;

import com.google.gson.annotations.SerializedName;

public class PagtoCliente {

	@SerializedName("ammout")
	private double quantia;

	@SerializedName("currency_code")
	private String moeda;

	public double getQuantia() {
		return quantia;
	}

	public void setQuantia(double quantia) {
		this.quantia = quantia;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

}
