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

/* 
 * Mielőtt megnyitnánk a ranglistát, kiválasztjuk, hogy melyik pályamérethez
 * és melyik nehézséghez akarjuk megnézni az eredményeket.
 * Ezek kiválasztását végzi ez az ablak.
 *  */

public class ChooseCategoryFrame extends JFrame {
	
	private JComboBox jcb, jcb1;
	
	/* Felépítjük az ablakot */
	public ChooseCategoryFrame() {

		this.setSize(230, 165);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new FlowLayout());
		
		JPanel jp1 = new JPanel();
		this.add(jp1);
		JLabel label2 = new JLabel("Choose difficulty:");
		jp1.add(label2);
		Integer[] levels = { 1, 2, 3, 4, 5 };
		jcb = new JComboBox(levels);
		jp1.add(jcb);

		JPanel jp3 = new JPanel();
		this.add(jp3);
		JLabel label3 = new JLabel("Choose map size:");
		jp3.add(label3);
		Integer[] sizes = new Integer[28];
		for (int i = 0; i < 28; ++i) {
			sizes[i] = i + 8;
		}
		jcb1 = new JComboBox(sizes);
		jp3.add(jcb1);

		JPanel jp2 = new JPanel();
		this.add(jp2);
		JButton button2 = new JButton("Submit");
		jp2.add(button2);
		ActionListener al = new SubmitButtonActionListener();
		button2.addActionListener(al);
		
		/* Középre */
		Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(center.width / 2 - this.getSize().width / 2, center.height / 2 - this.getSize().height / 2);
		this.setVisible(true);

	}
	
	/* Ha megnyomjuk, átirányít a ranglista frame-jére, 
	 * amin az általunk kívánt adatok lesznek
	 * */
	private class SubmitButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			ChooseCategoryFrame.this.dispose();

			BestScoresFrame bsf = new BestScoresFrame((Integer) jcb1.getSelectedItem(), (Integer) jcb.getSelectedItem());
		}
	}
}