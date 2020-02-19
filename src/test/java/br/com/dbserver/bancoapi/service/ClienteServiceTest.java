package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.BeforeTest;
import br.com.dbserver.bancoapi.controller.dto.AlteraClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteRequest;
import br.com.dbserver.bancoapi.controller.dto.ClienteResponse;
import br.com.dbserver.bancoapi.exceptions.ClienteNaoEncontradoException;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;


public class ClienteServiceTest extends BeforeTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    @Autowired
    AlteraClienteDTO alteraClienteDTO;

    private static final long UM = 1;
    private static final long SEIS = 6;

    @Test(expected = ClienteNaoEncontradoException.class)
    public void buscaPorIdClienteNull()  {
        clienteService.buscarClientePeloCodigo(null);
    }

    @Test
    public void buscaPorIdClientePreenchido(){
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findById(UM)).thenReturn(Optional.of(cliente));
        ClienteResponse result = clienteService.buscarClientePeloCodigo(UM);
        assertNotNull(result);
        assertNotSame(cliente, result);
        assertEquals(cliente.getId(), result.getId());
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

    @Test
    public void criacaoCliente(){
        Cliente cliente = new Cliente();
        ClienteRequest request = new ClienteRequest();
        request.setCpf("11122233345");
        request.setNome("Easdasda");
        Mockito.when(clienteRepository.save(any())).thenReturn(Optional.of(cliente));
        assertNull(clienteService.cadastroCliente(request));
    }
}
