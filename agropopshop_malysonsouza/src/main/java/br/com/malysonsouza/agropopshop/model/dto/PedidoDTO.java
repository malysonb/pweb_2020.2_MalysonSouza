package br.com.malysonsouza.agropopshop.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    List<ProdutoDTO> produtosList;
    private String pagamento;
}
