package br.com.coursera.organizador.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaRestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deveListarTarefas() throws Exception {
		mockMvc.perform(get("/api/tarefas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));
	}

	@Test
	void deveCriarTarefaRetornandoCreated() throws Exception {
		String corpo = """
				{
				  "titulo": "Publicar projeto",
				  "descricao": "Enviar o repositório final para avaliação.",
				  "responsavel": "Divan",
				  "status": "PENDENTE",
				  "prioridade": "ALTA",
				  "prazo": "2026-06-10",
				  "tags": ["github", "final"]
				}
				""";

		mockMvc.perform(post("/api/tarefas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.titulo").value("Publicar projeto"));
	}

	@Test
	void deveRetornarBadRequestQuandoTituloNaoForEnviado() throws Exception {
		String corpo = """
				{
				  "descricao": "Sem título para forçar erro de validação.",
				  "responsavel": "Divan",
				  "status": "PENDENTE",
				  "prioridade": "MEDIA"
				}
				""";

		mockMvc.perform(post("/api/tarefas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.campos.titulo").exists());
	}
}
