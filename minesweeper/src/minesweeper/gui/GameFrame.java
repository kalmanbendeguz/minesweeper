package minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import minesweeper.module.Game;

/*
 * A játék frame-je. Az összes FieldButton-t kirajzolja, és mindegyikhez MouseListener-t tart nyilván.
 * Méri az időt, amit az ablakon megjelenít. Egy gombbal feladhatjuk a játékot.
 * Ha nyerünk vagy veszítünk, azt is lekezeli.
 */

public class GameFrame extends JFrame {

	private MouseListener[][] fma;
	private FieldButton[][] b_fields;
	private JPanel jp;
	/* Nyilvántartjuk a játékot, mint adattagot */
	private Game g;
	private Timer timer;
	private JLabel clockLabel;

	public GameFrame(Game g) {
		this.g = g;
		this.setTitle("Game");
		this.setSize(g.getSize() * 23, g.getSize() * 23 + 70);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		JPanel felso = new JPanel();
		this.add(felso, BorderLayout.NORTH);
		BorderLayout blayout = new BorderLayout();
		blayout.setVgap(5);
		felso.setLayout(blayout);
		
		/* Elindítunk egy időzítőt, amely másodpercenként aktiválódik.
		 * Erről tudomást szerez a TimerListener, és inkrementálja a játékhoz tartozó
		 * idő értékét, majd megjeleníti a clockLabel-en, amit az aknamező fölé, középre helyezünk el. */
		clockLabel = new JLabel();
		ActionListener tl = new TimerListener();
		timer = new Timer(1000, tl);
		timer.setInitialDelay(0);
		timer.start();
		JPanel clockPanel = new JPanel();
		felso.add(clockPanel,BorderLayout.CENTER);
		clockPanel.add(clockLabel, BorderLayout.CENTER);
		
		/* Létrehozzuk a menüsort, két gomb lesz benne:
		 * egy "csalás" gomb és egy "feladás" gomb */
		JMenuBar menuBar = new JMenuBar();
		this.add(menuBar);
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		JMenuItem giveUpButton = new JMenuItem("Give up :(", KeyEvent.VK_T);
		menu.add(giveUpButton);
		ActionListener gubal = new GiveUpButtonListener();
		giveUpButton.addActionListener(gubal);
		this.setJMenuBar(menuBar);
		JMenuItem cheatButton = new JMenuItem("Cheat!", KeyEvent.VK_T);
		menu.add(cheatButton);
		ActionListener cbal = new CheatButtonListener();
		cheatButton.addActionListener(cbal);

		

		jp = new JPanel();
		jp.setSize(g.getSize() * 20, g.getSize() * 20);
		jp.setLayout(new GridLayout(g.getSize(), g.getSize()));
		this.add(jp, BorderLayout.CENTER);

		/* Megadjuk a mezők és a listenerek számát */
		b_fields = new FieldButton[g.getSize()][g.getSize()];
		fma = new FieldMouseAction[g.getSize()][g.getSize()];

		for (int i = 1; i < g.getSize() + 1; ++i) {
			for (int j = 1; j < g.getSize() + 1; ++j) {
				/* Példányosítjuk a FieldButtonokat, majd példányosítjuk a 
				 * listenert is, és ráállítjuk a gombra
				 *  */
				b_fields[i - 1][j - 1] = new FieldButton(g.getMap().getField(i, j));
				fma[i - 1][j - 1] = new FieldMouseAction();
				b_fields[i - 1][j - 1].addMouseListener(fma[i - 1][j - 1]);
				jp.add(b_fields[i - 1][j - 1]);
			}
		}
		
		/* Középre tesszük az ablakot */
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width / 2 - this.getSize().width / 2, center.height / 2 - this.getSize().height / 2);
		this.setVisible(true);
	}

	public Game getGame() {
		return this.g;
	}

	private class GiveUpButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			/* Ha feladjuk a játékot, visszakerülünk a menübe */
			GameFrame.this.dispose();
			MineFrame mf = new MineFrame();
		}

	}
	
	/* Ha csalni szeretnénk, minden aknás mezőn megjelenik egy zöld akna */
	private class CheatButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			GameFrame.this.g.toggleCheated();
			if(GameFrame.this.g.getCheated()) {
				for (int i = 1; i < g.getSize()+1; ++i) {
					for (int j = 1; j < g.getSize()+1; ++j) {
						if(GameFrame.this.g.getMap().getField(i, j).getMine()) {
							Image img = Toolkit.getDefaultToolkit().getImage("cheatmine.png");
							GameFrame.this.b_fields[i-1][j-1].setIcon(new ImageIcon(img));
						}
					}
				}
			} else {
			/* Ha kikapcsoljuk a csalást, visszaáll az eredeti állapot */
				for (int i = 1; i < g.getSize()+1; ++i) {
					for (int j = 1; j < g.getSize()+1; ++j) {
						GameFrame.this.b_fields[i-1][j-1].upDateIcon();
					}
				}
			}
			
		}
	}

	private class FieldMouseAction implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			/* Így tudjuk meg, hogy az adott kattintáshoz tartozó listener melyik mezőhöz tartozik */
			FieldButton fb = (FieldButton) e.getComponent();
			switch (e.getButton()) {
			
			/* Ha bal gombbal kattintottunk, felfedjük a mezőt, és frissítjük az ikonját,
			 * majd ellenőrizzük, hogy ezzel a kattintással bezárólag
			 * megnyertük (minden aknamentes mezőt felderítettünk)
			 * vagy elvesztettük (aknára léptünk) a játékot. */
			case MouseEvent.BUTTON1:
				
				fb.discover();
				fb.upDateIcon();
				
				/* Ha olyan mezőre kattintottunk, ami körül 0 akna van, akkor felderítődik az összes vele egybefüggő 0-s mező is */
				if(fb.getField().getMinesAroundNumber() == 0 && !fb.getField().getMine()) {
					GameFrame.this.g.getMap().fillZero(fb.getField());
					GameFrame.this.g.getMap().revealAround(fb.getField());
					for (int i = 1; i < g.getSize() + 1; ++i) {
						for (int j = 1; j < g.getSize() + 1; ++j) {
							if(!b_fields[i-1][j-1].getField().getMine()) {
								b_fields[i - 1][j - 1].upDateIcon();
							}						
						}
					}
				}
				
				if (fb.getField().getMine()) {
					GameFrame.this.setEnabled(false);
					GameOverFrame gof = new GameOverFrame(GameFrame.this);
				} else if (GameFrame.this.g.gameWon()) {
					timer.stop();
					GameFrame.this.setEnabled(false);
					GameWonFrame gwf = new GameWonFrame(GameFrame.this.g, GameFrame.this);
				}

				break;
			case MouseEvent.BUTTON2:
				break;
			/* Ha jobb gommbal kattintottunk, zászlót teszünk a mezőre, vagy a meglévő zászlót elvesszük onnan.
			 * Ezután frissítjük az ikont.*/
			case MouseEvent.BUTTON3:
				
				fb.flag();
				fb.upDateIcon();
				break;

			}
		}
		
		/* Ezeket kötelező volt implementálni, mert az osztályom a MouseListenertől örökölte,
		 * de üresen hagytam őket.
		 * */
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

	}

	/* Az időzítő aktiválódásaikor inkrementálja a játék idejét és frissíti az ablakon az időt. */
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			GameFrame.this.g.incrementTime();
			clockLabel.setText(Integer.toString(GameFrame.this.g.getTime()));
		}
	}

}
