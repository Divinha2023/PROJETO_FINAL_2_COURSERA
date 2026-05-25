package br.com.coursera.organizador.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.coursera.organizador.model.StatusTarefa;

public record ResumoPainel(
		long total,
		Map<StatusTarefa, Long> tarefasPorStatus,
		Map<String, Long> tarefasPorResponsavel,
		Set<String> etiquetas,
		List<TarefaSaida> proximasDaFila) {
}
