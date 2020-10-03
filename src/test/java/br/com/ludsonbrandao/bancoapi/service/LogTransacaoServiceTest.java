package br.com.ludsonbrandao.bancoapi.service;

import br.com.ludsonbrandao.bancoapi.BeforeTest;
import br.com.ludsonbrandao.bancoapi.repository.LogTransacaoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

public class LogTransacaoServiceTest extends BeforeTest {

    @InjectMocks
    LogTransacaoService logTransacaoService;

    @Mock
    LogTransacaoRepository logTransacaoRepository;

}
