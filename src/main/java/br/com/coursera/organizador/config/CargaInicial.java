package br.com.coursera.organizador.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.coursera.organizador.model.Prioridade;
import br.com.coursera.organizador.model.StatusTarefa;
import br.com.coursera.organizador.model.Tarefa;
import br.com.coursera.organizador.repository.TarefaRepository;

@Configuration
public class CargaInicial {

	@Bean
	CommandLineRunner carregarDados(TarefaRepository repository) {
		return args -> {
			if (repository.count() > 0) {
				return;
			}

			repository.save(tarefa(
					"Revisar README",
					"Conferir instruções de execução, endpoints e observações para o GitHub.",
					"Divan",
					StatusTarefa.EM_ANDAMENTO,
					Prioridade.ALTA,
					LocalDate.now().plusDays(2),
					"github", "documentacao"));

			repository.save(tarefa(
					"Testar API REST",
					"Validar criação, atualização, exclusão e tratamento de erros.",
					"Divan",
					StatusTarefa.PENDENTE,
					Prioridade.URGENTE,
					LocalDate.now().plusDays(1),
					"api", "spring"));

			repository.save(tarefa(
					"Organizar apresentação",
					"Separar os pontos principais do projeto para explicar aos professores.",
					"Equipe",
					StatusTarefa.PENDENTE,
					Prioridade.MEDIA,
					LocalDate.now().plusDays(5),
					"apresentacao", "curso"));

			repository.save(tarefa(
					"Fechar checklist do curso",
					"Marcar os critérios atendidos e revisar o funcionamento da aplicação.",
					"Equipe",
					StatusTarefa.CONCLUIDA,
					Prioridade.BAIXA,
					LocalDate.now().minusDays(1),
					"curso", "checklist"));
		};
	}

	private Tarefa tarefa(
			String titulo,
			String descricao,
			String responsavel,
			StatusTarefa status,
			Prioridade prioridade,
			LocalDate prazo,
			String... tags) {
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo(titulo);
		tarefa.setDescricao(descricao);
		tarefa.setResponsavel(responsavel);
		tarefa.setStatus(status);
		tarefa.setPrioridade(prioridade);
		tarefa.setPrazo(prazo);
		tarefa.setTags(new LinkedHashSet<>(Arrays.asList(tags)));
		return tarefa;
	}
}
