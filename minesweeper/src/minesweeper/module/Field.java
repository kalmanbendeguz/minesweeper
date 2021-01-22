package minesweeper.module;

import java.awt.Dimension;

/* Ez az osztály egy mezőt reprezentál. Változókban tárolja minden tulajdonságát.
 *	A tagfüggvényei segítségével tudjuk a mező állapotát változtatni.
 * */

public class Field {
	private Boolean isThereMine;
	private Boolean isDiscovered;
	private Boolean isFlagged;
	private Integer howManyMinesAround;
	private Dimension position;
	/* Kell a rekurzív függvénynek, hogy le tudja ellenőrizni, hogy ezt a mezőt meghívta-e már */
	private Boolean zeroed;
	
	/* 
	 * Eltároljuk a Map-et aminek a mező a része, hogy a mező felfedésénél
	 * tudjuk csökkenteni a Map "ismeretlen mezők" számlálóját.
	 */ 
	private Map m;
	
	/* Konstruktor: mező aknával, vagy anélkül. Beállítjuk a pozícióját is. */
	public Field(Boolean isThereMine, Dimension position) {
		this.isThereMine = isThereMine;
		this.isDiscovered = false;
		this.isFlagged = false;
		this.position = position;
		this.zeroed = false;
	}
	
	public void setMap(Map m) {
		this.m = m;
	}
	
	/* A mező felfedése, és a Map számlálójának dekrementálása. */
	public void reveal() {
		if(!this.isDiscovered) {
			this.isDiscovered = true;
			m.decrementUnknownFields();
		}
	}
	
	/* Visszaadja, hogy a mező körül hány mezőn van akna. */
	public Integer getMinesAroundNumber() {
		return howManyMinesAround;
	}
	
	public void setHowManyMinesAround(Integer howMany) {
		this.howManyMinesAround = howMany;
	}
	
	/* Zászlót teszünk a mezőre, vagy elveszünk onnan,
	 *  de csak akkor, ha még nincs felfedezve. Csak a Field osztály használhatja,
	 *  ezért privát.
	 *  */
	private void setFlag(Boolean b) {
		if(!(this.getDiscovered())) {
			this.isFlagged = b;
		}
		
	}
	
	public Boolean getFlag() {
		return this.isFlagged;
	}
	
	public Boolean getMine() {
		return this.isThereMine;
	}
	
	public Boolean getDiscovered() {
		return this.isDiscovered;
	}
	
	/* A mezőre zászlót teszünk, vagy elvesszük onnan. */
	public void flag() {
		this.setFlag(!this.getFlag());
	}
	
	public Dimension getPos() {
		return this.position;
	}
	
	public void setZeroed(Boolean what) {
		this.zeroed = what;
	}
	
	public Boolean getZeroed() {
		return zeroed;
	}
}
