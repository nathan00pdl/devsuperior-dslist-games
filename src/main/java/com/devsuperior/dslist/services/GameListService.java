package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.reppositories.GameListRepository;
import com.devsuperior.dslist.reppositories.GameRepository;



@Service  //'Registrando' a classe no sistemas   
public class GameListService {

	@Autowired
	private GameListRepository gameListRepository;
	
	
	@Transactional(readOnly = true)
	public List<GameListDTO> findAll(){
		List<GameList> result = gameListRepository.findAll();  //'findAll()' é um método disponibilizado pelo próprio Spring
		return result.stream().map(x -> new GameListDTO(x)).toList();
	}
	
	
	@Autowired 
	private GameRepository gameRepository;
	
	//Lógica de alteração das posições dos jogos
	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex) {
		
		//Buscando da memória a lista de jogos
		List<GameMinProjection> list = gameRepository.searchByList(listId);
		
		//Alteração de posição -> remove o jogo de uma posição (sourceIndex) e insere na posição escolhida (destinationIndex)
		GameMinProjection obj = list.remove(sourceIndex);
		list.add(destinationIndex, obj);
		
		//Declaração de variáveis para guardar a posição mínima e máxima entre 'sourceIndex' e 'destinationIndex'
		int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;
		
		for(int i=min ; i<=max ; i++) {
			gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
		}
	}
}
