package br.com.abraao.pa.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmpresaDto {

	private Long id;
	private String razaoSocial;
	private String cnpj;
	
	
	
}
