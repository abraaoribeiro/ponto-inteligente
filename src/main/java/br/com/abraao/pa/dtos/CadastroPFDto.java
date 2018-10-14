package br.com.abraao.pa.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CadastroPFDto {

	private Long id;

	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;

	@NotEmpty(message = "Email não pode ser vazio")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message = " Email inválido")
	private String email;

	@NotEmpty(message = "Senha não poed ser vazia.")
	private String senha;

	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;

	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ(message = " CNPJ inválido")
	private String cnpj;

	private Optional<String> valorHora = Optional.empty();

	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();

	private Optional<String> qtdHorasAlmoco = Optional.empty();

	public CadastroPFDto() {

	}

}
