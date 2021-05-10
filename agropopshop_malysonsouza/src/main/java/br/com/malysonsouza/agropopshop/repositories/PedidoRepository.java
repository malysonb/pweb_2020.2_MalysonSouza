package br.com.malysonsouza.agropopshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.malysonsouza.agropopshop.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
