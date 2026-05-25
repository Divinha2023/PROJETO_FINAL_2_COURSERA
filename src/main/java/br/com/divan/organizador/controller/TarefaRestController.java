package br.com.divan.organizador.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.divan.organizador.dto.ResumoPainel;
import br.com.divan.organizador.dto.TarefaEntrada;
import br.com.divan.organizador.dto.TarefaSaida;
import br.com.divan.organizador.model.StatusTarefa;
import br.com.divan.organizador.service.TarefaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaRestController {

	private final TarefaService service;

	public TarefaRestController(TarefaService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<TarefaSaida>> listar(
			@RequestParam(required = false) StatusTarefa status,
			@RequestParam(required = false) String termo) {
		return ResponseEntity.ok(service.listarParaApi(status, termo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TarefaSaida> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<TarefaSaida> criar(
			@Valid @RequestBody TarefaEntrada entrada,
			UriComponentsBuilder uriBuilder) {
		TarefaSaida tarefa = service.criar(entrada);
		URI uri = uriBuilder.path("/api/tarefas/{id}").buildAndExpand(tarefa.id()).toUri();
		return ResponseEntity.created(uri).body(tarefa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TarefaSaida> atualizar(
			@PathVariable Long id,
			@Valid @RequestBody TarefaEntrada entrada) {
		return ResponseEntity.ok(service.atualizar(id, entrada));
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<TarefaSaida> alterarStatus(
			@PathVariable Long id,
			@RequestParam StatusTarefa status) {
		return ResponseEntity.ok(service.alterarStatus(id, status));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/resumo")
	public ResponseEntity<ResumoPainel> resumo() {
		return ResponseEntity.ok(service.gerarResumo());
	}

	@GetMapping("/fila")
	public ResponseEntity<List<TarefaSaida>> fila() {
		return ResponseEntity.ok(service.listarFilaCompleta());
	}
}
