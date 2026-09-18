package com.devsuperior.dslist.projections;

// projection for the custom query in GameRepository: only the columns a list view needs

public interface GameMinProjection {
	Long getId();
	String getTitle();
	Integer getGameYear();
	String getImgUrl();
	String getShortDescription();
	Integer getPosition();
}
