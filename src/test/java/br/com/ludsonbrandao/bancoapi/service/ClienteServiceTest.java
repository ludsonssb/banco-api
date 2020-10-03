package br.com.ludsonbrandao.bancoapi.service;

import br.com.ludsonbrandao.bancoapi.BeforeTest;
import br.com.ludsonbrandao.bancoapi.controller.dto.ClienteResponse;
import br.com.ludsonbrandao.bancoapi.exceptions.ClienteNaoEncontradoException;
import br.com.ludsonbrandao.bancoapi.model.Cliente;
import br.com.ludsonbrandao.bancoapi.model.Conta;
import br.com.ludsonbrandao.bancoapi.repository.ClienteRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


public class ClienteServiceTest extends BeforeTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    private static final long UM = 1;

    @Test(expected = ClienteNaoEncontradoException.class)
    public void buscaPorIdClienteNull()  {
        clienteService.buscarClientePeloCodigo(null);
    }

    @Test
    public void buscaPorIdClientePreenchido(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setDataCadastro(LocalDate.now());
        cliente.setNome("eu");

        List<Conta> contas = new ArrayList<>();
        contas.add(new Conta());
        cliente.setContas(contas);

        Mockito.when(clienteRepository.findById(UM)).thenReturn(Optional.of(cliente));
        ClienteResponse response = clienteService.buscarClientePeloCodigo(UM);
        assertNotNull(response);

        assertEquals(cliente.getId(), response.getId());
        assertEquals(cliente.getNome(), response.getNome());
        assertEquals(cliente.getDataCadastro(), response.getDataCadastro());
        assertEquals(cliente.getContas().size(), response.getConta().size());
    }

    @Test
    public void atualizaCliente() {
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.save(any())).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = clienteService.atualizaClientePorCpf("",any());
        assertNotNull(result);
    }

    @Test
    public void listaClienteAndContas() {
        assertEquals(Collections.EMPTY_LIST, clienteService.listaClienteIdContas());
    }

    @Test
    public void listaClienteAndSaldosInContas() {
        assertEquals(Collections.EMPTY_LIST, clienteService.listaClienteSaldoContas());
    }

    //Não funciona
    @Test
    public void criacaoCliente(){
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.save(any())).thenReturn(Optional.of(cliente));
        assertNull(clienteService.cadastroCliente(any()));
    }
}
