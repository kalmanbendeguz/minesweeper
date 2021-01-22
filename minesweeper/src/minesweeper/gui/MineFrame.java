package minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* A menü frame-je. */

public class MineFrame extends JFrame {
	private JPanel jp;
	private JButton jb_startgame, jb_scores, jb_exit;

	public MineFrame() {

		this.setTitle("MineSweeper");
		this.setSize(280, 240);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 100, 10, 100);
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		this.jp = new JPanel();
		this.jp.setLayout(new GridBagLayout());
		this.add(jp, BorderLayout.CENTER);

		Dimension dim = new Dimension(115, 25);

		this.jb_startgame = new JButton();
		this.jb_startgame.setText("Start game");
		this.jb_startgame.setMinimumSize(dim);
		jp.add(jb_startgame, gbc);
		ActionListener al_start = new StartButtonActionListener();
		jb_startgame.addActionListener(al_start);

		jb_scores = new JButton();
		jb_scores.setText("Best scores");
		ActionListener al_bestscores = new ScoresButtonActionListener();
		jb_scores.addActionListener(al_bestscores);
		jb_scores.setMinimumSize(dim);
		jp.add(jb_scores, gbc);

		jb_exit = new JButton();
		jb_exit.setText("Exit");
		jb_exit.setMinimumSize(dim);
		jp.add(jb_exit, gbc);
		ActionListener al_exit = new ExitButtonActionListener();
		jb_exit.addActionListener(al_exit);
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width/2-this.getSize().width/2, center.height/2-this.getSize().height/2);
		this.setVisible(true);
	}
	
	private class ExitButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
	}

	private class StartButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			MineFrame.this.dispose();
			StartGameFrame sgf = new StartGameFrame();
			
		}
	}
	
	private class ScoresButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			MineFrame.this.dispose();
			ChooseCategoryFrame cgf = new ChooseCategoryFrame();
		}
	}
}
