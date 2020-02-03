package br.com.dbserver.bancoapi.repository;

import br.com.dbserver.bancoapi.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ContaRepository extends JpaRepository<Conta, Long> {


    Optional<Conta> findByConta(Long conta);

   }
