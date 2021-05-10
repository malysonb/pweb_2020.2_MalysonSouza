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
import br.com.malysonsouza.agropopshop.model.Dependente;
import br.com.malysonsouza.agropopshop.repositories.ClienteRepository;
import br.com.malysonsouza.agropopshop.repositories.DependenteRepository;

@Controller
@RequestMapping("/dependentes")
public class DependenteController {
    
    @Autowired
    ClienteRepository clienteRepo;

    @Autowired
    DependenteRepository dependenteRepo;

    @GetMapping("/{id}")
    public ModelAndView getClienteDependentes(@PathVariable("id") long id){
        ModelAndView mav = new ModelAndView("listarDependentes");
        Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        mav.addObject("cliente", cliente);
        mav.addObject("dependentes", cliente.getDependentes());
        return mav;
    }

    @GetMapping("/adicionar/{id}")
    public ModelAndView formAddDependente(@PathVariable("id") long id){
        ModelAndView mav = new ModelAndView("formDependente");
        Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        mav.addObject("dependente", new Dependente());
        mav.addObject("isEdit", false);
        mav.addObject("cliente", cliente);

        return mav;
    }

    @PostMapping("/adicionar/{id}")
	public String adicionarCliente(@PathVariable("id") long id, Dependente d) {
        d.setId(0);
        Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        d.setClienteDependente(cliente);
        List<Dependente> dependentes = cliente.getDependentes();
		this.dependenteRepo.save(d);
        this.clienteRepo.save(cliente);
        cliente.setDependentes(dependentes);
		return "redirect:/dependentes/" + id;
	}

    @GetMapping("/remover/{id}")
    public String removerDependente(@PathVariable("id") long id){
        Dependente dependente = dependenteRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        Cliente cliente = dependente.getClienteDependente();
        cliente.getDependentes().remove(dependente);
        dependenteRepo.delete(dependente);
        return "redirect:/dependentes/" + cliente.getId();
    }

    @GetMapping("/editar/{id}")
	public ModelAndView formEditarDependente(@PathVariable("id") long id) {
        Dependente dependente = dependenteRepo.findById(id)
				.orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        Cliente cliente = clienteRepo.findById(dependente.getClienteDependente().getId())
				.orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
		ModelAndView mav = new ModelAndView("formDependente");
		mav.addObject("isEdit", true);
		mav.addObject("cliente", cliente);
        mav.addObject("dependente", dependente);
		return mav;
	}

    @PostMapping("/editar/{id}")
	public String editarDependente(@PathVariable("id") long id, Dependente d) {
        Dependente dependente = dependenteRepo.findById(id).orElseThrow(() -> new IllegalAddException("Este id é invalido: " + id));
        d.setClienteDependente(dependente.getClienteDependente());
		this.dependenteRepo.save(d);
		return "redirect:/dependentes/" + d.getClienteDependente().getId();
	}
}
