package br.com.coursera.organizador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import br.com.coursera.organizador.model.Prioridade;
import br.com.coursera.organizador.model.StatusTarefa;
import br.com.coursera.organizador.model.Tarefa;

public record TarefaSaida(
		Long id,
		String titulo,
		String descricao,
		String responsavel,
		StatusTarefa status,
		Prioridade prioridade,
		LocalDate prazo,
		Set<String> tags,
		boolean atrasada,
		LocalDateTime criadaEm,
		LocalDateTime atualizadaEm) {

	public static TarefaSaida de(Tarefa tarefa) {
		return new TarefaSaida(
				tarefa.getId(),
				tarefa.getTitulo(),
				tarefa.getDescricao(),
				tarefa.getResponsavel(),
				tarefa.getStatus(),
				tarefa.getPrioridade(),
				tarefa.getPrazo(),
				Set.copyOf(tarefa.getTags()),
				tarefa.isAtrasada(),
				tarefa.getCriadaEm(),
				tarefa.getAtualizadaEm());
	}
}
