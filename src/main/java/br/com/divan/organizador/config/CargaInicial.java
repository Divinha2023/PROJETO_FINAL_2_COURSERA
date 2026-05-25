package br.com.divan.organizador.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.divan.organizador.model.Prioridade;
import br.com.divan.organizador.model.StatusTarefa;
import br.com.divan.organizador.model.Tarefa;
import br.com.divan.organizador.repository.TarefaRepository;

@Configuration
public class CargaInicial {

	@Bean
	CommandLineRunner carregarDados(TarefaRepository repository) {
		return args -> {
			if (repository.count() > 0) {
				return;
			}

			repository.save(tarefa(
					"Revisar contratos pendentes",
					"Conferir cláusulas, prazos e responsáveis antes da reunião semanal.",
					"Mariana",
					StatusTarefa.EM_ANDAMENTO,
					Prioridade.ALTA,
					LocalDate.now().plusDays(2),
					"juridico", "contratos"));

			repository.save(tarefa(
					"Atualizar indicadores do mês",
					"Consolidar os números de entregas, atrasos e demandas concluídas.",
					"Rafael",
					StatusTarefa.PENDENTE,
					Prioridade.URGENTE,
					LocalDate.now().plusDays(1),
					"relatorios", "gestao"));

			repository.save(tarefa(
					"Planejar agenda com clientes",
					"Organizar horários disponíveis e confirmar prioridades de atendimento.",
					"Camila",
					StatusTarefa.PENDENTE,
					Prioridade.MEDIA,
					LocalDate.now().plusDays(5),
					"clientes", "agenda"));

			repository.save(tarefa(
					"Concluir treinamento interno",
					"Registrar presença e finalizar os materiais compartilhados com a equipe.",
					"Rafael",
					StatusTarefa.CONCLUIDA,
					Prioridade.BAIXA,
					LocalDate.now().minusDays(1),
					"treinamento", "equipe"));
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
