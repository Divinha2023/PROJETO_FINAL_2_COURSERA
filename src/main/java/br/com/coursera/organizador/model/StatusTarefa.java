package br.com.coursera.organizador.model;

public enum StatusTarefa {
	PENDENTE("Pendente"),
	EM_ANDAMENTO("Em andamento"),
	CONCLUIDA("Concluída");

	private final String descricao;

	StatusTarefa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
