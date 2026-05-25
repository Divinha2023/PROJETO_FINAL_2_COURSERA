package br.com.divan.organizador.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.divan.organizador.dto.TarefaEntrada;
import br.com.divan.organizador.model.Prioridade;
import br.com.divan.organizador.model.StatusTarefa;

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
				new String[] { "Cliente", "cliente", " Urgente " });

		var tarefa = service.criar(entrada);

		assertThat(tarefa.id()).isNotNull();
		assertThat(tarefa.tags()).containsExactlyInAnyOrder("cliente", "urgente");
	}

	@Test
	void deveMontarFilaPriorizandoTarefasUrgentes() {
		var fila = service.listarFilaCompleta();

		assertThat(fila).isNotEmpty();
		assertThat(fila.getFirst().prioridade()).isEqualTo(Prioridade.URGENTE);
		assertThat(fila).noneMatch(tarefa -> tarefa.status() == StatusTarefa.CONCLUIDA);
	}
}
