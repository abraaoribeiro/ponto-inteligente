package br.com.abraao.pa.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abraao.pa.domain.Empresa;
import br.com.abraao.pa.dtos.EmpresaDto;
import br.com.abraao.pa.services.EmpresaService;
import br.com.abraao.pa.response.Response;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaRest {

	private static final Logger log = LoggerFactory.getLogger(EmpresaRest.class);

	@Autowired
	private EmpresaService empresaService;

	public EmpresaRest() {

	}
	
	/**
	 * 
	 * @param cnpj
	 * @return ResponseEntity<Response<EmpresaDto>>
	 */

	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		log.info("Buscando empresa por CNPJ: {}", cnpj);
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		if (!empresa.isPresent()) {
			log.info("Empresa não encontrada para o CNPJ" + cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ" + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 
	 * @param empresa
	 * @return EmpresaDto
	 */

	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setId(empresa.getId());
		empresa.setCnpj(empresa.getCnpj());
		empresa.setRazaoSocial(empresa.getRazaoSocial());

		return empresaDto;
	}

}
