package br.edu.devmedia.entity;

public class Pagamento {

	private int id;

	private String pagtoPayPalId;

	private String estado;

	private Double valor;

	private String moeda;

	private Usuario usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPagtoPayPalId() {
		return pagtoPayPalId;
	}

	public void setPagtoPayPalId(String pagtoPayPalId) {
		this.pagtoPayPalId = pagtoPayPalId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
