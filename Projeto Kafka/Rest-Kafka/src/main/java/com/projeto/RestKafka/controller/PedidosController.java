package com.projeto.RestKafka.controller;

import com.projeto.RestKafka.Services.RegistraEventoService;
import com.projeto.RestKafka.models.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PedidosController {
    private final RegistraEventoService eventoService;

    @PostMapping("api/v1/salvarPedido")
    public ResponseEntity<String> salvarPedido(@RequestBody Pedido pedido){
        eventoService.adicionarEvento("SalvarPedido",pedido);
        return ResponseEntity.ok("Pedido Solicitado com Sucesso");
    }

}
