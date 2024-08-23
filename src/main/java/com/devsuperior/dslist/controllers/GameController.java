package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.services.GameService;

//Classe responsável por ser a 'porta' de entrada para o backend

@RestController
@RequestMapping (value = "/games")  //Especificação do nome do endpoint
public class GameController {

	@Autowired //"injeção" de uma instância da classe 'GameService' na classe 'GameController'
	private GameService gameService;

	//Declarando formato das requisições que serão retornadas pelo 'Postman'
	//Busca pelo 'id'
	@GetMapping(value = "/{id}")
	public GameDTO findById(@PathVariable Long id){
		 return gameService.findById(id);
	}
	//Busca por todos os dados
	@GetMapping
	public List<GameMinDTO> findAll(){
		 return gameService.findAll();
	}
}
