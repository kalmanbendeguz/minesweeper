package minesweeper.gui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import minesweeper.module.Field;

/* Ez az osztály egy-egy mezőhöz tartozó gombot reprezentál a grafikus felületen.
 * Minden példánya nyilvántartja a játék egy-egy mezőjét.
 * Ha a játékos interakcióba lép ezzel, tehát a mezőt reprezentáló gombbal, akkor
 * az adott interakciót az osztály végrehajtja a hozzá tartozó mezőn is.
 *   */

public class FieldButton extends JButton {

	private Field f;

	public FieldButton(Field f) {
		super();
		this.f = f;
		/* Alapból a mező ismeretlen, az ehhez tartozó ikont állítjuk be */
		Image img = Toolkit.getDefaultToolkit().getImage("unknown.png");
		this.setIcon(new ImageIcon(img));

	}

	public Field getField() {
		return f;
	}

	/* Felfed egy mezőt */
	public void discover() {
		f.reveal();
	}

	/* Megjelöl egy mezőt zászlóval */
	public void flag() {
		f.flag();
	}

	/* A mező állapota alapján frissíti a gomb ikonját */
	public void upDateIcon() {
		Image img;
		
		if (!f.getDiscovered()) {
			img = Toolkit.getDefaultToolkit().getImage("unknown.png");
			if (f.getFlag()) {
				img = Toolkit.getDefaultToolkit().getImage("flag.png");
			}
		} else {
			if (f.getMine()) {
				img = Toolkit.getDefaultToolkit().getImage("mine.png");
			} else {
				switch (f.getMinesAroundNumber()) {
				case 0:
					img = Toolkit.getDefaultToolkit().getImage("zero.png");
					break;
				case 1:
					img = Toolkit.getDefaultToolkit().getImage("one.png");
					break;
				case 2:
					img = Toolkit.getDefaultToolkit().getImage("two.png");
					break;
				case 3:
					img = Toolkit.getDefaultToolkit().getImage("three.png");
					break;
				case 4:
					img = Toolkit.getDefaultToolkit().getImage("four.png");
					break;
				case 5:
					img = Toolkit.getDefaultToolkit().getImage("five.png");
					break;
				case 6:
					img = Toolkit.getDefaultToolkit().getImage("six.png");
					break;
				case 7:
					img = Toolkit.getDefaultToolkit().getImage("seven.png");
					break;
				case 8:
					img = Toolkit.getDefaultToolkit().getImage("eight.png");
					break;
				default:
					img = Toolkit.getDefaultToolkit().getImage("empty.png");
					break;
				}

			}
		}
		//while(img==null){;}
		this.setIcon(new ImageIcon(img));
	}

}
