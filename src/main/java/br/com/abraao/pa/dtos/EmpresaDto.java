package br.com.abraao.pa.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaDto {

	private Long id;
	private String razaoSocial;
	private String cnpj;
	
	public EmpresaDto() {
		
	}
	
}
