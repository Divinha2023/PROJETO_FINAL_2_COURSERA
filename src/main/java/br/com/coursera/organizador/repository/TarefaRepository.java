package br.com.coursera.organizador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.coursera.organizador.model.StatusTarefa;
import br.com.coursera.organizador.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

	List<Tarefa> findByStatus(StatusTarefa status);
}
