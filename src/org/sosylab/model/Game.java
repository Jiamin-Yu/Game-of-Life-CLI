package org.sosylab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Manage a game of life.
 */
public class Game implements Grid {

  // Staying alive in this range
  private static final int STAY_ALIVE_MIN_NEIGHBORS = 2;
  private static final int STAY_ALIVE_MAX_NEIGHBORS = 3;

  // Condition for getting newly born
  private static final int NEWBORN_NEIGHBORS = 3;

  //the size of the columns of the game
  private int columnSize;

  //the size of the rows of the game
  private int rowSize;

  //the game board of the game
  private Cell[][] gameBoard;

  //number of the generation of the game
  private int generation;


  /**
   * Create a game of life.
   *
   * @param cols the size of the columns.
   * @param rows the size of the rows.
   */
  public Game(int cols, int rows) {
    if (!(cols > 0 && rows > 0)) {
      throw new IllegalArgumentException("Number of columns and rows must be positive");
    }
    columnSize = cols;
    rowSize = rows;
    gameBoard = new Cell[rowSize][columnSize];
    for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
        gameBoard[rowIndex][columnIndex] = new Cell(columnIndex, rowIndex);
      }
    }
    generation = 0;
  }

  @Override
  public void setCellAlive(int col, int row) {
    if (col >= columnSize || row >= rowSize) {
      throw new IllegalArgumentException(
          "Parameters for column and row may not exceed the maximum number of columns and rows");
    }

    if (col < 0 || row < 0) {
      throw new IllegalArgumentException("Number of column and row may not be negative");
    }

    gameBoard[row][col].setStatusAlive();

  }

  @Override
  public boolean isCellAlive(int col, int row) {
    if (col >= columnSize || row >= rowSize) {
      throw new IllegalArgumentException(
          "Parameters for column and row may not exceed the maximum number of columns and rows");
    }

    if (col < 0 || row < 0) {
      throw new IllegalArgumentException("Number of column and row may not be negative");
    }

    return gameBoard[row][col].getStatus();

  }

  @Override
  public void setCellDead(int col, int row) {
    if (col >= columnSize || row >= rowSize) {
      throw new IllegalArgumentException(
          "Parameters for column and row may not exceed the maximum number of columns and rows");
    }

    if (col < 0 || row < 0) {
      throw new IllegalArgumentException("Number of column and row may not be negative");
    }

    gameBoard[row][col].setStatusDead();
  }

  @Override
  public int getColumns() {
    return columnSize;
  }

  @Override
  public int getRows() {
    return rowSize;
  }

  @Override
  public int getGenerations() {
    return generation;
  }

  @Override
  public void clear() {
    for (Cell liveCell : getPopulation()) {
      liveCell.setStatusDead();
    }
    generation = 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {

      for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
        if (gameBoard[rowIndex][columnIndex].getStatus()) {
          sb.append("X");
        } else {
          sb.append(".");
        }
      }
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }

  @Override
  public void next() {
    generation = generation + 1;
    ArrayList<Integer> allLiveCells = new ArrayList<>();
    for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
        //loop through the game board and count live cells for each cell.
        //add the live cell numbers to a list
        allLiveCells.add(countLiveCells(gameBoard[rowIndex][columnIndex]));
      }
    }

    for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
        //apply generation rules to each cell
        int liveCellsIndex = rowIndex * columnSize + columnIndex;
        applyRules(gameBoard[rowIndex][columnIndex], allLiveCells.get(liveCellsIndex));
      }
    }
  }

  /**
   * A method to count the numbers of live cells in the relevant cell block.
   * This number includes the cell that is considered itself.
   *
   * @param cell the cell that is considered.
   * @return number of live cells in the corresponding cell block.
   */
  public int countLiveCells(Cell cell) {
    int liveCells = 0;
    int cellRow = cell.getRow();
    int cellColumn = cell.getColumn();
    int neighbourRowUp;
    int neighbourRowDown;
    int neighbourColumnLeft;
    int neighbourColumnRight;

    //deal with edge cells, namely, cells that don't have a full 9-cell block
    if (cellRow - 1 >= 0) {
      neighbourRowUp = cellRow - 1;
    } else {
      neighbourRowUp = cellRow;
    }
    if (cellRow + 1 < rowSize) {
      neighbourRowDown = cellRow + 1;
    } else {
      neighbourRowDown = cellRow;
    }
    if (cellColumn - 1 >= 0) {
      neighbourColumnLeft = cellColumn - 1;
    } else {
      neighbourColumnLeft = cellColumn;
    }
    if (cellColumn + 1 < columnSize) {
      neighbourColumnRight = cellColumn + 1;
    } else {
      neighbourColumnRight = cellColumn;
    }

    //loop through all neighbours and the cell itself
    //count the number of live cells
    for (int rowIndex = neighbourRowUp; rowIndex <= neighbourRowDown; rowIndex++) {
      for (int columnIndex = neighbourColumnLeft; columnIndex <= neighbourColumnRight;
           columnIndex++) {
        if (gameBoard[rowIndex][columnIndex].getStatus()) {
          liveCells = liveCells + 1;
        }
      }
    }
    return liveCells;
  }

  /**
   * A method to apply the generation rules to a cell.
   *
   * @param cell the cell to which the rules are applied.
   * @param liveCells number of live cells in the relevant cell block.
   */
  public void applyRules(Cell cell, int liveCells) {
    //the variable "liveNeighbours" is the number of live neighbours
    //if the cell itself is alive,
    //then the number of live neighbours is the number of liveCells -1;
    //if the cell itself is dead, then the number of live neighbours is the number of liveCells.
    int liveNeighbours;

    if (cell.getStatus()) {
      //rules applied to living cells.
      liveNeighbours = liveCells - 1;
      //Living cells with less than two neighbors die of loneliness
      if (liveNeighbours < STAY_ALIVE_MIN_NEIGHBORS) {
        cell.setStatusDead();
      }
      //Living cells with more than three neighbors die of overpopulation
      if (liveNeighbours > STAY_ALIVE_MAX_NEIGHBORS) {
        cell.setStatusDead();
      }
      //Living cells with exactly two or three living neighbors remain alive
    } else {
      //the rule applied to dead cells
      liveNeighbours = liveCells;
      //Dead cells with exactly three living neighbors are reborn
      if (liveNeighbours == NEWBORN_NEIGHBORS) {
        cell.setStatusAlive();
      }
    }
  }

  @Override
  public void resize(int cols, int rows) {

    if (!(cols > 0 && rows > 0)) {
      throw new IllegalArgumentException("Number of columns and rows must be positive");
    }

    Cell[][] resizedGameboard = new Cell[rows][cols];

    //the game board becomes smaller (both rows and columns).
    if (rows <= rowSize && cols <= columnSize) {
      for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
        for (int columnIndex = 0; columnIndex < cols; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = gameBoard[rowIndex][columnIndex];
        }
      }
    }
    //the game board becomes bigger (both rows and columns)
    if (rows > rowSize && cols > columnSize) {
      for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
        for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = gameBoard[rowIndex][columnIndex];
        }
      }
      for (int rowIndex = rowSize; rowIndex < rows; rowIndex++) {
        for (int columnIndex = 0; columnIndex < cols; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = new Cell(columnIndex, rowIndex);
        }
      }
      for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
        for (int columnIndex = columnSize; columnIndex < cols; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = new Cell(columnIndex, rowIndex);
        }
      }
    }
    //only the row size of the game board becomes larger
    if (rows > rowSize && cols <= columnSize) {
      for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
        for (int columnIndex = 0; columnIndex < cols; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = gameBoard[rowIndex][columnIndex];
        }
      }
      for (int rowIndex = rowSize; rowIndex < rows; rowIndex++) {
        for (int columnIndex = 0; columnIndex < cols; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = new Cell(columnIndex, rowIndex);
        }
      }
    }
    //only the column size of the game board becomes larger
    if (cols > columnSize && rows <= rowSize) {
      for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
        for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = gameBoard[rowIndex][columnIndex];
        }
      }
      for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
        for (int columnIndex = columnSize; columnIndex < cols; columnIndex++) {
          resizedGameboard[rowIndex][columnIndex] = new Cell(columnIndex, rowIndex);
        }
      }
    }
    gameBoard = resizedGameboard;
    columnSize = cols;
    rowSize = rows;
  }

  @Override
  public Collection<Cell> getPopulation() {
    HashSet<Cell> population = new HashSet<>();
    for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
        if (gameBoard[rowIndex][columnIndex].getStatus()) {
          population.add(gameBoard[rowIndex][columnIndex]);
        }
      }
    }
    return population;
  }

}

