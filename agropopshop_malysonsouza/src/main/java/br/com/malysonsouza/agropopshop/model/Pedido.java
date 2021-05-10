package br.com.malysonsouza.agropopshop.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate dataVenda;
    private String formaPagamento;
    private int qnt;

    @ManyToOne
    @JoinColumn(name = "id_cliente_pedido")
    private Cliente clientePedido;

    @OneToMany(mappedBy = "pedidoProduto")
    private List<ItemPedido> pedidoProduto;
}
