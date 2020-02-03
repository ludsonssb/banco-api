package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.model.LogTransacao;
import br.com.dbserver.bancoapi.repository.ContaRepository;
import br.com.dbserver.bancoapi.repository.LogTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class LogTransacaoService {

    @Autowired
    private LogTransacaoRepository logTransacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaService contaService;


    public LogTransacao salvaLogTransacao(Long idConta, LocalDate dataCriacaoConta, Long conta, double saldo, String tipoMovimentacao) {

            LogTransacao log = new LogTransacao();
            log.setIdConta(idConta);
            log.setDataCriacaoConta(dataCriacaoConta);
            log.setConta(conta);
            log.setSaldo(saldo);
            log.setDataAlteracao(LocalDateTime.now());
            log.setTipoMovimentacao(tipoMovimentacao);
            return logTransacaoRepository.save(log);
    }
}
