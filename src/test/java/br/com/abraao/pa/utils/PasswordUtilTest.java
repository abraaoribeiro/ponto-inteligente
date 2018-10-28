package br.com.abraao.pa.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.abraao.pa.utils.PasswordUtil;

public class PasswordUtilTest {
	private static final String SENHA = "123456";
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Test
	public void testSenhaNula() throws Exception {
		assertNull(PasswordUtil.gerarBCrypt(null));
	}

	@Test
	public void testGerarHashSenha() throws Exception {
		String hash = PasswordUtil.gerarBCrypt(SENHA);
		assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
	}

}
