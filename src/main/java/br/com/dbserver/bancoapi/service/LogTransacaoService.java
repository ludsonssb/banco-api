package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.model.LogTransacao;
import br.com.dbserver.bancoapi.repository.LogTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class LogTransacaoService {

    @Autowired
    private LogTransacaoRepository logTransacaoRepository;

    public LogTransacao salvaLogTransacao(Conta conta, String tipoMovimentacao) {
        LogTransacao log = new LogTransacao();
        log.setIdConta(conta.getId());
        log.setDataCriacaoConta(conta.getDataCriacaoConta());
        log.setConta(conta.getNumero());
        log.setSaldo(conta.getSaldo());
        log.setDataAlteracao(LocalDate.now());
        log.setTipoMovimentacao(tipoMovimentacao);
        return logTransacaoRepository.save(log);
    }

    public LogTransacao salvaLogTransacao(Long idConta, LocalDate dataCriacaoConta, Long conta, double saldo, String tipoMovimentacao) {

            LogTransacao log = new LogTransacao();
            log.setIdConta(idConta);
            log.setDataCriacaoConta(dataCriacaoConta);
            log.setConta(conta);
            log.setSaldo(saldo);
            log.setDataAlteracao(LocalDate.now());
            log.setTipoMovimentacao(tipoMovimentacao);
            return logTransacaoRepository.save(log);
    }

    public List<LogTransacao> recuperaLog(LocalDate dataDe, LocalDate dataAte, long conta) {

        List<LogTransacao> recuperaLog = logTransacaoRepository.findByDataAlteracaoBetweenAndConta(dataDe, dataAte, conta);
        if(recuperaLog != null) {
            return recuperaLog;
        }
       //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
    }
}
