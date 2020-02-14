package br.com.dbserver.bancoapi.controller;

import br.com.dbserver.bancoapi.controller.dto.*;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import br.com.dbserver.bancoapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return clienteService.buscarClientePeloCodigo(id);
    }

    /*@GetMapping
    public ClienteDTO buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.map(ClienteDTO::new).orElse(null);
    }*/

    @PostMapping
    public ClienteResponse cadastrar(@RequestBody ClienteRequest novoCliente) {
        Cliente cliente = novoCliente.converter();
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponse(clienteSalvo);
    }

    @PutMapping("{cpf}")
    public Optional<Cliente> alterar(@PathVariable String cpf, @RequestBody AlteraClienteDTO alteraCliente){
       return clienteService.atualizaClientePorCpf(cpf, alteraCliente);
    }

    @Transactional
    @DeleteMapping("{cpf}")
    public void excluirPorCpf(@PathVariable String cpf){
        clienteRepository.deleteByCpf(cpf);
    }

    @GetMapping("/clientesIdContas")
    List<ClienteIdContaDTO> listaClienteIdContas(){
        return clienteService.listaClienteIdContas();
    }

    @GetMapping("/clientesSaldosContas")
    List<ClienteSaldoContaDTO> listaClienteSaldoContas(){
        return clienteService.listaClienteSaldoContas();
    }
}
