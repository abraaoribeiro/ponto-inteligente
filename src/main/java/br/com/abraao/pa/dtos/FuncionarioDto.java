package br.com.abraao.pa.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FuncionarioDto {

	private Long id;
	
	@NotEmpty(message ="Nome não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message ="Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message="Email invalido.")
	private String email;
	
	private Optional<String> senha = Optional.empty();
	
	private Optional<String> valorHora = Optional.empty();
	
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	
	
	
}
