import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class gameOfLife {
	public static final int DEAD = 0;
	public static final int ALIVE = 1;

	/**
	 * Stores a board state in which every cell is initialized to DEAD
	 * 
	 * @param width
	 *            the width of the board state
	 * @param height
	 *            the height of the board state
	 * @return the dead board state
	 */
	public static int[][] deadState(int width, int height) {
		int[][] boardState = new int[width][height];
		for (int i = 0; i < boardState.length; i++) {
			for (int j = 0; j < boardState[i].length; j++) {
				boardState[i][j] = 0;
			}
		}
		return boardState;
	}

	/**
	 * Stores a board state in which every cell has been randomly initialized to
	 * either ALIVE or DEAD
	 * 
	 * @param width
	 *            the width of the board state
	 * @param height
	 *            the height of the board state
	 * @return the random board state
	 */
	public static int[][] randomState(int width, int height) {
		int[][] randomState = deadState(width, height);
		for (int i = 0; i < randomState.length; i++) {
			for (int j = 0; j < randomState[i].length; j++) {
				if (Math.random() >= 0.5) {
					randomState[i][j] = DEAD;
				} else {
					randomState[i][j] = ALIVE;
				}
			}
		}
		return randomState;
	}

	/**
	 * Formats the board state and prints to terminal
	 * 
	 * @param boardState
	 *            the board state
	 */
	public static void render(int[][] boardState) {
		System.out.println();
		System.out.print(" ");
		for (int i = 0; i < boardState[0].length; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i = 0; i < boardState.length; i++) {
			System.out.print("|");
			for (int j = 0; j < boardState[i].length; j++) {
				if (boardState[i][j] == ALIVE) {
					System.out.print("#");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.print(" ");
		for (int i = 0; i < boardState[0].length; i++) {
			System.out.print("-");
		}
	}

	/**
	 * Gets the width of the state
	 * 
	 * @param state
	 *            a Game state
	 * @return the width of the input state
	 */
	public static int getWidth(int[][] state) {
		return state[0].length;
	}

	/**
	 * Gets the height of a state
	 * 
	 * @param state
	 *            a Game state
	 * @return the height of the input state
	 */
	public static int getHeight(int[][] state) {
		return state.length;
	}

	/**
	 * Gets the next value of a single cell in a state
	 * 
	 * @param x1
	 *            the x-coordinate of the cell
	 * @param y1
	 *            the y-coordinate of the cell
	 * @param state
	 *            the currents state of the Game board
	 * @return the new state of the given cell - either DEAD or ALIVE
	 */
	public static int nextCellValue(int x1, int y1, int[][] state) {
		int width = getWidth(state);
		int height = getHeight(state);
		int x = x1;
		int y = y1;
		int count = 0;
		// Iterates around the cell's neighbors
		for (int i = x - 1; i < x + 2; i++) {
			for (int j = y - 1; j < y + 2; j++) {
				// Makes sure we don't go off edge of board
				if (i < 0 || i >= height) {
					continue;
				}
				if (j < 0 || j >= width) {
					continue;
				}
				// Makes sure we don't count the cell as a neighbor
				if (i == x && j == y) {
					continue;
				}
				if (state[i][j] == ALIVE) {
					count++;
				}
			}
		}
		if (state[x][y] == ALIVE) {
			if (count <= 1) {
				return DEAD;
			} else if (count == 2 || count == 3) {
				return ALIVE;
			} else {
				return DEAD;
			}
		} else {
			if (count == 3) {
				return ALIVE;
			} else {
				return DEAD;
			}
		}
	}

	/**
	 * Calculates the next board state according to rules of Life
	 * 
	 * @param initialState
	 *            the initial board state
	 * @return the next board state
	 */
	public static int[][] nextState(int[][] initialState) {
		int[][] newState = deadState(initialState.length, initialState[0].length);
		for (int i = 0; i < newState.length; i++) {
			for (int j = 0; j < newState[0].length; j++) {
				newState[i][j] = nextCellValue(i, j, initialState);
			}
		}
		return newState;
	}

	/**
	 * Runs the Game of Life forever, starting from given initial state.
	 * 
	 * @param initialState
	 *            the Game state to start at
	 * @throws InterruptedException
	 */
	public static void eternalLife(int[][] initialState) throws InterruptedException {
		int[][] state = nextState(initialState);
		while (true) {
			render(state);
			state = nextState(state);
			Thread.sleep(300);
		}
	}

	/**
	 * Loads a board state from the given filepath
	 * 
	 * @param fileName
	 *            the filepath to load the state from/
	 * @return The board state loaded from the given filepath
	 * @throws IOException
	 */
	public static void loadBoardState(String fileName) throws IOException {
		Scanner in = new Scanner(new BufferedReader(new FileReader(fileName)));
		String[] line = in.nextLine().trim().split(" ");
		System.out.println(Arrays.toString(line));
		int rows = 1;
		int columns = line[0].length();
		while (in.hasNextLine()) {
			rows++;
			in.nextLine();
		}
		int[][] board = deadState(rows, columns);
		for (int i = 0; i < rows; i++) {
			String cells[] = line[i].trim().split(" ");
			for (int j = 0; j < columns; j++) {
				board[i][j] = Integer.parseInt(cells[j]);
			}
		}
		in.close();
	}
}
