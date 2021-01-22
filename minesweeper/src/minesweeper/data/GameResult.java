package minesweeper.data;

import java.io.Serializable;

/* 
 * Egy sikeres játék eredményének adatait tároló osztály
 * Csak adattagok, konstruktor, getter-setterek
 * */

public class GameResult implements Serializable {

	private Integer mapSize;
	private Integer difficulty;
	private Integer time;
	private String name;

	public GameResult(Integer mapSize, Integer difficulty, Integer time, String name) {
		this.name = name;
		this.difficulty = difficulty;
		this.mapSize = mapSize;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getMapSize() {
		return mapSize;
	}

	public void setMapSize(Integer mapSize) {
		this.mapSize = mapSize;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

}
