package br.com.divan.organizador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.divan.organizador.model.StatusTarefa;
import br.com.divan.organizador.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

	List<Tarefa> findByStatus(StatusTarefa status);
}
