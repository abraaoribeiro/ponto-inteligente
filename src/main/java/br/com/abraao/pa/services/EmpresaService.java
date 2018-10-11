package br.com.abraao.pa.services;

import java.util.Optional;

import br.com.abraao.pa.api.entities.Empresa;

public interface EmpresaService {
	
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	Empresa persistir(Empresa empresa);
}
