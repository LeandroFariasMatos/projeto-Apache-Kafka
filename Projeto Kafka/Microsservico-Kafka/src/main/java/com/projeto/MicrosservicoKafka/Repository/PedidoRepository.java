package com.projeto.MicrosservicoKafka.Repository;

import com.projeto.MicrosservicoKafka.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
