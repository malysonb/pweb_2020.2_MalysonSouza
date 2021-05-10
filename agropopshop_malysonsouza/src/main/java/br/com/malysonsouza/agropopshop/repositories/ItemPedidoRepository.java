package br.com.malysonsouza.agropopshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.malysonsouza.agropopshop.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{
    
}
