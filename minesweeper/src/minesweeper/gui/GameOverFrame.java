package minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* Egyszerű frame, amely közli a játékossal, hogy elvesztette a játékot.
 * Az "Ok" gomb hatására visszakerülünk a menübe.
 * */

public class GameOverFrame extends JFrame {
	private JPanel jp, jp1;
	private JButton okButton;
	private JLabel gameOverLabel;
	/* Átadjuk neki a játék frame-jét, mert ő fogja bezárni, magával együtt. */
	private GameFrame gf;

	public GameOverFrame(GameFrame gf) {
		this.gf = gf;
		this.setTitle("Game Over");
		this.setSize(140, 100);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		jp = new JPanel();
		this.add(jp, BorderLayout.NORTH);
		gameOverLabel = new JLabel("Game Over!");
		jp.add(gameOverLabel, BorderLayout.NORTH);

		jp1 = new JPanel();
		this.add(jp1, BorderLayout.SOUTH);
		okButton = new JButton("Ok");
		jp1.add(okButton, BorderLayout.SOUTH);
		ActionListener obak = new OkButtonActionListener();
		okButton.addActionListener(obak);
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width / 2 - this.getSize().width / 2, center.height / 2 - this.getSize().height / 2);
		this.setVisible(true);
	}

	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			/* Bezárjuk ezt az ablakot és a játék ablakát, majd megnyitjuk a menüt. */
			GameOverFrame.this.dispose();
			gf.dispose();
			MineFrame mf = new MineFrame();
		}
	}
}
