package com.devsuperior.dslist.projections;

//Interface relacionada a consulta SQL feita em 'GameRepository'

public interface GameMinProjection {
	Long getId();
	String getTitle();
	Integer getGameYear();
	String getImgUrl();
	String getShortDescription();
	Integer getPosition();
}
