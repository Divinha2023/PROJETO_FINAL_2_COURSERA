package br.com.coursera.organizador.web;

import java.time.LocalDate;

import br.com.coursera.organizador.dto.TarefaEntrada;
import br.com.coursera.organizador.model.Prioridade;
import br.com.coursera.organizador.model.StatusTarefa;
import br.com.coursera.organizador.model.Tarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TarefaFormulario {

	@NotBlank
	@Size(max = 90)
	private String titulo;

	@NotBlank
	@Size(max = 500)
	private String descricao;

	@NotBlank
	@Size(max = 80)
	private String responsavel;

	@NotNull
	private StatusTarefa status = StatusTarefa.PENDENTE;

	@NotNull
	private Prioridade prioridade = Prioridade.MEDIA;

	private LocalDate prazo;

	private String tags;

	public static TarefaFormulario de(Tarefa tarefa) {
		TarefaFormulario formulario = new TarefaFormulario();
		formulario.setTitulo(tarefa.getTitulo());
		formulario.setDescricao(tarefa.getDescricao());
		formulario.setResponsavel(tarefa.getResponsavel());
		formulario.setStatus(tarefa.getStatus());
		formulario.setPrioridade(tarefa.getPrioridade());
		formulario.setPrazo(tarefa.getPrazo());
		formulario.setTags(String.join(", ", tarefa.getTags()));
		return formulario;
	}

	public TarefaEntrada toEntrada() {
		String[] etiquetas = tags == null || tags.isBlank() ? new String[0] : tags.split(",");
		return new TarefaEntrada(titulo, descricao, responsavel, status, prioridade, prazo, etiquetas);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public LocalDate getPrazo() {
		return prazo;
	}

	public void setPrazo(LocalDate prazo) {
		this.prazo = prazo;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
