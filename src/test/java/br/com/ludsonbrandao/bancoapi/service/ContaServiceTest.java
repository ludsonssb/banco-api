package br.com.ludsonbrandao.bancoapi.service;

import br.com.ludsonbrandao.bancoapi.BeforeTest;
import br.com.ludsonbrandao.bancoapi.controller.dto.SaldoContaDTO;
import br.com.ludsonbrandao.bancoapi.model.Conta;
import br.com.ludsonbrandao.bancoapi.repository.ContaRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class ContaServiceTest extends BeforeTest {

    @InjectMocks
    ContaService contaService;

    @Mock
    LogTransacaoService logTransacaoService;

    @Mock
    ContaRepository contaRepository;

    private static final long UM = 1;

    @Test
    public void buscaPorNumeroDeConta(){
        Conta conta = new Conta();
        Mockito.when(contaRepository.findByNumero(UM)).thenReturn(Optional.of(conta));
        assertNotNull(conta);
    }

    @Test
    public void saquePorConta(){
        Long numeroConta = 1676L;
        double valorSaque = 1000;
        double saldoInicial = 2500;

        Conta conta = new Conta();
              conta.setSaldo(saldoInicial);
              conta.setBloqueio(Boolean.FALSE);
              conta.setNumero(1676L);

        Mockito.when(contaRepository.findByNumero(numeroConta)).thenReturn(Optional.of(conta));
        Mockito.when(contaRepository.save(any())).thenReturn(conta);
        Mockito.when(logTransacaoService.salvaLogTransacao(1L,LocalDate.now(),1L,1231,"")).thenReturn(any());

        Optional<Conta> retorno = contaService.saquePorConta(numeroConta, valorSaque);

        boolean saldoContaMenorDepoisDoSaque = saldoInicial > retorno.get().getSaldo();

        assertEquals(numeroConta, retorno.get().getNumero());
        assertFalse(retorno.get().getBloqueio());
        assertTrue(saldoContaMenorDepoisDoSaque);
    }

    @Test
    public void depositoPorConta(){
        Long numeroConta = 1676L;
        double valorDeposito = 1520;
        double saldoInicial = 3000;

        Conta conta = new Conta();
        conta.setSaldo(saldoInicial);
        conta.setBloqueio(Boolean.FALSE);
        conta.setNumero(1676L);

        Mockito.when(contaRepository.findByNumero(numeroConta)).thenReturn(Optional.of(conta));
        Mockito.when(contaRepository.save(any())).thenReturn(conta);
        Mockito.when(logTransacaoService.salvaLogTransacao(1L,LocalDate.now(),1L,1231,"")).thenReturn(any());

        Optional<Conta> retorno = contaService.despositoPorConta(numeroConta, valorDeposito);

        boolean saldoContaMenorDepoisDoDeposito = saldoInicial < retorno.get().getSaldo();

        assertEquals(numeroConta, retorno.get().getNumero());
        assertFalse(retorno.get().getBloqueio());
        assertTrue(saldoContaMenorDepoisDoDeposito);
    }

    @Test
    public void bloqueioConta(){
        Long numeroConta = 1676L;
        Boolean bloq = Boolean.TRUE;

        Conta conta = new Conta();
            conta.setNumero(1676L);

        Mockito.when(contaRepository.findByNumero(numeroConta)).thenReturn(Optional.of(conta));
        Mockito.when(contaRepository.save(any())).thenReturn(conta);
        Mockito.when(logTransacaoService.salvaLogTransacao(1L,LocalDate.now(),1L,1231,"")).thenReturn(any());

        Optional<Conta> retorno = contaService.atualizarBloqueio(numeroConta, bloq);

        assertEquals(numeroConta, retorno.get().getNumero());
        assertTrue(retorno.get().getBloqueio());
    }

    @Test
    public void somaTotal(){
        Conta conta1 = new Conta();
        conta1.setSaldo(1490);
        Conta conta2 = new Conta();
        conta2.setSaldo(1640);
        Conta conta3 = new Conta();
        conta3.setSaldo(1050);

        List <Conta> contas = new ArrayList<>();
        contas.add(conta1);
        contas.add(conta2);
        contas.add(conta3);

        Mockito.when(contaRepository.findAll()).thenReturn(contas);

        SaldoContaDTO retorno = contaService.somaTotal();

        double somaInicial = conta1.getSaldo() + conta2.getSaldo() + conta3.getSaldo();

        assertEquals(somaInicial, retorno.getSaldo(), 0.0001);

    }

}
