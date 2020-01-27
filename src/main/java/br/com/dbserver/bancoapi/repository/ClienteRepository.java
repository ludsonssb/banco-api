package br.com.dbserver.bancoapi.repository;

import br.com.dbserver.bancoapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}