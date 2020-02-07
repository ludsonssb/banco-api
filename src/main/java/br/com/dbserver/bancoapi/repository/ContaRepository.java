package br.com.dbserver.bancoapi.repository;

import br.com.dbserver.bancoapi.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {


    Optional<Conta> findByNumero(Long numero);

   }
