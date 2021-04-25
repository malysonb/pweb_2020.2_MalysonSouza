package br.com.malysonsouza.agropopshop.controller;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.malysonsouza.agropopshop.model.Cliente;
import br.com.malysonsouza.agropopshop.repositories.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepo;
	
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
	
	@GetMapping("/editar/{id}")
	public ModelAndView formEditarCliente(@PathVariable("id") long id) {
		Cliente cliente = clienteRepo.findById(id).orElseThrow(()-> new IllegalAddException("Este id é invalido: " + id));
		ModelAndView mav = new ModelAndView("formCliente");
		Boolean isEdit = true;
		mav.addObject("isEdit", isEdit);
		mav.addObject(cliente); 
		return mav;
	}
	
	@PostMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") long id, Cliente cliente)
	{
		this.clienteRepo.save(cliente);
		return new ModelAndView("redirect:/clientes");
	}
	
	@GetMapping("/remover/{id}")
	public ModelAndView removerCliente(@PathVariable("id") long id) {
		Cliente aRemover = clienteRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Este id é invalido: " + id));
		clienteRepo.delete(aRemover);
		return new ModelAndView("redirect:/clientes");
	}
	
}
