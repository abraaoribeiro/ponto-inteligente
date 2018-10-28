package br.com.abraao.pa.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.abraao.pa.domain.Funcionario;
import br.com.abraao.pa.domain.Lancamento;
import br.com.abraao.pa.domain.enums.TipoEnum;
import br.com.abraao.pa.dtos.LancamentoDto;
import br.com.abraao.pa.response.Response;
import br.com.abraao.pa.services.FuncionarioService;
import br.com.abraao.pa.services.LancamentoService;

@RestController
@RequestMapping("api/lancamentos")
@CrossOrigin(origins= "*")
public class LancamentoRest {
	
	private static final Logger log = LoggerFactory.getLogger(LancamentoRest.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	/**
	 * Retorna a lista de lançamento de um funcionario.
	 * 
	 * @param funcionarioId
	 * @param pag
	 * @param ord
	 * @param dir
	 * @return ResponseEntity<Response<LancamentoDto>>
	 */
	
	@GetMapping(value="/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamentoDto>>> listaPorFuncionarioId(
			@PathVariable("fincionarioID") Long funcionarioId,
			@RequestParam(value="page", defaultValue="0")int pag,
			@RequestParam(value="ord", defaultValue="id") String ord,
			@RequestParam(value="dir", defaultValue="DES")String dir ){
		
		log.info("Buscando funcionario por ID do funcionario");
		Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = this.lancamentoService.buscarPoFuncionario(funcionarioId, pageRequest);
		Page<LancamentoDto> lancamentoDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));
		
		response.setData(lancamentoDto);
		return ResponseEntity.ok(response);
		
		
	}
	
	/**
	 * Retorna lancamento por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<LancamentoDto>>
	 */

	@GetMapping(value ="/{id}")
	public ResponseEntity<Response<LancamentoDto>> listtarPorId(@PathVariable ("id") Long id){
		log.info("Buscando lançamento po Id: {}", id);
		
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);
		
		if(!lancamento.isPresent()) {
			log.info("Lancamento não para o ID: {}", id);
			response.getErrors().add("Lançamento não encontrado para o id"+ id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterLancamentoDto(lancamento.get()));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Adiciona um novo lançamento
	 * 
	 * @param lancamentoDto
	 * @param result
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException
	 */
	
	@PostMapping()
	public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto,
			BindingResult result) throws ParseException{
		log.info("Adicionar Lanacamento: {}", lancamentoDto.toString());
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		validarFuncionario(lancamentoDto, result);
		Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			return ResponseEntity.badRequest().body(response);
			
			
		}
		
		lancamento = this.lancamentoService.persistir(lancamento);
		response.setData(this.converterLancamentoDto(lancamento));	
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um lançamento.
	 * 
	 * @param id
	 * @param lancamentoDto
	 * @param result
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException
	 */
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result)throws ParseException {
		log.info("Atualizando lancamento: {}", lancamentoDto.toString());
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		validarFuncionario(lancamentoDto, result);
		lancamentoDto.setId(Optional.of(id));
		Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);
		
		if(result.hasErrors()){
			log.info("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
			
		}
		
		lancamento = this.lancamentoService.persistir(lancamento);
		response.setData(this.converterLancamentoDto(lancamento));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um lançamento
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>  
	 */
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
		log.info("REmovendo laçamento:{}", id);
		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);
		
		if(!lancamento.isPresent()) {
			log.info("Erro ao remover devido ao laçançamento ID: {}", id);
			response.getErrors().add("Erro ao remmover lançamento. Resgistro não encontrado para id" + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		this.lancamentoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
		
	}
	
	/**
	 * Converte um LancamentoDto para uma entidade Lancamento.
	 * 
	 * @param lancamentoDto
	 * @param result
	 * @return Lancamento
	 * @throws ParseException 
	 */
	
	private Lancamento converterDtoParaLancamento(LancamentoDto lancamentoDto, BindingResult result)
		throws ParseException {
		Lancamento lancamento = new Lancamento();
		
		if(lancamentoDto.getId().isPresent()) {
			Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDto.getId().get());
			if(lanc.isPresent()) {
				lancamento = lanc.get();
			} else {
				result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
			}
			
		}else {
			
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());			
		}
		
		lancamento.setDescricao(lancamentoDto.getDescricao());
		lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
		lancamento.setData(this.dateFormat.parse(lancamentoDto.getData()));
		
		if(EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
			lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
			
		}else {
			result.addError(new ObjectError("tipo", "Tipo inválido"));
		}
		
		return lancamento;		
	}

	/**
	 * Valida um funcionário, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param lancamentoDto
	 * @param result
	 */
	
	private void validarFuncionario(LancamentoDto lancamentoDto, BindingResult result) {
		if(lancamentoDto.getFuncionarioId()== null) {
			result.addError(new ObjectError("funcionario", "Funcionario não informado."));
			return;
			
		}
		log.info("Validando funcionario id {}:", lancamentoDto.getFuncionarioId());
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(lancamentoDto.getFuncionarioId());
		if(!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcioanrio não encontrado. ID inexistente"));
		}
	}
	
	/**
	 * Converte uma entidade lançamento para seu respectivo DTO
	 * 
	 * @param lancamento
	 * @return
	 */
	private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
		LancamentoDto  lancamentoDto = new LancamentoDto();
		lancamentoDto.setId(Optional.of(lancamento.getId()));
		lancamentoDto.setData(this.dateFormat.format(lancamento.getData()));
		lancamentoDto.setTipo(lancamento.getTipo().toString());
		lancamentoDto.setDescricao(lancamento.getDescricao());
		lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
		lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());
	
		return lancamentoDto;
	}
	
}
