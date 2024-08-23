package com.devsuperior.dslist.reppositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dslist.entities.GameList;

//Essa interface representa um objeto de acesso a dados, promovendo consultas no banco de dados
//Ferramenta disponibilizada pelo próprio Spring: 'jpaRepository'

public interface GameListRepository extends JpaRepository<GameList, Long>{
	
	//Atualização da posição do jogo ao qual foi modificado
	@Modifying
	@Query(
		nativeQuery = true, 
		value = """
			UPDATE tb_belonging 
		    SET position = :newPosition 
			WHERE list_id = :listId AND game_id = :gameId
			""")
			
		void updateBelongingPosition(Long listId, Long gameId, Integer newPosition
	);
	
}
