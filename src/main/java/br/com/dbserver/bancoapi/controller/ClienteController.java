package br.com.dbserver.bancoapi.controller;

import br.com.dbserver.bancoapi.controller.dto.*;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import br.com.dbserver.bancoapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping("{id}")
//    public ClienteResponse buscarPorId(@PathVariable Long id) {
//
//        return clienteService.buscarClientePeloCodigo(id);
//    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.buscarClientePeloCodigo(id);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }


//    @PostMapping
//    public ClienteResponse cadastrar(@RequestBody ClienteRequest novoCliente) {
//        return clienteService.cadastroCliente(novoCliente);
//    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody ClienteRequest novoCliente) {
        ClienteResponse cliente = clienteService.cadastroCliente(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

//    @PutMapping("{cpf}")
//    public Optional<Cliente> alterar(@PathVariable String cpf, @RequestBody AlteraClienteDTO alteraCliente){
//       return clienteService.atualizaClientePorCpf(cpf, alteraCliente);
//    }

    @PutMapping("{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  ResponseEntity<Cliente> alterar(@PathVariable String cpf, @RequestBody AlteraClienteDTO alteraCliente){
        Optional<Cliente> cliente = clienteService.atualizaClientePorCpf(cpf, alteraCliente);
        return ResponseEntity.ok(cliente.get());
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
