package br.com.abraao.pa.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.abraao.pa.domain.Lancamento;

public interface LancamentoService {
	
	/**
	 * Retorna uma lista paginada de um determinado funcionario 
	 * 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 */
	
	Page<Lancamento> buscarPoFuncionario(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 *Retorna um lacamento por ID
	 * 
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 */
	
	Optional<Lancamento> buscarPorId(Long id);
	
	/**
	 *  Persiste um lacamento na base de dados
	 *   
	 *   
	 * @param lancamento
	 * @return Lancamento
	 */
	
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * Remove um funcionario por ID
	 * 
	 * 
	 * @param id
	 */
	
	void remover(Long id);
	
	
}
