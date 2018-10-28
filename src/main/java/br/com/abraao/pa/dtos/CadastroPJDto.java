package br.com.abraao.pa.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CadastroPJDto {

	private Long id;
	
	@NotEmpty(message="Nome não pode ser vazio")
	@Length(min = 3, max = 200, message ="O Nome deve conter entre 3 e 200 caracteres." )
	private String nome;
	
	@NotEmpty(message="O email não pode ser vazio")
	@Length(min = 5, max = 200, message ="O email deve conter entre 5 e 200 caracteres.")
	@Email(message="Email valido")
	private String email;
	
	@NotEmpty(message = "A senha não pode ser vazia")
	private String senha;
	
	@NotEmpty(message = "Cpf não pode ser vazio")
	private String cpf;
	
	@NotEmpty(message = "Razão Social Não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Razão social deve conter entre 5  e 200 caracteres")
	private String razaoSocial;
	
	@NotEmpty(message="Cnpj não pode ser vazio")
	@CNPJ(message="CNPJ inválido.")
	private String cnpj;
	
	
	
}
