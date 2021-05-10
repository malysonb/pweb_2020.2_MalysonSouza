package br.com.malysonsouza.agropopshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.malysonsouza.agropopshop.model.ItemPedido;
import br.com.malysonsouza.agropopshop.model.Pedido;
import br.com.malysonsouza.agropopshop.model.Produto;
import br.com.malysonsouza.agropopshop.repositories.PedidoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepo;

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") long id){
        Pedido pedido = pedidoRepo.findById(id)
                        .orElseThrow(() -> new IllegalAccessError("Pedido não encontrado! id: " + id));
        ModelAndView mav = new ModelAndView("cliente/detalharPedido");
        mav.addObject("pedido", pedido);
        mav.addObject("cliente", pedido.getClientePedido());
        mav.addObject("itempedidos", pedido.getPedidoProduto());
        mav.addObject("produtos", converter(pedido.getPedidoProduto()));
        return mav;
    }

    @PostMapping("/cancelar/{id}")
    public ModelAndView cancelar(@PathVariable("id") long id){
        Pedido pedido = pedidoRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        pedido.setStatus("Cancelado");
        pedidoRepo.save(pedido);
        return new ModelAndView("redirect:/pedidos/" + id);
    }

    private List<Produto> converter(List<ItemPedido> itemPedido){
        return itemPedido.stream().map(item -> item.getProduto()).collect(Collectors.toList());
    }
}
