package br.com.coursera.organizador.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.coursera.organizador.dto.TarefaEntrada;
import br.com.coursera.organizador.model.Prioridade;
import br.com.coursera.organizador.model.StatusTarefa;

@SpringBootTest
@Transactional
class TarefaServiceTests {

	@Autowired
	private TarefaService service;

	@Test
	void deveCriarTarefaComTagsSemDuplicidade() {
		TarefaEntrada entrada = new TarefaEntrada(
				"Preparar entrega",
				"Separar os arquivos finais do projeto.",
				"Divan",
				StatusTarefa.PENDENTE,
				Prioridade.ALTA,
				LocalDate.now().plusDays(3),
				new String[] { "GitHub", "github", " Java " });

		var tarefa = service.criar(entrada);

		assertThat(tarefa.id()).isNotNull();
		assertThat(tarefa.tags()).containsExactlyInAnyOrder("github", "java");
	}

	@Test
	void deveMontarFilaPriorizandoTarefasUrgentes() {
		var fila = service.listarFilaCompleta();

		assertThat(fila).isNotEmpty();
		assertThat(fila.getFirst().prioridade()).isEqualTo(Prioridade.URGENTE);
		assertThat(fila).noneMatch(tarefa -> tarefa.status() == StatusTarefa.CONCLUIDA);
	}
}
