package minesweeper.module;

/* Ez az osztály reprezentál egy játékot.  */

public class Game {
	private String playerName;
	private Integer difficulty;
	private Integer size;
	private Map mineMap;
	private Integer time;
	private Boolean isCheated;
	
	/* 
	 * Létrehozzuk a játékot. Beállítjuk a paramétereket, létrehozunk egy 
	 * megfelelő Map-et, és az időt 0-ra állítjuk.
	 */
	public Game (String playerName, Integer difficulty, Integer size) {
		this.playerName = playerName;
		this.difficulty = difficulty;
		this.size = size;
		this.mineMap = new Map(size,difficulty);
		this.time = 0;
		this.isCheated = false;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public int getTime() {
		return time;
	}
	
	/* Növeljük az időt 1-gyel.
	 * Egy időzített ActionListener fogja hívogatni. 
	 * */
	public void incrementTime() {
		this.time += 1;
	}
	
	public Map getMap() {
		return mineMap;
	}
	
	public String getName() {
		return this.playerName;
	}
	
	/* Leellenőrizzük, hogy megnyertük-e ezt a játékot */
	public Boolean gameWon() {
		/* Akkor nyertünk, ha ugyanannyi felderítetlen mező van, mint ahány aknás mező. */
		if(mineMap.getUnknownFields().equals(mineMap.getMineCount())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Integer getDifficulty() {
		return this.difficulty;
	}
	
	/* A játék éppen "csaló" állapotban van-e */
	public void toggleCheated() {
		this.isCheated = !this.isCheated;
	}
	
	public Boolean getCheated() {
		return this.isCheated;
	}
}
