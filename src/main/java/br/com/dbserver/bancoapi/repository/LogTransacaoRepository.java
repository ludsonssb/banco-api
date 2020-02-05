package br.com.dbserver.bancoapi.repository;

import br.com.dbserver.bancoapi.model.LogTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LogTransacaoRepository extends JpaRepository<LogTransacao, Long> {

   List<LogTransacao> findByDataAlteracaoBetweenAndConta(LocalDate dataDe, LocalDate dataAte, Long conta);
}
