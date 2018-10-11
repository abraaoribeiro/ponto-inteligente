package br.com.abraao.pa.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.abraao.pa.api.entities.Empresa;
import br.com.abraao.pa.api.entities.Funcionario;
import br.com.abraao.pa.api.enun.PerfilEnum;
import br.com.abraao.pa.api.utils.PasswordUtil;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String EMAIL = "funcionario@email.com";
	private static final String CPF = "051.000.000-00";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterEmpresa());
		this.funcionarioRepository.save(obterFuncionario(empresa));

	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testBuscaFuncionarioPorCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);

		assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testBuscaFuncionarioPorEmailECpfParaEmailInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(funcionario);
	}

	@Test
	public void testBuscaFunionarioPorEmailECpfParaCpfInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("123456788985", EMAIL);

		assertNotNull(funcionario);

	}

	private Funcionario obterFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulano");
		funcionario.setPerfil(PerfilEnum.ROLE_USE);
		funcionario.setSenha(PasswordUtil.gerarBCrypt("12333"));
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);
		return funcionario;

	}

	private Empresa obterEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("1234567897");
		empresa.setRazaoSocial("exemplo");

		return empresa;
	}

}
