package br.com.malysonsouza.agropopshop.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
		mav.addObject("produtos", produtos);

		return mav;
	}
	
	@GetMapping("/adicionar")
	public ModelAndView formAdicionarProduto(){
		ModelAndView mav = new ModelAndView("formProduto");
		Boolean isEdit = false;
		mav.addObject("isEdit", isEdit);
		mav.addObject("produto", new Produto());
		return mav;
	}
	
	@PostMapping("/adicionar")
	@Transactional
	public String adicionarProduto(@RequestParam("imagem") MultipartFile imagem, Produto produto) {
		if(!imagem.isEmpty()){
			byte[] bytes = new byte[(int) imagem.getSize()];
			try {
				bytes = imagem.getBytes();
			} catch (Exception e) {
				System.out.println("opa deu errado" + e);
			}
			produto.setFoto(bytes);
		}
		produtoRepo.save(produto);
		return "redirect:/produtos";
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView formEditarProduto(@PathVariable("id") long id) {
		Produto produto = produtoRepo.findById(id).orElseThrow(
				() -> new IllegalAccessError("O produto com id: " + id + " não existe!"));
		ModelAndView mav = new ModelAndView("formProduto");
		Boolean isEdit = true;
		mav.addObject("isEdit", isEdit);
		mav.addObject("produto", produto);
		return mav;
	}
	
	@PostMapping("/editar/{id}")
	public String editarProduto(@RequestParam("imagem") MultipartFile imagem, @PathVariable("id") long id, Produto produto) {
		if(!imagem.isEmpty()){
			byte[] bytes = new byte[(int) imagem.getSize()];
			try {
				bytes = imagem.getBytes();
			} catch (Exception e) {
				System.out.println("opa deu errado" + e);
			}
			produto.setFoto(bytes);
		}
		produtoRepo.save(produto);
		return "redirect:/produtos";
	}
	
	@GetMapping("/remover/{id}")
	public ModelAndView removerProduto(@PathVariable("id") long id) {
		Produto aRemover = produtoRepo.findById(id).orElseThrow(
				() -> new IllegalAccessError("O produto com id: " + id + " não existe!"));
		produtoRepo.delete(aRemover);
		return new ModelAndView("redirect:/produtos");
	}
	
}
