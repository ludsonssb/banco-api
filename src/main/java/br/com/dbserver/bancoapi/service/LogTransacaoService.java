package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.model.LogTransacao;
import br.com.dbserver.bancoapi.repository.LogTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class LogTransacaoService {

    @Autowired
    private LogTransacaoRepository logTransacaoRepository;

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

    public List<LogTransacao> recuperaLog(String dataDeStr, String dataAteStr, long conta) {

        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        LocalDate dataDe = LocalDate.parse(dataDeStr,formataData);
        LocalDate dataAte = LocalDate.parse(dataAteStr,formataData);

        List<LogTransacao> recuperaLog = logTransacaoRepository.findByDataAlteracaoBetweenAndConta(dataDe, dataAte, conta);
        if(recuperaLog != null) {
            return recuperaLog;
        }
       //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
    }
}
