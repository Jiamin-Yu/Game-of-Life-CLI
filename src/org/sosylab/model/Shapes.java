package org.sosylab.model;


/**
 * Manage the shapes which can be loaded in the game of life.
 */
public final class Shapes {
  private static int beginRow;
  private static int beginColumn;

  //dimensions of the shape "Block"
  private static final int blockRow = 2;
  private static final int blockColumn = 2;

  //dimensions of the shape "Boat"
  private static final int boatRow = 3;
  private static final int boatColumn = 3;

  //dimensions of the shape "Blinker"
  private static final int blinkerRow = 1;
  private static final int blinkerColumn = 3;

  //dimensions of the shape "Toad"
  private static final int toadRow = 2;
  private static final int toadColumn = 4;

  //dimensions of the shape "Glider"
  private static final int gliderRow = 3;
  private static final int gliderColumn = 3;

  //dimensions of the shape "Spaceship"
  private static final int spaceshipRow = 4;
  private static final int spaceshipColumn = 5;

  //dimensions of the shape "Pulsar"
  private static final int pulsarRow = 13;
  private static final int pulsarColumn = 13;

  /**
   * A private constructor that prevent instantiations of the class "Shape".
   */
  private Shapes() {
    throw new java.lang.UnsupportedOperationException("Utility class and cannot be instantiated");
  }


  /**
   * A method to calculate the row index where the shape should begin.
   *
   * @param game the current game.
   * @param rowDimension the row dimension of the shape.
   * @return the row index where the shape should begin.
   */
  private static int calculateBeginRow(Grid game, int rowDimension) {
    beginRow = Math.floorDiv(game.getRows() - rowDimension, 2);
    return beginRow;
  }

  /**
   * A method to calculate the column index where the shape should begin.
   *
   * @param game the current game.
   * @param columnDimension the column dimension of the shape.
   * @return the column index where the shape should begin.
   */
  private static int calculateBeginColumn(Grid game, int columnDimension) {
    beginColumn = Math.floorDiv(game.getColumns() - columnDimension, 2);
    return beginColumn;
  }

  /**
   * A method to check whether the input population fits the game board.
   *
   * @param game the current game
   * @param rowDimension the dimension of the row of the input population.
   * @param columnDimension the dimension of the column of the input population.
   * @return false if the population doesn't fit, true if it does fit.
   */
  private static boolean populationFit(Grid game, int rowDimension, int columnDimension) {
    return (beginRow + rowDimension) <= game.getRows()
        && (beginColumn + columnDimension) <= game.getColumns();
  }

