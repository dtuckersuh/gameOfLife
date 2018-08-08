import java.io.FileNotFoundException;
import java.util.Arrays;

public class gameOfLifeUnitTests {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// Test 1: Dead cells with no live neighbors
		// should stay dead
		int[][] currentState = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
		int[][] expectedNextState = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
		compare(currentState, expectedNextState, 1);

		// Test 2: dead cells with exactly 3 neighbors
		// should come alive
		int[][] currentState2 = { { 0, 0, 1 }, { 0, 1, 1 }, { 0, 0, 0 } };
		int[][] expectedNextState2 = { { 0, 1, 1 }, { 0, 1, 1 }, { 0, 0, 0 } };
		compare(currentState2, expectedNextState2, 2);

		// Test 2: Cells on the edge
		int[][] currentState3 = { { 1, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 } };
		int[][] expectedNextState3 = { { 0, 0, 1 }, { 1, 0, 0 }, { 0, 1, 1 } };
		compare(currentState3, expectedNextState3, 3);

		// Test 4: More than 3 live neighbors
		int[][] currentState4 = { { 0, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1 }, { 0, 0, 1, 1, 0 }, { 0, 1, 1, 0, 1 } };
		int[][] expectedNextState4 = { { 0, 1, 1, 0, 1 }, { 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0 } };

		compare(currentState4, expectedNextState4, 4);
		// gameOfLife.eternalLife(gameOfLife.randomState(5, 10));
		// Toad state
		gameOfLife.eternalLife(gameOfLife.loadBoardState("./toad.txt"));
	}

	public static void compare(int[][] actualState, int[][] expectedState, int testNumber) {
		int[][] actualNextState = gameOfLife.nextState(actualState);
		if (Arrays.deepEquals(expectedState, actualNextState)) {
			System.out.println("PASSED " + testNumber);
		} else {
			System.out.println("FAILED " + testNumber);
			System.out.println("Expected: ");

			gameOfLife.render(expectedState);
			System.out.println();
			System.out.println("Actual: ");
			gameOfLife.render(actualNextState);
		}
	}
}
