package br.com.divan.organizador.model;

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
