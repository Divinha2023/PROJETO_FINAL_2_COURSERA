package br.com.divan.organizador.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Tarefa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 90)
	@Column(nullable = false, length = 90)
	private String titulo;

	@NotBlank
	@Size(max = 500)
	@Column(nullable = false, length = 500)
	private String descricao;

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	private String responsavel;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusTarefa status = StatusTarefa.PENDENTE;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Prioridade prioridade = Prioridade.MEDIA;

	private LocalDate prazo;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tarefa_tags", joinColumns = @JoinColumn(name = "tarefa_id"))
	@Column(name = "tag", nullable = false, length = 40)
	private Set<String> tags = new LinkedHashSet<>();

	@Column(nullable = false, updatable = false)
	private LocalDateTime criadaEm;

	@Column(nullable = false)
	private LocalDateTime atualizadaEm;

	@PrePersist
	void antesDeCriar() {
		LocalDateTime agora = LocalDateTime.now();
		criadaEm = agora;
		atualizadaEm = agora;
	}

	@PreUpdate
	void antesDeAtualizar() {
		atualizadaEm = LocalDateTime.now();
	}

	public boolean isAtrasada() {
		return prazo != null && prazo.isBefore(LocalDate.now()) && status != StatusTarefa.CONCLUIDA;
	}

	public Long getId() {
		return id;
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

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public LocalDateTime getCriadaEm() {
		return criadaEm;
	}

	public LocalDateTime getAtualizadaEm() {
		return atualizadaEm;
	}
}
