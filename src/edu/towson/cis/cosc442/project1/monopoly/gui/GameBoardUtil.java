package edu.towson.cis.cosc442.project1.monopoly.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import edu.towson.cis.cosc442.project1.monopoly.Cell;
import edu.towson.cis.cosc442.project1.monopoly.GameBoard;

public class GameBoardUtil {
    
	/**
	 * Calculates the dimensions representing the long and short sides of the game board based on the total number of cells.
	 * @param i the total number of cells on the game board
	 * @return a Dimension object where width is the long side and height is the short side of the board
	 */
	public static Dimension calculateDimension(int i) {
		i = i - 4;
		int shortSide = i / 4;
		int longSide = (i - (shortSide * 2)) / 2;
		return new Dimension(longSide, shortSide);
	}
	
	/**
	 * Returns a list of cells located on the east side of the game board.
	 * @param board the GameBoard instance from which to retrieve east side cells
	 * @return a list of Cell objects representing the east side cells
	 */
	public static List<Cell> getEastCells(GameBoard board) {
		Dimension d = calculateDimension(board.getCellNumber());
		int shortSide = d.height;
		List<Cell> cells = new ArrayList<Cell>();
		for(int i = board.getCellNumber() - shortSide; i <= board.getCellNumber() - 1; i++) {
			cells.add(board.getCell(i));
		}
		return cells;
	}
	
	/**
	 * Returns a list of cells located on the north side of the game board.
	 * @param board the GameBoard instance from which to retrieve north side cells
	 * @return a list of Cell objects representing the north side cells
	 */
	public static List<Cell> getNorthCells(GameBoard board) {
		Dimension d = calculateDimension(board.getCellNumber());
		int longSide = d.width;
		int shortSide = d.height;
		List<Cell> cells = new ArrayList<Cell>();
		for(int i = longSide + 2 + shortSide; i <= longSide + 2 + shortSide + longSide + 1; i++) {
			cells.add(board.getCell(i));
		}
		return cells;
	}
	
	/**
	 * Returns a list of cells located on the south side of the game board.
	 * @param board the GameBoard instance from which to retrieve south side cells
	 * @return a list of Cell objects representing the south side cells
	 */
	public static List<Cell> getSouthCells(GameBoard board) {
		Dimension d = calculateDimension(board.getCellNumber());
		int longSide = d.width;
		List<Cell> cells = new ArrayList<Cell>();
		for(int i = longSide + 1; i >= 0; i--) {
			cells.add(board.getCell(i));
		}
		return cells;
	}
	
	/**
	 * Returns a list of cells located on the west side of the game board.
	 * @param board the GameBoard instance from which to retrieve west side cells
	 * @return a list of Cell objects representing the west side cells
	 */
	public static List<Cell> getWestCells(GameBoard board) {
		Dimension d = calculateDimension(board.getCellNumber());
		int longSide = d.width;
		int shortSide = d.height;
		List<Cell> cells = new ArrayList<Cell>();
		for(int i = longSide + 1 + shortSide; i > longSide + 1; i--) {
			cells.add(board.getCell(i));
		}
		return cells;
	}
}
