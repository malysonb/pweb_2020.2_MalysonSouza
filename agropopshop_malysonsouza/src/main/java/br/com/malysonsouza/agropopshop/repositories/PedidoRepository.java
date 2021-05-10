package br.com.malysonsouza.agropopshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.malysonsouza.agropopshop.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
