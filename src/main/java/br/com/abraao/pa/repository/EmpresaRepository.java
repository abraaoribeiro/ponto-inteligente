package br.com.abraao.pa.repository;

import org.springframework.transaction.annotation.Transactional;

import br.com.abraao.pa.domain.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
}
