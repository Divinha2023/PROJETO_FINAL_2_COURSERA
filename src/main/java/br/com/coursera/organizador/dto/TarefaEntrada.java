package br.com.coursera.organizador.dto;

import java.time.LocalDate;

import br.com.coursera.organizador.model.Prioridade;
import br.com.coursera.organizador.model.StatusTarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TarefaEntrada(
		@NotBlank @Size(max = 90) String titulo,
		@NotBlank @Size(max = 500) String descricao,
		@NotBlank @Size(max = 80) String responsavel,
		@NotNull StatusTarefa status,
		@NotNull Prioridade prioridade,
		LocalDate prazo,
		String[] tags) {
}
