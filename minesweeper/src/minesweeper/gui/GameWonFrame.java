package minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minesweeper.data.BestScores;
import minesweeper.data.GameResult;
import minesweeper.module.Game;

/* Ha megnyerjük a játékot (tehát minden aknamentes mezőt felderítettünk), ez az ablak
 * jelenik meg. Ez felel az eredménynek a ranglistára írásáért.
 * */
public class GameWonFrame extends JFrame {
	private JPanel jp, jp1;
	private JButton okButton;
	private JLabel gameWonLabel;
	/*
	 * Konstruktorban átadjuk és eltároljuk a játékot, és a játék frame-jét. Előbbit
	 * azért, hogy a játék adatait fel tudjuk írni a ranglistára, utóbbit pedig azért,
	 * hogy be tudjuk zárni.
	 */
	private Game g;
	private GameFrame gf;

	public GameWonFrame(Game g, GameFrame gf) {
		this.g = g;
		this.gf = gf;
		this.setTitle("Game Won");
		this.setSize(140, 100);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		this.jp = new JPanel();
		this.add(jp, BorderLayout.NORTH);
		this.gameWonLabel = new JLabel("You won!");
		jp.add(gameWonLabel, BorderLayout.NORTH);

		this.jp1 = new JPanel();
		this.add(jp1, BorderLayout.SOUTH);
		this.okButton = new JButton("Ok");
		jp1.add(okButton, BorderLayout.SOUTH);
		ActionListener obak = new OkButtonActionListener();
		okButton.addActionListener(obak);
		/* Középre igazítjuk */
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width / 2 - this.getSize().width / 2, center.height / 2 - this.getSize().height / 2);
		this.setVisible(true);
	}
	
	/* Ezt a listener-t állítjuk az "Ok" gombra. Ennek hatására ha rákattintunk,
	 * beolvassa a ranglistát, hozzáadja az eredményünket, majd visszaírja a ranglistát
	 * a háttértárra. Végül bezárja az ablakot és a játék ablakát, majd megnyitja a menüt. */
	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			/* Ebbe olvasunk */
			BestScores data = new BestScores();
			try {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bestscores.dat"));
				data.setList((List<GameResult>) ois.readObject());
				ois.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			/* Ezt adjuk hozzá */
			GameResult gr = new GameResult(GameWonFrame.this.g.getSize(), GameWonFrame.this.g.getDifficulty(),
					GameWonFrame.this.g.getTime(), GameWonFrame.this.g.getName());
			data.addGameResult(gr);

			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bestscores.dat"));
				oos.writeObject(data.getList());
				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			/* Ablakot váltunk */
			GameWonFrame.this.dispose();
			gf.dispose();
			MineFrame mf = new MineFrame();
		}
	}
}
