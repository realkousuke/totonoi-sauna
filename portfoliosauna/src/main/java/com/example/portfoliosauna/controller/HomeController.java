package com.example.portfoliosauna.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.portfoliosauna.entity.House;
import com.example.portfoliosauna.repository.HouseRepository;

@Controller
public class HomeController {
	 private final HouseRepository houseRepository;        
    
     public HomeController(HouseRepository houseRepository) {
         this.houseRepository = houseRepository;            
     }    
    
	@GetMapping("/")
	 public String index(Model model) {
        List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc();
        model.addAttribute("newHouses", newHouses);
        
          return "index";
     }   
}
