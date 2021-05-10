package br.com.malysonsouza.agropopshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.malysonsouza.agropopshop.repositories.DependenteRepository;

@Controller
@RequestMapping("/dependentes")
public class DependenteController {
    
    @Autowired
    DependenteRepository dependenteRepo;

    

}
