package minesweeper.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import minesweeper.module.Game;

/* Amikor új játékor szeretnénk kezdeni, ez a frame nyílik meg.
 * Itt beírhatjuk a nevünket, kiválaszthatjuk a pálya méretét és a játék nehézségét.
 */

public class StartGameFrame extends JFrame {

	
	private JComboBox jcb, jcb1;
	private JTextField field1;
	private JLabel label1;
	
	public StartGameFrame() {
		this.setTitle("Start Game");
		this.setSize(290, 165);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new FlowLayout());

		JPanel jp = new JPanel();
		this.add(jp);
		label1 = new JLabel("Enter your name:");
		jp.add(label1);
		/* Ha nem változtatjuk, "Anonymous" lesz a nevünk. */
		field1 = new JTextField("Anonymous", 15);
		jp.add(field1);

		JPanel jp1 = new JPanel();
		this.add(jp1);
		JLabel label2 = new JLabel("Choose difficulty:");
		jp1.add(label2);
		Integer[] levels = { 1, 2, 3, 4, 5 };
		this.jcb = new JComboBox(levels);
		jp1.add(jcb);

		JPanel jp3 = new JPanel();
		this.add(jp3);
		JLabel label3 = new JLabel("Choose map size:");
		jp3.add(label3);
		/* 8 és 35 között tudunk majd választani. */
		Integer[] sizes = new Integer[28];
		for (int i = 0; i < 28; ++i) {
			sizes[i] = i + 8;
		}
		this.jcb1 = new JComboBox(sizes);
		jp3.add(jcb1);

		JPanel jp2 = new JPanel();
		this.add(jp2);
		JButton button2 = new JButton("Submit");
		jp2.add(button2);
		ActionListener al = new SubmitButtonActionListener();
		button2.addActionListener(al);
		/* A képernyő közepére */
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width / 2 - this.getSize().width / 2, center.height / 2 - this.getSize().height / 2);
		this.setVisible(true);
	}
	
	/* Ha lenyomjuk a "Submit" gombot, bezáródik ez a frame, létrejön egy Game
	 * objektum a megadott paramétereinkkel, majd megnyílik a játék ablaka.
	 * */
	private class SubmitButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			StartGameFrame.this.dispose();
			Game g = new Game(field1.getText(), (Integer) jcb.getSelectedItem(), (Integer) jcb1.getSelectedItem());
			/* Konstruktorban átadjuk g-t */
			GameFrame gf = new GameFrame(g);
		}
	}
}
