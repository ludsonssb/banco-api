package br.com.dbserver.bancoapi.controller;

import br.com.dbserver.bancoapi.controller.dto.AlteraClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.NovoClienteDTO;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
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

    @GetMapping("{id}")
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.map(ClienteDTO::new).orElse(null);
    }

    /*@GetMapping
    public ClienteDTO buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.map(ClienteDTO::new).orElse(null);
    }*/

    @PostMapping
    public ClienteDTO cadastrar(@RequestBody NovoClienteDTO novoCliente) {
        Cliente cliente = novoCliente.converter();
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteDTO(clienteSalvo);
    }

    @PutMapping("{cpf}")
    public ClienteDTO alterar(@PathVariable String cpf, @RequestBody AlteraClienteDTO alteraCliente){
        Optional<Cliente> optionalCliente = clienteRepository.findByCpf(cpf);
        if(optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setNome(alteraCliente.getNome());
            clienteRepository.save(cliente);
        }
        return  null;
    }

    @Transactional
    @DeleteMapping("{cpf}")
    public void excluirPorCpf(@PathVariable String cpf){
        clienteRepository.deleteByCpf(cpf);
    }

}
