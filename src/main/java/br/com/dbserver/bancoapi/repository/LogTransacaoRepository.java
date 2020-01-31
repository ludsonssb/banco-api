package br.com.dbserver.bancoapi.repository;

import br.com.dbserver.bancoapi.model.LogTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogTransacaoRepository extends JpaRepository<LogTransacao, Long> {

}
