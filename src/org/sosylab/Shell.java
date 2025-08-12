package org.sosylab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.sosylab.model.Game;
import org.sosylab.model.Grid;
import org.sosylab.model.Shapes;

/**
 * This class provides the utility to let a user play the Game of Life application interactively on
 * a shell.
 */
class Shell {

  private static final String PROMPT = "gol> ";
  private static final String ERROR = "Error! ";

  private static final String HELP = """
      Game of Life - possible commands:
      alive i j    set cell in column i and row j alive
      clear        kill all cells and reset generations
      dead i j     kill cell in column i and row j
      generate     compute next generation
      help         print this help
      new x y      start a new game with dimensions x times y
      print        print the gameboard
      quit         quit the program
      resize x y   resize current game to dimensions x times y
      shape name   load initial population""";

  private Grid game;


  /**
   * The main loop that handles the shell interaction. It takes commands from the user and executes
   * them.
   *
   * @throws IOException thrown when reading from stdin fails
   */
  void run() throws IOException {
    BufferedReader stdin =
        new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    boolean quit = false;
    while (!quit) {
      //output the prompt to console
      System.out.print(PROMPT);
      //wait for user's input
      String input = stdin.readLine();
      //if no more input
      if (input == null) {
        break;
      }

      //slice the input string on one or more white spaces
      String[] subStrings = input.trim().split("\\s+");

      //display error message if no command is given
      if (subStrings[0].length() == 0) {
        System.out.println(ERROR + "No command given");
        continue;
      }

      //assign the first part(before the first white space) of the input string
      //(after handling it with the parseCommand method) to the variable "command"
      Command command = parseCommand(subStrings[0]);

      //check what the input command is
      //call corresponding command method
      switch (command) {
        case NEW:
          commandNew(subStrings);
          break;
        case ALIVE:
          commandAlive(subStrings);
          break;
        case DEAD:
          commandDead(subStrings);
          break;
        case GENERATE:
          commandGenerate(subStrings);
          break;
        case CLEAR:
          commandClear(subStrings);
          break;
        case PRINT:
          commandPrint(subStrings);
          break;
        case RESIZE:
          commandResize(subStrings);
          break;
        case SHAPE:
          commandShape(subStrings);
          break;
        case HELP:
          commandHelp(subStrings);
          break;
        case QUIT:
          quit = commandQuit(subStrings);
          break;
        case UNKNOWN:
          //do nothing
          break;
        //if not a valid command, output error message to console
        default:
          System.out.println(ERROR + "Invalid command.");
          break;
      }

    }
  }

  /**
   * Handle an input command and check whether it matches a {@link Command}.
   * If so, the corresponding command gets returned, {@link Command#UNKNOWN} otherwise.
   *
   * @param unhandledCommand the input command needs to be checked
   * @return {@link Command} containing either the matched token or {@link Command#UNKNOWN}.
   */
  private Command parseCommand(String unhandledCommand) {
    Command result = Command.UNKNOWN;
    //switch input command to uppercase
    //ensure case insensitivity
    String commandUpperCase = unhandledCommand.toUpperCase();
    //loop through all possible command values
    for (Command command : Command.values()) {
      //if input is the prefix of a specific command
      //assign this command to the variable "result"
      if (command.getName().startsWith(commandUpperCase)) {
        result = command;
        break;
      }
    }
    //if input is not a valid command
    //output error message
    if (result == Command.UNKNOWN) {
      System.out.println(ERROR + "Invalid command.");
    }
    return result;

  }

  /**
   * Handle the command "NEW x y". It checks the input to see whether it has valid arguments.
   * If the input has valid arguments, create a new game.
   *
   * @param subStrings the input needs to be checked
   */
  private void commandNew(String[] subStrings) {
    if (subStrings.length > 3) {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"NEW\".");
      return;
    }

    if (subStrings.length < 3) {
      System.out.println(ERROR + "Invalid arguments: too few arguments for command \"NEW\".");
      return;
    }

