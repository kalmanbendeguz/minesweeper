package minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import minesweeper.data.BestScores;
import minesweeper.data.GameResult;

/* A ranglista frame-je */

public class BestScoresFrame extends JFrame {

	private BestScores data;

	/* Elhelyezzük az elemeket az ablakon */

	private void initComponents() {
		this.setLayout(new BorderLayout());

		JTable jt = new JTable(data);
		jt.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(jt);
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel jp = new JPanel();
		this.add(jp, BorderLayout.SOUTH);

		JButton jb = new JButton("Back to menu");
		ActionListener a = new MenuButtonActionListener();
		jb.addActionListener(a);
		jp.add(jb);
		
	}

	public BestScoresFrame(Integer mapSize, Integer difficulty) {
		super("Best Scores");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/* Először ebbe olvassuk az egész listát, utána válogatjuk ki ami kell */
		BestScores temp = new BestScores();
		data = new BestScores();
		try {

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bestscores.dat"));
			/* Beolvasunk temp-be */
			temp.setList((List<GameResult>) ois.readObject());

			for (int i = 0; i < temp.getRowCount(); ++i) {
				/*
				 * temp minden elemére megnézzük, hogy egyezik-e a megadott difficulty-vel és
				 * mapSize-al, ha igen, hozzáadjuk a listához, ami a táblázatban fog megjelenni,
				 * majd rendezzük a listát az idő szerint
				 */
				GameResult temp_g = temp.get(i);
				if (temp_g.getMapSize().equals(mapSize) && temp_g.getDifficulty().equals(difficulty)) {
					data.addGameResult(temp_g);
				}
				data.sortByTime();
			}
			ois.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		setMinimumSize(new Dimension(400, 200));
		/* Középre helyezzük az ablakot */
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width / 2 - this.getSize().width / 2, center.height / 2 - this.getSize().height / 2);
		initComponents();
		this.setVisible(true);
	}
	
	/* Ha megnyomjuk, visszakerülünk a menübe */
	private class MenuButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			BestScoresFrame.this.dispose();
			MineFrame mf = new MineFrame();
		}
	}
}
