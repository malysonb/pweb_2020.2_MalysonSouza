package br.com.malysonsouza.agropopshop.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.malysonsouza.agropopshop.model.Cliente;
import br.com.malysonsouza.agropopshop.model.ItemPedido;
import br.com.malysonsouza.agropopshop.model.Pedido;
import br.com.malysonsouza.agropopshop.model.Produto;
import br.com.malysonsouza.agropopshop.model.dto.PedidoDTO;
import br.com.malysonsouza.agropopshop.model.dto.ProdutoDTO;
import br.com.malysonsouza.agropopshop.repositories.ClienteRepository;
import br.com.malysonsouza.agropopshop.repositories.PedidoItemRepository;
import br.com.malysonsouza.agropopshop.repositories.PedidoRepository;
import br.com.malysonsouza.agropopshop.repositories.ProdutoRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	ProdutoRepository produtoRepo;
	@Autowired
	PedidoRepository pedidoRepo;
	@Autowired
	PedidoItemRepository itemPedidoRepo;

	@GetMapping
	public ModelAndView listarClientes() {
		List<Cliente> lista = clienteRepo.findAll();
		ModelAndView mav = new ModelAndView("listarClientes");
		mav.addObject("clientes", lista);
		return mav;
	}

	@GetMapping("/adicionar")
	public ModelAndView formAdicionarCliente() {
		ModelAndView mav = new ModelAndView("formCliente");
		Boolean isEdit = false;
		mav.addObject("isEdit", isEdit);
		mav.addObject(new Cliente());
		return mav;
	}

	@PostMapping("/adicionar")
	public String adicionarCliente(Cliente p) {
		this.clienteRepo.save(p);
		return "redirect:/clientes";
	}

	@GetMapping("/compras/{id}")
	public ModelAndView formCompras(@PathVariable("id") long id) {

		ModelAndView mav = new ModelAndView("cliente/compras");
		Cliente cliente = clienteRepo.findById(id)
				.orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
		mav.addObject("cliente", cliente);
		List<Produto> produtos = produtoRepo.findAll(Sort.by("nomeProduto").ascending());
		mav.addObject("produtos", produtos);
		mav.addObject("dto", new PedidoDTO());
		return mav;
	}

	@GetMapping("/compras")
	public ModelAndView ComprasSemID() {

		ModelAndView mav = new ModelAndView("cliente/select");
		List<Cliente> clientes = clienteRepo.findAll();
		mav.addObject("clientes", clientes);
		mav.addObject("path", "clientes/compras");
		return mav;
	}

	@GetMapping("/pedidos/{id}")
    public ModelAndView getById(@PathVariable("id") long id){
        ModelAndView mav = new ModelAndView("cliente/listarPedidos");
		Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        mav.addObject("pedidos", pedidoRepo.findByClientePedido(cliente));
        mav.addObject("cliente", cliente);
        return mav;
    }

	@GetMapping("/pedidos")
	public ModelAndView PedidosSemID() {

		ModelAndView mav = new ModelAndView("cliente/select");
		List<Cliente> clientes = clienteRepo.findAll();
		mav.addObject("clientes", clientes);
		mav.addObject("path", "clientes/pedidos");
		return mav;
	}

	@Transactional
	@PostMapping("/compras/{id}")
	public ModelAndView compras(@PathVariable("id") long id, PedidoDTO dto) {
		Cliente cliente = clienteRepo.findById(id)
				.orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
		Pedido pedido = new Pedido();
		pedido.setClientePedido(cliente);
		pedido.setDataVenda(LocalDate.now());
		pedido.setFormaPagamento(dto.getPagamento());
		pedido.setStatus("Efetuado");
		List<ItemPedido> itmped = converter(pedido,dto.getProdutosList());
		itemPedidoRepo.saveAll(itmped);
		pedidoRepo.save(pedido);
		pedido.setPedidoProduto(itmped);
		return new ModelAndView("redirect:/pedidos/" + pedido.getId());
	}



	@GetMapping("/editar/{id}")
	public ModelAndView formEditarCliente(@PathVariable("id") long id) {
		Cliente cliente = clienteRepo.findById(id)
				.orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
		ModelAndView mav = new ModelAndView("formCliente");
		Boolean isEdit = true;
		mav.addObject("isEdit", isEdit);
		mav.addObject(cliente);
		return mav;
	}

	@PostMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") long id, Cliente cliente) {
		this.clienteRepo.save(cliente);
		return new ModelAndView("redirect:/clientes");
	}

	@GetMapping("/remover/{id}")
	public ModelAndView removerCliente(@PathVariable("id") long id) {
		Cliente aRemover = clienteRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Este id é invalido: " + id));
		clienteRepo.delete(aRemover);
		return new ModelAndView("redirect:/clientes");
	}

	@Transactional
	private List<ItemPedido> converter(Pedido pedido, List<ProdutoDTO> produtos){
		List<ProdutoDTO> filtrado = produtos.stream().filter(c -> c.getId() != null).collect(Collectors.toList());
		return filtrado.stream().map(dto ->
		{
				Produto temp = produtoRepo.findById(Long.parseLong(dto.getId()))
				.orElseThrow(() -> new IllegalArgumentException("Este id de produto é invalido"));
				ItemPedido itemPedido = ItemPedido.builder()
					.produto(temp)
					.pedidoProduto(pedido)
					.quantidade(dto.getQuantidade())
					.preco(temp.getPreco())
					.total(temp.getPreco() * dto.getQuantidade())
					.build();
				return itemPedido;
		}).collect(Collectors.toList());
	}

}