    if (isNumeric(subStrings[1]) && isNumeric(subStrings[2])) {
      int columns = Integer.parseInt(subStrings[1]);
      int rows = Integer.parseInt(subStrings[2]);
      if (columns > 0 && rows > 0) {
        game = new Game(columns, rows);
      } else {
        System.out.println(ERROR + "Invalid arguments: columns or rows should be positive.");
      }
    } else {
      System.out.println(ERROR + "Invalid arguments: invalid arguments for columns or rows.");
    }

  }

  /**
   * A method to check whether the input "number string" is numeric.
   * namely, it is not null, and it contains only numbers (as strings)
   *
   * @param str the input string needs to be checked
   * @return true if the input string is numeric, false if not.
   */

  private boolean isNumeric(String str) {
    return str != null && str.matches("[0-9]+");
  }

  /**
   * Handle the command "ALIVE i j". It checks the input command to see
   * whether it has valid arguments. If the input command has valid arguments,
   * set corresponding cell to alive.
   *
   * @param subStrings the input command needs to be checked
   */
  private void commandAlive(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length > 3) {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"ALIVE\".");
      return;
    }
    if (subStrings.length < 3) {
      System.out.println(ERROR + "Invalid arguments: too few arguments for command \"ALIVE\".");
      return;
    }
    if (isNumeric(subStrings[1]) && isNumeric(subStrings[2])) {
      int column = Integer.parseInt(subStrings[1]);
      int row = Integer.parseInt(subStrings[2]);
      if (column < 0 || row < 0) {
        System.out.println(ERROR + "row/column index should not be negative.");
        return;
      }
      if (column >= game.getColumns() || row >= game.getRows()) {
        System.out.println(ERROR + "row/column index should be smaller than row/column size.");
        return;
      }
      if (game.isCellAlive(column, row)) {
        System.out.println(ERROR + "Cell is already alive.");
        return;
      }
      game.setCellAlive(column, row);
    } else {
      System.out.println(ERROR + "Invalid arguments: invalid arguments for column or row.");
    }

  }

  /**
   * Handle the command "DEAD i j". It checks the input command to see
   * whether it has valid arguments. If the input command has valid arguments,
   * set corresponding cell to dead.
   *
   * @param subStrings the input command needs to be checked
   */
  private void commandDead(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length > 3) {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"DEAD\".");
      return;
    }

    if (subStrings.length < 3) {
      System.out.println(ERROR + "Invalid arguments: too few arguments for command \"DEAD\".");
      return;
    }

    if (isNumeric(subStrings[1]) && isNumeric(subStrings[2])) {
      int column = Integer.parseInt(subStrings[1]);
      int row = Integer.parseInt(subStrings[2]);
      if (column < 0 || row < 0) {
        System.out.println(ERROR + "row/column index should not be negative.");
        return;
      }
      if (column >= game.getColumns() || row >= game.getRows()) {
        System.out.println(ERROR + "row/column index should be smaller than row/column size.");
        return;
      }
      if (!game.isCellAlive(column, row)) {
        System.out.println(ERROR + "Cell is already dead.");
      }
      game.setCellDead(column, row);
    } else {
      System.out.println(ERROR + "Invalid arguments: invalid arguments for column or row.");
    }
  }

  /**
   * Handle the command "CLEAR". It checks the input command to see whether it has redundant
   * arguments. If yes, output error message; if not, clear the game board.
   *
   * @param subStrings the input command needs to be checked
   */
  private void commandClear(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length == 1) {
      game.clear();
    } else {
      System.out.println(ERROR + "Invalid arguments: redundant arguments for command \"CLEAR\".");
    }
  }

  /**
   * Handle the command "PRINT". It checks the input command to see whether it has redundant
   * arguments. If yes, output error message; if not, print the game state.
   *
   * @param subStrings the input command needs to be checked
   */
  private void commandPrint(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length == 1) {
      System.out.println(game);
    } else {
      System.out.println(ERROR + "Invalid arguments: redundant arguments for command \"PRINT\".");
    }

  }

  /**
   * Handle the command "HELP". It checks the input to see whether it is a valid help command.
   * If yes, print message to help, if not, print error message.
   *
   * @param subStrings the input needs to be checked
   */
  private void commandHelp(String[] subStrings) {
    if (subStrings.length == 1) {
      System.out.println(HELP);
    } else {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"HELP\".");
    }

  }

  /**
   * Handle the command "QUIT". It checks the input to see whether it is a valid "QUIT" command.
   * If yes, quit the game, if not, print error message.
   *
   * @param subStrings the input needs to be checked
   * @return true if the input is a valid "QUIT" command, false if not.
   */
  private boolean commandQuit(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return false;
    }
    if (subStrings.length == 1) {
      return true;
    } else {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"QUIT\".");
      return false;
    }
  }

  /**
   * Handle the command "GENERATE". It checks the input command to see whether it has redundant
   * arguments. If yes, output error message;
   * if no, calculates the next generation according to the rules of the gam
   *
   * @param subStrings the input command needs to be checked
   */
  private void commandGenerate(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length == 1) {
      game.next();
      System.out.println("Generation: " + game.getGenerations());
    } else {
      System.out.println(ERROR
          + "Invalid arguments: redundant arguments for command \"GENERATE\".");
    }
  }

  /**
   * Handle the command "SHAPE s". It checks the input to see whether it has a valid argument.
   * If the input has a valid argument, load a corresponding initial population.
   *
   * @param subStrings the input command needs to be checked
   */
  private void commandResize(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length > 3) {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"RESIZE\".");
      return;
    }

    if (subStrings.length < 3) {
      System.out.println(ERROR + "Invalid arguments: too few arguments for command \"RESIZE\".");
      return;
    }

    if (isNumeric(subStrings[1]) && isNumeric(subStrings[2])) {
      int columns = Integer.parseInt(subStrings[1]);
      int rows = Integer.parseInt(subStrings[2]);
      if (columns > 0 && rows > 0) {
        game.resize(columns, rows);
      } else {
        System.out.println(ERROR + "Invalid arguments: columns or rows should be positive.");
      }
    } else {
      System.out.println(ERROR + "Invalid arguments: invalid arguments for columns or rows.");
    }
  }

  private void commandShape(String[] subStrings) {
    //check whether a game is running
    if (game == null) {
      System.out.println(ERROR + "No active game.");
      return;
    }
    if (subStrings.length > 2) {
      System.out.println(ERROR + "Invalid arguments: too many arguments for command \"SHAPE\".");
      return;
    }

    if (subStrings.length < 2) {
      System.out.println(ERROR + "Invalid arguments: too few arguments for command \"SHAPE\".");
      return;
    }

    String shape = subStrings[1].toUpperCase();

    switch (shape) {
      case "BLOCK":
        Shapes.loadBlock(game);
        break;
      case "BOAT":
        Shapes.loadBoat(game);
        break;
      case "BLINKER":
        Shapes.loadBlinker(game);
        break;
      case "TOAD":
        Shapes.loadToad(game);
        break;
      case "GLIDER":
        Shapes.loadGlider(game);
        break;
      case "SPACESHIP":
        Shapes.loadSpaceship(game);
        break;
      case "PULSAR":
        Shapes.loadPulsar(game);
        break;
      default:
        System.out.println(ERROR + "Invalid population.");
        break;
    }
  }



  /**
   * The commands available to the user on a shell.
   */
  private enum Command {
    NEW("NEW"),
    ALIVE("ALIVE"),
    DEAD("DEAD"),
    GENERATE("GENERATE"),
    PRINT("PRINT"),
    CLEAR("CLEAR"),
    RESIZE("RESIZE"),
    SHAPE("SHAPE"),
    HELP("HELP"),
    QUIT("QUIT"),
    UNKNOWN;
    private final String name;

    Command(String cmd) {
      name = cmd;
    }

    Command() {
      name = "";
    }

    public String getName() {
      return name;
    }
  }

}

