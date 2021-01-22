package minesweeper.module;

import java.awt.Dimension;
import java.util.Random;

/*
 *  Egy aknamezőt reprezentál. Ez az osztály tartja nyilván a mezőket, és hogy 
 *  hány felderítetlen mező van.
 *  */

public class Map {
	private Integer size;
	private Field[][] fields;
	private Integer mineChance;
	private Integer mineCount = 0;
	private Integer unknownFields;
	
	/* Itt hozzuk létre az aknamezőt */
	public Map(Integer size, Integer difficulty) {
		this.size = size;
		/* Tehát egy mezőn az akna valószínűsége 7%-tól 35%-ig terjedhet, a nehézségtől függően.*/
		this.mineChance = difficulty*7;
		/* A könnyebb programozás érdekében a pálya szélén lesznek határmezők,
		 * amik nem látszanak, de a pálya generálását megkönnyítik, ezért size+2 
		 * méretű aknamezőt hozunk létre. */
		this.fields = new Field[size+2][size+2];
		this.unknownFields = size*size;
		
		
		Random rand = new Random();
		
		/* Példányosítjuk a mezőket, egyelőre mindegyik aknamentes. 
		 * Azért kell ezt előre megtegyük, hogy a szélső mezőket üresre
		 * inicializáljuk.
		 * */
		for(int i=0; i < this.size+2; ++i) {
			for(int j=0; j < this.size+2; ++j) {
				fields[i][j] = new Field(false, new Dimension(i-1,j-1));
				
			}
		}
		
		/* Minden mezőre generálunk 0 és 100 között egy véletlen számot,
		 * és ha ez kisebb, mint a mineChance, akkor a mezőre aknát teszünk.
		 * A szélső mezőkhöz nem nyúlunk.
		 *  */
		for(int i=1; i < this.size+1; ++i) {
			for(int j=1; j < this.size+1; ++j) {
				Integer random = rand.nextInt(100);
				if(random < mineChance) {
					fields[i][j] = new Field(true, new Dimension(i-1,j-1));
					++mineCount;
				}
				else {
					fields[i][j] = new Field(false, new Dimension(i-1,j-1));
				}
			}
		}
		
		/* A pálya minden mezején végigmegyünk, és megszámoljuk, hogy hány mező van körülötte. */
		for(int i=1; i < this.size+1; ++i) {			// Minden mezőre
			for(int j=1; j < this.size+1; ++j) {		//
				Integer howManyMinesAround = 0;
				for(int k = i-1; k < i+2; ++k) { 		// 	Az összes körülötte lévő mezőre, és saját magára is
					for(int m = j-1; m < j+2; ++m) {	// 	(összesen 9 mező)
						if(fields[k][m].getMine()) {	// 	Ha ott akna van
							++howManyMinesAround; 		//	A körülötte lévő aknák számát nyilvántartó változót növeljük.
						}
					}
				}
				if(fields[i][j].getMine()) { // Hogyha saját magán akna volt, akkor azt nem szabadott volna beleszámolni (de így szebb lett a ciklus)
					--howManyMinesAround;	 // Ezért kivonunk egyet.
				}
				fields[i][j].setHowManyMinesAround(howManyMinesAround);
			}
		}
		
		/* Minden mező Map-je ez a Map lesz. */
		for(int i=1; i < this.size+1; ++i) {
			for(int j=1; j < this.size+1; ++j) {
				fields[i][j].setMap(this);
			}
		}
	}
	
	public Field getField(Integer x, Integer y) {
		return fields[x][y];
	}
	
	public Integer getMineCount() {
		return mineCount;
	}
	
	public Integer getUnknownFields() {
		return unknownFields;
	}
	
	public void decrementUnknownFields() {
		this.unknownFields -= 1;
	}
	
	/* Rekurzív függvény, amely felderíti az egybefüggő 0-s mezőket.
	 * A sok feltétel azért van, mert figyelni kell a pálya határaira.
	 * Például a pálya bal felső sarkában lévő mezőnek csak a tőle jobbra, lent, vagy jobbra lent lévő mezőire szabad meghívni.
	 * Így erre csak az 5., 7. és 8. feltétel fog igazat adni. */
	public void fillZero(Field f) {
		Dimension pos = f.getPos();
		if(f.getMinesAroundNumber() != 0) {
			return;
		}
		this.revealAround(this.getField(f.getPos().width + 1, f.getPos().height + 1));
		
		if(pos.width > 0 && pos.height > 0 && !this.getField(pos.width, pos.height).getZeroed()) {fillZero(this.getField(pos.width, pos.height));}
		if(pos.height > 0 && !this.getField(pos.width+1, pos.height).getZeroed()) {fillZero(this.getField(pos.width+1, pos.height));}
		if(pos.height > 0 && pos.width < this.size - 1 && !this.getField(pos.width+2, pos.height).getZeroed()) {fillZero(this.getField(pos.width+2, pos.height));}
		if(pos.width > 0 && !this.getField(pos.width, pos.height+1).getZeroed()) {fillZero(this.getField(pos.width, pos.height+1));}
		if(pos.width < this.size - 1 && !this.getField(pos.width+2, pos.height+1).getZeroed()) {fillZero(this.getField(pos.width+2, pos.height+1));}
		if(pos.width > 0 && pos.height < this.size - 1 && !this.getField(pos.width, pos.height+2).getZeroed()) {fillZero(this.getField(pos.width, pos.height+2));}
		if(pos.height < this.size - 1 && !this.getField(pos.width+1, pos.height+2).getZeroed()) {fillZero(this.getField(pos.width+1, pos.height+2));}
		if(pos.height < this.size -1 && pos.width < this.size - 1 && !this.getField(pos.width+2, pos.height+2).getZeroed()) {fillZero(this.getField(pos.width+2, pos.height+2));}
	}
	
	/* Egy mező körüli összes mezőt felderíti. Figyelni kell a pálya széleire. */
	public void revealAround(Field f) {
		f.setZeroed(true);
		Dimension pos = f.getPos();
		if(pos.width > 0 && pos.height > 0) {fields[pos.width][pos.height].reveal();}
		if(pos.height > 0) {fields[pos.width+1][pos.height].reveal();}
		if(pos.height > 0 && pos.width < this.size - 1) {fields[pos.width+2][pos.height].reveal();}
		if(pos.width > 0) {fields[pos.width][pos.height+1].reveal();}
		if(pos.width < this.size - 1) {fields[pos.width+2][pos.height+1].reveal();}
		if(pos.width > 0 && pos.height < this.size - 1) {fields[pos.width][pos.height+2].reveal();}
		if(pos.height < this.size - 1) {fields[pos.width+1][pos.height+2].reveal();}
		if(pos.height < this.size -1 && pos.width < this.size - 1) {fields[pos.width+2][pos.height+2].reveal();}
	}
	
}
