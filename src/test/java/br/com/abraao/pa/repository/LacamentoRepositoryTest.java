package br.com.abraao.pa.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.abraao.pa.api.entities.Empresa;
import br.com.abraao.pa.api.entities.Funcionario;
import br.com.abraao.pa.api.entities.Lancamento;
import br.com.abraao.pa.api.enun.PerfilEnum;
import br.com.abraao.pa.api.enun.TipoEnum;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LacamentoRepositoryTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterEmpresa());

		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId= funcionario.getId();

		this.lancamentoRepository.save(obterDadosLacamento(funcionario));
		this.lancamentoRepository.save(obterDadosLacamento(funcionario));

	}

	@After
	public void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorPaginaIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);

		assertEquals(2, lancamentos.getTotalElements());

	}

	public Lancamento obterDadosLacamento(Funcionario funcionario) {

		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		
		return lancamento;

	}

	public Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulano");
		funcionario.setPerfil(PerfilEnum.ROLE_USE);
		funcionario.setSenha("123456");
		funcionario.setCpf("126345648");
		funcionario.setEmail("eamil@email.com");
		funcionario.setEmpresa(empresa);

		return funcionario;

	}
	public Empresa obterEmpresa() {
		Empresa empresa = new Empresa();
		
		empresa.setRazaoSocial("exemplo");
		empresa.setCnpj("12344588255");
		
		return empresa;
	}

}
