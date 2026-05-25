package br.com.divan.organizador.model;

public enum Prioridade {
	BAIXA(1, "Baixa"),
	MEDIA(2, "Média"),
	ALTA(3, "Alta"),
	URGENTE(4, "Urgente");

	private final int peso;
	private final String descricao;

	Prioridade(int peso, String descricao) {
		this.peso = peso;
		this.descricao = descricao;
	}

	public int getPeso() {
		return peso;
	}

	public String getDescricao() {
		return descricao;
	}
}
