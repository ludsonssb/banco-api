package br.com.ludsonbrandao.bancoapi.repository;

import br.com.ludsonbrandao.bancoapi.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {


    Optional<Conta> findByNumero(Long numero);

   }