  /**
   * A method to load the shape "Block".
   *
   * @param game the current game.
   */
  public static void loadBlock(Grid game) {
    beginRow = calculateBeginRow(game, blockRow);
    beginColumn = calculateBeginColumn(game, blockColumn);
    if (populationFit(game, blockRow, blockColumn)) {
      game.clear();
      for (int rowIndex = beginRow; rowIndex < beginRow + 2; rowIndex++) {
        for (int columnIndex = beginColumn; columnIndex < beginColumn + 2; columnIndex++) {
          game.setCellAlive(columnIndex, rowIndex);
        }
      }
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

  /**
   * A method to load the shape "Boat".
   *
   * @param game the current game.
   */
  public static void loadBoat(Grid game) {
    beginRow = calculateBeginRow(game, boatRow);
    beginColumn = calculateBeginColumn(game, boatColumn);
    if (populationFit(game, boatRow, boatColumn)) {
      game.clear();
      game.setCellAlive(beginColumn, beginRow);
      game.setCellAlive(beginColumn + 1, beginRow);
      game.setCellAlive(beginColumn, beginRow + 1);
      game.setCellAlive(beginColumn + 2, beginRow + 1);
      game.setCellAlive(beginColumn + 1, beginRow + 2);
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

  /**
   * A method to load the shape "Blinker".
   *
   * @param game the current game.
   */
  public static void loadBlinker(Grid game) {
    beginRow = calculateBeginRow(game, blinkerRow);
    beginColumn = calculateBeginColumn(game, blinkerColumn);
    if (populationFit(game, blinkerRow, blinkerColumn)) {
      game.clear();
      game.setCellAlive(beginColumn, beginRow);
      game.setCellAlive(beginColumn + 1, beginRow);
      game.setCellAlive(beginColumn + 2, beginRow);
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

  /**
   * A method to load the shape "Toad".
   *
   * @param game the current game.
   */
  public static void loadToad(Grid game) {
    beginRow = calculateBeginRow(game, toadRow);
    beginColumn = calculateBeginColumn(game, toadColumn);
    if (populationFit(game, toadRow, toadColumn)) {
      game.clear();
      game.setCellAlive(beginColumn + 1, beginRow);
      game.setCellAlive(beginColumn + 2, beginRow);
      game.setCellAlive(beginColumn + 3, beginRow);
      game.setCellAlive(beginColumn, beginRow + 1);
      game.setCellAlive(beginColumn + 1, beginRow + 1);
      game.setCellAlive(beginColumn + 2, beginRow + 1);
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

  /**
   * A method to load the shape "Glider".
   *
   * @param game the current game.
   */
  public static void loadGlider(Grid game) {
    beginRow = calculateBeginRow(game, gliderRow);
    beginColumn = calculateBeginColumn(game, gliderColumn);
    if (populationFit(game, gliderRow, gliderColumn)) {
      game.clear();
      game.setCellAlive(beginColumn, beginRow);
      game.setCellAlive(beginColumn + 1, beginRow);
      game.setCellAlive(beginColumn + 2, beginRow);
      game.setCellAlive(beginColumn, beginRow + 1);
      game.setCellAlive(beginColumn + 1, beginRow + 2);
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

  /**
   * A method to load the shape "Spaceship".
   *
   * @param game the current game.
   */
  public static void loadSpaceship(Grid game) {
    beginRow = calculateBeginRow(game, spaceshipRow);
    beginColumn = calculateBeginColumn(game, spaceshipColumn);
    if (populationFit(game, spaceshipRow, spaceshipColumn)) {
      game.clear();
      game.setCellAlive(beginColumn + 1, beginRow);
      game.setCellAlive(beginColumn + 4, beginRow);
      game.setCellAlive(beginColumn, beginRow + 1);
      game.setCellAlive(beginColumn, beginRow + 2);
      game.setCellAlive(beginColumn + 4, beginRow + 2);
      game.setCellAlive(beginColumn, beginRow + 3);
      game.setCellAlive(beginColumn + 1, beginRow + 3);
      game.setCellAlive(beginColumn + 2, beginRow + 3);
      game.setCellAlive(beginColumn + 3, beginRow + 3);
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

  /**
   * A method to load the shape "Pulsar".
   *
   * @param game the current game.
   */
  public static void loadPulsar(Grid game) {
    beginRow = calculateBeginRow(game, pulsarRow);
    beginColumn = calculateBeginColumn(game, pulsarColumn);
    if (populationFit(game, pulsarRow, pulsarColumn)) {
      game.clear();
      //set row 0, row 12
      game.setCellAlive(beginColumn + 2, beginRow);
      game.setCellAlive(beginColumn + 3, beginRow);
      game.setCellAlive(beginColumn + 9, beginRow);
      game.setCellAlive(beginColumn + 10, beginRow);
      game.setCellAlive(beginColumn + 2, beginRow + 12);
      game.setCellAlive(beginColumn + 3, beginRow + 12);
      game.setCellAlive(beginColumn + 9, beginRow + 12);
      game.setCellAlive(beginColumn + 10, beginRow + 12);
      // set row 1, row 11
      game.setCellAlive(beginColumn + 3, beginRow + 1);
      game.setCellAlive(beginColumn + 4, beginRow + 1);
      game.setCellAlive(beginColumn + 8, beginRow + 1);
      game.setCellAlive(beginColumn + 9, beginRow + 1);
      game.setCellAlive(beginColumn + 3, beginRow + 11);
      game.setCellAlive(beginColumn + 4, beginRow + 11);
      game.setCellAlive(beginColumn + 8, beginRow + 11);
      game.setCellAlive(beginColumn + 9, beginRow + 11);
      //set row 2, row 10
      game.setCellAlive(beginColumn, beginRow + 2);
      game.setCellAlive(beginColumn + 3, beginRow + 2);
      game.setCellAlive(beginColumn + 5, beginRow + 2);
      game.setCellAlive(beginColumn + 7, beginRow + 2);
      game.setCellAlive(beginColumn + 9, beginRow + 2);
      game.setCellAlive(beginColumn + 12, beginRow + 2);
      game.setCellAlive(beginColumn, beginRow + 10);
      game.setCellAlive(beginColumn + 3, beginRow + 10);
      game.setCellAlive(beginColumn + 5, beginRow + 10);
      game.setCellAlive(beginColumn + 7, beginRow + 10);
      game.setCellAlive(beginColumn + 9, beginRow + 10);
      game.setCellAlive(beginColumn + 12, beginRow + 10);
      //set row 3, 9
      game.setCellAlive(beginColumn, beginRow + 3);
      game.setCellAlive(beginColumn + 1, beginRow + 3);
      game.setCellAlive(beginColumn + 2, beginRow + 3);
      game.setCellAlive(beginColumn + 4, beginRow + 3);
      game.setCellAlive(beginColumn + 5, beginRow + 3);
      game.setCellAlive(beginColumn + 7, beginRow + 3);
      game.setCellAlive(beginColumn + 8, beginRow + 3);
      game.setCellAlive(beginColumn + 10, beginRow + 3);
      game.setCellAlive(beginColumn + 11, beginRow + 3);
      game.setCellAlive(beginColumn + 12, beginRow + 3);
      game.setCellAlive(beginColumn, beginRow + 9);
      game.setCellAlive(beginColumn + 1, beginRow + 9);
      game.setCellAlive(beginColumn + 2, beginRow + 9);
      game.setCellAlive(beginColumn + 4, beginRow + 9);
      game.setCellAlive(beginColumn + 5, beginRow + 9);
      game.setCellAlive(beginColumn + 7, beginRow + 9);
      game.setCellAlive(beginColumn + 8, beginRow + 9);
      game.setCellAlive(beginColumn + 10, beginRow + 9);
      game.setCellAlive(beginColumn + 11, beginRow + 9);
      game.setCellAlive(beginColumn + 12, beginRow + 9);
      //set row 4, 8
      game.setCellAlive(beginColumn + 1, beginRow + 4);
      game.setCellAlive(beginColumn + 3, beginRow + 4);
      game.setCellAlive(beginColumn + 5, beginRow + 4);
      game.setCellAlive(beginColumn + 7, beginRow + 4);
      game.setCellAlive(beginColumn + 9, beginRow + 4);
      game.setCellAlive(beginColumn + 11, beginRow + 4);
      game.setCellAlive(beginColumn + 1, beginRow + 8);
      game.setCellAlive(beginColumn + 3, beginRow + 8);
      game.setCellAlive(beginColumn + 5, beginRow + 8);
      game.setCellAlive(beginColumn + 7, beginRow + 8);
      game.setCellAlive(beginColumn + 9, beginRow + 8);
      game.setCellAlive(beginColumn + 11, beginRow + 8);
      //set row 5, 7
      game.setCellAlive(beginColumn + 2, beginRow + 5);
      game.setCellAlive(beginColumn + 3, beginRow + 5);
      game.setCellAlive(beginColumn + 4, beginRow + 5);
      game.setCellAlive(beginColumn + 8, beginRow + 5);
      game.setCellAlive(beginColumn + 9, beginRow + 5);
      game.setCellAlive(beginColumn + 10, beginRow + 5);
      game.setCellAlive(beginColumn + 2, beginRow + 7);
      game.setCellAlive(beginColumn + 3, beginRow + 7);
      game.setCellAlive(beginColumn + 4, beginRow + 7);
      game.setCellAlive(beginColumn + 8, beginRow + 7);
      game.setCellAlive(beginColumn + 9, beginRow + 7);
      game.setCellAlive(beginColumn + 10, beginRow + 7);
    } else {
      System.out.println("ERROR! Population doesn't fit on game board.");
    }
  }

}
