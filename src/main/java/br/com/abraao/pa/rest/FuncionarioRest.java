package br.com.abraao.pa.rest;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abraao.pa.domain.Funcionario;
import br.com.abraao.pa.dtos.FuncionarioDto;
import br.com.abraao.pa.repository.FuncionarioRepository;
import br.com.abraao.pa.response.Response;
import br.com.abraao.pa.services.FuncionarioService;
import br.com.abraao.pa.utils.PasswordUtil;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioRest {
	
	private static final Logger log = LoggerFactory.getLogger(FuncionarioRest.class);
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	public FuncionarioRest() {
		
	}
	
	/**
	 * Atualiza os dados do Funcionario
	 * 
	 * @param id
	 * @param funcionarioDto
	 * @param result
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	
	
	@PutMapping(value= "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable ("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException{
			log.info("Atualizando funcionario:{}", funcionarioDto.toString());
			
			Response<FuncionarioDto> response = new  Response<FuncionarioDto>();
			
			Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
			if(!funcionario.isPresent()) {
				result.addError(new ObjectError("funcionario", "FUncionario"));
			}
			this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);
			
			if(result.hasErrors()) {
				log.error("Erro validando funcionario: {}", result.getAllErrors());
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
				
			}
			this.funcionarioService.persistir(funcionario.get());
			response.setData(this.converterFuncionarioDto(funcionario.get()));
			
			return ResponseEntity.ok(response);

			
		}
	
		/**
		 * Atualiza os dados dos Funcionarios com base nos dados encontrados no DTO. 
		 *    
		 * @param funcionario
		 * @param funcionarioDto
		 * @param result
		 * @throws NoSuchAlgorithmException
		 */
	
		private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result)
		throws NoSuchAlgorithmException{
			funcionario.setNome(funcionarioDto.getNome());
			
			
			if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
				
				this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("email", "Email já existe")));
			
			funcionario.setEmail(funcionarioDto.getEmail());
				
			}
			
			funcionario.setQtdHorasAlmoco(null);
			funcionarioDto.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
			
			funcionario.setQtdHorasTrabalhoDia(null);
			funcionarioDto.getQtdHorasTrabalhoDia()
			.ifPresent(horaTrabalho -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(horaTrabalho)));
			
			
			funcionario.setValorHora(null);
			funcionarioDto.getValorHora()
			.ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
			
			if(funcionarioDto.getSenha().isPresent()) {
				funcionario.setSenha(PasswordUtil.gerarBCrypt(funcionarioDto.getSenha().get()));
			}
			
		}
		/**
		 * Retorna um Dto com os dados do funcionario
		 * 
		 * @param funcionario
		 * @return FuncionarioDto
		 */
		
		private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
			FuncionarioDto funcionarioDto = new FuncionarioDto();			
			funcionarioDto.setId(funcionario.getId());
			funcionarioDto.setNome(funcionario.getNome());
			funcionarioDto.setEmail(funcionario.getEmail());
			funcionario.getQtdHorasAlmocoOpt()
			.ifPresent(qtdHoraAlmoco -> funcionarioDto
					.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHoraAlmoco))));
			
			funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
					qtdHorasTrabDia -> funcionarioDto
					.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
			
			funcionario.getValorHoraOpt()
					.ifPresent(valorHora -> funcionarioDto
							.setValorHora(Optional.of(valorHora.toString())));
			
			return funcionarioDto;
			
		}
		
	
	
	
	
}
