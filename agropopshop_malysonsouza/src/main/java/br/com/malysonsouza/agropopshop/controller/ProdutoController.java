package br.com.malysonsouza.agropopshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.malysonsouza.agropopshop.model.Produto;
import br.com.malysonsouza.agropopshop.repositories.ProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepo;
	
	@GetMapping
	public ModelAndView listarProdutos(){
		List<Produto> produtos = produtoRepo.findAll();
		ModelAndView mav = new ModelAndView("listarProdutos");
		mav.addObject("produto", produtos);
		return mav;
	}
	
	@GetMapping("/adicionar")
	public ModelAndView formAdicionarProduto(){
		ModelAndView mav = new ModelAndView("formProduto");
		
		mav.addObject(new Produto());
		return mav;
	}
	
	@PostMapping("/adicionar")
	public String adicionarProduto(Produto produto) {
		produtoRepo.save(produto);
		return "redirect:/produtos";
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView formEditarProduto(@PathVariable("id") long id) {
		Produto produto = produtoRepo.findById(id).orElseThrow(
				() -> new IllegalAccessError("O produto com id: " + id + " não existe!"));
		ModelAndView mav = new ModelAndView("formProduto");
		mav.addObject(produto);
		return mav;
	}
	
	@PostMapping("/editar/{id}")
	public String editarProduto(@PathVariable("id") long id, Produto produto) {
		produtoRepo.save(produto);
		return "redirect:/produtos";
	}
	
}
