package br.com.abraao.pa.services;

import java.util.Optional;

import br.com.abraao.pa.domain.Empresa;

public interface EmpresaService {
	
	/**
	 * 
	 * @param cnpj
	 * @return Optional<Fincionario>
	 */
	
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * 
	 * @param empresa
	 * @return Empresa
	 */
	
	Empresa persistir(Empresa empresa);
}
