package minesweeper.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/* A ranglistát tároló osztály */

public class BestScores extends AbstractTableModel {

	/* A lista bejegyzései GameResult típusúak */
	private List<GameResult> results = new ArrayList<GameResult>();

	/* A táblázat működéséhez szükséges tagfüggvények */

	@Override
	public int getRowCount() {
		return results.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		GameResult result = results.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return result.getMapSize();
		case 1:
			return result.getDifficulty();
		case 2:
			return result.getTime();
		default:
			return result.getName();
		}
	}

	/* Visszaadja a lista i. elemét */
	public GameResult get(int index) {
		return results.get(index);
	}

	/* Ezen keresztül a lista adattag kívülről beállítható */
	public void setList(List<GameResult> results) {
		this.results = results;
	}

	/* Visszaadja a listát */
	public List<GameResult> getList() {
		return this.results;
	}

	/* A táblázat fejlécei */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Map Size";
		case 1:
			return "Difficulty";
		case 2:
			return "Time";
		default:
			return "Name";
		}
	}

	/* Hozzáad a ranglistához egy eredményt */
	public void addGameResult(GameResult gs) {
		results.add(gs);
	}

	/* Rendezi a ranglistát az idő szerint */
	public void sortByTime() {
		Comparator<GameResult> c = new SortByTime();
		results.sort(c);
	}

	/* Az idő szerinti rendezéshez szükséges Comparator */
	private class SortByTime implements Comparator<GameResult> {
		public int compare(GameResult a, GameResult b) {
			return a.getTime().compareTo(b.getTime());
		}
	}
}
