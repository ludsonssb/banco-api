package br.com.ludsonbrandao.bancoapi.repository;

import br.com.ludsonbrandao.bancoapi.model.LogTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LogTransacaoRepository extends JpaRepository<LogTransacao, Long> {

   List<LogTransacao> findByDataAlteracaoBetweenAndConta(LocalDate dataDe, LocalDate dataAte, Long conta);
}
