package br.com.malysonsouza.cadpessoas.controllers;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.malysonsouza.cadpessoas.model.Pessoa;
import br.com.malysonsouza.cadpessoas.repositories.PessoaRepository;

@Controller
@RequestMapping("/")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepo;
	
	@GetMapping
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/listarPessoas")
	public ModelAndView listarPessoas() {
		List<Pessoa> lista = pessoaRepo.findAll();
		ModelAndView mav = new ModelAndView("listarPessoas");
		mav.addObject("pessoas", lista);
		return mav;
	}
	
	@GetMapping("/adicionarPessoa")
	public ModelAndView formAdicionarPessoa() {
		ModelAndView mav = new ModelAndView("adicionarPessoa");
		mav.addObject(new Pessoa());
		return mav;
	}
	
	@PostMapping("/adicionarPessoa")
	public String adicionarPessoa(Pessoa p) {
		this.pessoaRepo.save(p);
		return "redirect:/listarPessoas";
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView formEditarPessoa(@PathVariable("id") long id) {
		Pessoa pessoa = pessoaRepo.findById(id).orElseThrow(()-> new IllegalAddException("Este id é invalido: " + id));
		ModelAndView mav = new ModelAndView("editarPessoa");
		mav.addObject(pessoa);
		return mav;
	}
	
	@PostMapping("/editar/{id}")
	public ModelAndView editarPessoa(@PathVariable("id") long id, Pessoa pessoa)
	{
		this.pessoaRepo.save(pessoa);
		return new ModelAndView("redirect:/listarPessoas");
	}
	
	@GetMapping("/remover/{id}")
	public ModelAndView removerPessoa(@PathVariable("id") long id) {
		Pessoa aRemover = pessoaRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Este id é invalido: " + id));
		pessoaRepo.delete(aRemover);
		return new ModelAndView("redirect:/listarPessoas");
	}
	
}
