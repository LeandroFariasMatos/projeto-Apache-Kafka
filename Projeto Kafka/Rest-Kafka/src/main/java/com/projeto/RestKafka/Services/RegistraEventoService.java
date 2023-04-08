package com.projeto.RestKafka.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistraEventoService {
    private final KafkaTemplate<Object,Object> template;

    public <T> void adicionarEvento(String topico, T dados){
        template.send(topico,dados);
    }

    @KafkaListener(topics = "PedidoSalvo",groupId = "MicrosservicoPedidos")
    public void executar(ConsumerRecord<String,String> record){
        log.info("Chave = {}",record.key());
        log.info("Headers = {}",record.headers());
        log.info("Particao = {}",record.partition());

        String strDados = record.value();
        log.info(strDados);
    }
}
