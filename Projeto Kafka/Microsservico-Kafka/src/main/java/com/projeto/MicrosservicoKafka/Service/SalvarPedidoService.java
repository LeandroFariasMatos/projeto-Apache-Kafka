package com.projeto.MicrosservicoKafka.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.MicrosservicoKafka.Repository.PedidoRepository;
import com.projeto.MicrosservicoKafka.models.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalvarPedidoService {

    private final PedidoRepository pedidoRepository;
    private final KafkaTemplate<Object,Object> template;

    @KafkaListener(topics = "SalvarPedido",groupId = "MicrosservicoSalvaPedido")
    public void executar(ConsumerRecord<String,String> record){
        log.info("Chave = {}",record.key());
        log.info("Headers = {}",record.headers());
        log.info("Particao = {}",record.partition());

        String strDados = record.value();

        ObjectMapper mapper = new ObjectMapper();
        Pedido pedido = null;
        try {
            pedido = mapper.readValue(strDados,Pedido.class);
        } catch (JsonProcessingException e) {
            log.error("Falha ao converter evento [dados={}]",strDados,e);
            return;
        }
        log.info("Evento recebido = {}",strDados);

        log.info("Gravando no banco de dados");
        gravarBancoDeDados(pedido);

        log.info("Respondendo na fila que evento foi salvo com sucesso no banco de dados");
        adicionarEvento("PedidoSalvo","Pedido salvo e realizado com Sucesso");




    }

    private void gravarBancoDeDados(Pedido pedido){

        pedidoRepository.save(pedido);
    }

    public <T> void adicionarEvento(String topico, T dados){
        template.send(topico,dados);
    }
}
