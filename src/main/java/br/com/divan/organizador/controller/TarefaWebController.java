package br.com.divan.organizador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.divan.organizador.model.Prioridade;
import br.com.divan.organizador.model.StatusTarefa;
import br.com.divan.organizador.service.TarefaService;
import br.com.divan.organizador.web.TarefaFormulario;
import jakarta.validation.Valid;

@Controller
public class TarefaWebController {

	private final TarefaService service;

	public TarefaWebController(TarefaService service) {
		this.service = service;
	}

	@GetMapping("/")
	public String inicio() {
		return "redirect:/tarefas";
	}

	@GetMapping("/tarefas")
	public String listar(
			@RequestParam(required = false) StatusTarefa status,
			@RequestParam(required = false) String termo,
			Model model) {
		prepararOpcoes(model);
		model.addAttribute("tarefas", service.listarParaTela(status, termo));
		model.addAttribute("resumo", service.gerarResumo());
		model.addAttribute("statusSelecionado", status);
		model.addAttribute("termo", termo);
		return "tarefas";
	}

	@GetMapping("/tarefas/nova")
	public String nova(Model model) {
		prepararFormulario(model, new TarefaFormulario(), false, null);
		return "formulario";
	}

	@PostMapping("/tarefas")
	public String criar(
			@Valid @ModelAttribute("formulario") TarefaFormulario formulario,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			prepararFormulario(model, formulario, false, null);
			return "formulario";
		}

		service.criar(formulario.toEntrada());
		redirectAttributes.addFlashAttribute("mensagem", "Tarefa criada com sucesso.");
		return "redirect:/tarefas";
	}

	@GetMapping("/tarefas/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		prepararFormulario(model, TarefaFormulario.de(service.buscarEntidade(id)), true, id);
		return "formulario";
	}

	@PostMapping("/tarefas/{id}")
	public String atualizar(
			@PathVariable Long id,
			@Valid @ModelAttribute("formulario") TarefaFormulario formulario,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			prepararFormulario(model, formulario, true, id);
			return "formulario";
		}

		service.atualizar(id, formulario.toEntrada());
		redirectAttributes.addFlashAttribute("mensagem", "Tarefa atualizada com sucesso.");
		return "redirect:/tarefas";
	}

	@PostMapping("/tarefas/{id}/status")
	public String alterarStatus(
			@PathVariable Long id,
			@RequestParam StatusTarefa status,
			RedirectAttributes redirectAttributes) {
		service.alterarStatus(id, status);
		redirectAttributes.addFlashAttribute("mensagem", "Status atualizado.");
		return "redirect:/tarefas";
	}

	@PostMapping("/tarefas/{id}/excluir")
	public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		service.remover(id);
		redirectAttributes.addFlashAttribute("mensagem", "Tarefa removida.");
		return "redirect:/tarefas";
	}

	private void prepararFormulario(Model model, TarefaFormulario formulario, boolean modoEdicao, Long tarefaId) {
		prepararOpcoes(model);
		model.addAttribute("formulario", formulario);
		model.addAttribute("modoEdicao", modoEdicao);
		model.addAttribute("tarefaId", tarefaId);
		model.addAttribute("tituloPagina", modoEdicao ? "Editar tarefa" : "Nova tarefa");
	}

	private void prepararOpcoes(Model model) {
		model.addAttribute("statusDisponiveis", StatusTarefa.values());
		model.addAttribute("prioridades", Prioridade.values());
	}
}
