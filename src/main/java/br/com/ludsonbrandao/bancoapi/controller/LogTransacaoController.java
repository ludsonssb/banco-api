package br.com.ludsonbrandao.bancoapi.controller;

import br.com.ludsonbrandao.bancoapi.model.LogTransacao;
import br.com.ludsonbrandao.bancoapi.service.LogTransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("logTransacao")
public class LogTransacaoController {

    @Autowired
    private LogTransacaoService logTransacaoService;

    @GetMapping("/log")
    List<LogTransacao> logs(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDe,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAte,
                            @RequestParam long conta){
        return logTransacaoService.recuperaLog(dataDe,dataAte,conta);
    }
}
