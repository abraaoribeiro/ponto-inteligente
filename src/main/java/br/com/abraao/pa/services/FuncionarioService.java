package br.com.abraao.pa.services;

import java.util.Optional;

import br.com.abraao.pa.domain.Funcionario;

public interface FuncionarioService {

	
	/**
	 * 		
	 * @param funcionario
	 * @return Funcionario
	 */
						
	Funcionario persistir(Funcionario funcionario); 
	
	/**
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * 
	 * @param email
	 * @return Optional<Funcionario>
	 */
	
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * 
	 * @param id
	 * @return Optional Funcionario
	 */
	
	Optional<Funcionario> buscarPorId(Long id);
	
	
}
