package br.com.abraao.pa.services.implemets;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.abraao.pa.api.entities.Lancamento;
import br.com.abraao.pa.repository.LancamentoRepository;
import br.com.abraao.pa.services.LancamentoService;

@Service
public class LancamentosServiceImpl implements LancamentoService {

	private static final Logger log = LoggerFactory.getLogger(LancamentosServiceImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Override
	public org.springframework.data.domain.Page<Lancamento> buscarPoFuncionario(Long funcionarioId,
			PageRequest pageRequest) {

		log.info("Buscando Lancamento para o funcionario ID {}", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {

		log.info("Buscando um Lancamento por ID {}", id);
		return Optional.ofNullable(this.lancamentoRepository.findOne(id));
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {

		log.info("Persistindo um lancamento {} ", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo o lancamento");
		this.lancamentoRepository.delete(id);

	}

}
