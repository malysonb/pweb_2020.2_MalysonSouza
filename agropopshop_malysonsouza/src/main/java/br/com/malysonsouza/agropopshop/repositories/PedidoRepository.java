package br.com.malysonsouza.agropopshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.malysonsouza.agropopshop.model.Cliente;
import br.com.malysonsouza.agropopshop.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByClientePedido(Cliente cliente);
}
