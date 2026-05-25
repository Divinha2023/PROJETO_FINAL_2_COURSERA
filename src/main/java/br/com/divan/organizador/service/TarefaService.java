package br.com.divan.organizador.service;

import java.text.Normalizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.divan.organizador.dto.ResumoPainel;
import br.com.divan.organizador.dto.TarefaEntrada;
import br.com.divan.organizador.dto.TarefaSaida;
import br.com.divan.organizador.model.StatusTarefa;
import br.com.divan.organizador.model.Tarefa;
import br.com.divan.organizador.repository.TarefaRepository;

@Service
public class TarefaService {

	private final TarefaRepository repository;

	public TarefaService(TarefaRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<Tarefa> listarParaTela(StatusTarefa status, String termo) {
		return filtrarTarefas(status, termo);
	}

	@Transactional(readOnly = true)
	public List<TarefaSaida> listarParaApi(StatusTarefa status, String termo) {
		return filtrarTarefas(status, termo).stream()
				.map(TarefaSaida::de)
				.toList();
	}

	@Transactional(readOnly = true)
	public Tarefa buscarEntidade(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada: " + id));
	}

	@Transactional(readOnly = true)
	public TarefaSaida buscarPorId(Long id) {
		return TarefaSaida.de(buscarEntidade(id));
	}

	@Transactional
	public TarefaSaida criar(TarefaEntrada entrada) {
		Tarefa tarefa = new Tarefa();
		aplicarDados(tarefa, entrada);
		return TarefaSaida.de(repository.save(tarefa));
	}

	@Transactional
	public TarefaSaida atualizar(Long id, TarefaEntrada entrada) {
		Tarefa tarefa = buscarEntidade(id);
		aplicarDados(tarefa, entrada);
		return TarefaSaida.de(repository.save(tarefa));
	}

	@Transactional
	public TarefaSaida alterarStatus(Long id, StatusTarefa status) {
		Tarefa tarefa = buscarEntidade(id);
		tarefa.setStatus(status);
		return TarefaSaida.de(repository.save(tarefa));
	}

	@Transactional
	public void remover(Long id) {
		Tarefa tarefa = buscarEntidade(id);
		repository.delete(tarefa);
	}

	@Transactional(readOnly = true)
	public ResumoPainel gerarResumo() {
		List<Tarefa> tarefas = repository.findAll();

		Map<StatusTarefa, Long> porStatus = new EnumMap<>(StatusTarefa.class);
		for (StatusTarefa status : StatusTarefa.values()) {
			porStatus.put(status, 0L);
		}
		tarefas.forEach(tarefa -> porStatus.merge(tarefa.getStatus(), 1L, Long::sum));

		Map<String, Long> porResponsavel = tarefas.stream()
				.collect(Collectors.groupingBy(
						Tarefa::getResponsavel,
						LinkedHashMap::new,
						Collectors.counting()));

		Set<String> etiquetas = tarefas.stream()
				.flatMap(tarefa -> tarefa.getTags().stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));

		List<TarefaSaida> proximasDaFila = consumirFilaDePrioridade(montarFilaDePrioridade(tarefas), 5);

		return new ResumoPainel(tarefas.size(), porStatus, porResponsavel, etiquetas, proximasDaFila);
	}

	@Transactional(readOnly = true)
	public List<TarefaSaida> listarFilaCompleta() {
		return consumirFilaDePrioridade(montarFilaDePrioridade(repository.findAll()), Integer.MAX_VALUE);
	}

	private List<Tarefa> filtrarTarefas(StatusTarefa status, String termo) {
		List<Tarefa> tarefas = status == null ? repository.findAll() : repository.findByStatus(status);
		return tarefas.stream()
				.filter(tarefa -> combinaComTermo(tarefa, termo))
				.sorted(comparadorDaTela())
				.toList();
	}

	private void aplicarDados(Tarefa tarefa, TarefaEntrada entrada) {
		tarefa.setTitulo(limparTexto(entrada.titulo()));
		tarefa.setDescricao(limparTexto(entrada.descricao()));
		tarefa.setResponsavel(limparTexto(entrada.responsavel()));
		tarefa.setStatus(entrada.status());
		tarefa.setPrioridade(entrada.prioridade());
		tarefa.setPrazo(entrada.prazo());
		tarefa.setTags(normalizarTags(entrada.tags()));
	}

	private Set<String> normalizarTags(String[] tags) {
		Set<String> resultado = new LinkedHashSet<>();
		if (tags == null) {
			return resultado;
		}

		// O array vem da API ou do formulário; o conjunto remove duplicidades sem perder a intenção do usuário.
		for (String tag : tags) {
			String texto = limparTexto(tag).toLowerCase(Locale.ROOT);
			if (StringUtils.hasText(texto)) {
				resultado.add(texto);
			}
		}
		return resultado;
	}

	private Queue<Tarefa> montarFilaDePrioridade(List<Tarefa> tarefas) {
		return tarefas.stream()
				.filter(tarefa -> tarefa.getStatus() != StatusTarefa.CONCLUIDA)
				.sorted(comparadorDePrioridade())
				.collect(Collectors.toCollection(ArrayDeque::new));
	}

	private List<TarefaSaida> consumirFilaDePrioridade(Queue<Tarefa> fila, int limite) {
		List<TarefaSaida> resultado = new ArrayList<>();
		while (!fila.isEmpty() && resultado.size() < limite) {
			resultado.add(TarefaSaida.de(fila.poll()));
		}
		return resultado;
	}

	private boolean combinaComTermo(Tarefa tarefa, String termo) {
		if (!StringUtils.hasText(termo)) {
			return true;
		}

		String textoDaTarefa = normalizarParaBusca(String.join(" ",
				tarefa.getTitulo(),
				tarefa.getDescricao(),
				tarefa.getResponsavel(),
				String.join(" ", tarefa.getTags())));

		String[] palavrasBuscadas = normalizarParaBusca(termo).split("\\s+");
		return Arrays.stream(palavrasBuscadas)
				.filter(StringUtils::hasText)
				.allMatch(textoDaTarefa::contains);
	}

	private Comparator<Tarefa> comparadorDaTela() {
		return Comparator.comparing(Tarefa::getStatus)
				.thenComparing(comparadorDePrioridade())
				.thenComparing(Tarefa::getPrazo, Comparator.nullsLast(Comparator.naturalOrder()));
	}

	private Comparator<Tarefa> comparadorDePrioridade() {
		return Comparator.comparingInt((Tarefa tarefa) -> tarefa.getPrioridade().getPeso()).reversed()
				.thenComparing(Tarefa::getPrazo, Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(Tarefa::getCriadaEm, Comparator.nullsLast(Comparator.naturalOrder()));
	}

	private String limparTexto(String valor) {
		return valor == null ? "" : valor.trim().replaceAll("\\s+", " ");
	}

	private String normalizarParaBusca(String valor) {
		String texto = limparTexto(valor).toLowerCase(Locale.ROOT);
		return Normalizer.normalize(texto, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "");
	}
}
