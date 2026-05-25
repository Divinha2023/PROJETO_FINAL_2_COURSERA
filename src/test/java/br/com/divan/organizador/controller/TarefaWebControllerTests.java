package br.com.divan.organizador.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaWebControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deveRenderizarPainelWeb() throws Exception {
		mockMvc.perform(get("/tarefas"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Organizador de Tarefas")))
				.andExpect(content().string(containsString("Fila de prioridade")));
	}

	@Test
	void deveRenderizarFormularioDeCriacao() throws Exception {
		mockMvc.perform(get("/tarefas/nova"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Nova tarefa")))
				.andExpect(content().string(containsString("Salvar")));
	}
}
