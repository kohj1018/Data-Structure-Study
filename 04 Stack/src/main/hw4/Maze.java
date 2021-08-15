package hw4;
/*
 * CSE2010 Homework #4: Maze.java
 *
 * Complete the code below.
 */

import java.util.Arrays;

public class Maze {
	private final int numRows;
	private final int numCols;

	private int[][] maze;
	private boolean[][] visited;

	private final Location entry; // Entry Location
	private final Location exit;  // Exit Location

	private final ArrayStack<Location> _stack = new ArrayStack<>(100);

	public Maze(int[][] maze, Location entry, Location exit) {
		this.maze = maze;
		numRows = maze.length;
		numCols = maze[0].length;
		visited = new boolean[numRows][numCols]; // initialized to false

		this.entry = entry;
		this.exit = exit;
	}

	// For testing purpose
	public void printMaze() {
		System.out.println("Maze[" + numRows + "][" + numCols + "]");
		System.out.println("Entry index = (" + entry.row + ", " + entry.col + ")");
		System.out.println("Exit index = (" + exit.row + ", " + exit.col + ")" + "\n");

		for (int i = 0; i < numRows; i++) {
			System.out.println(Arrays.toString(maze[i]));
		}
		System.out.println();
	}

	public boolean findPath() {
		return moveTo(entry.row, entry.col);
	}

	private boolean moveTo(int row, int col) {
		if ( (row == exit.row) && (col == exit.col) ) {
			_stack.push(exit);
			return true;
		} else {
			visited[row][col] = true;

			if (row-1 >= 0 && maze[row-1][col] == 0 && !visited[row-1][col]) {
				_stack.push(new Location(row, col));
				return moveTo(row-1, col);
			} else if (col+1 <= numCols-1 && maze[row][col+1] == 0 && !visited[row][col+1]) {
				_stack.push(new Location(row, col));
				return moveTo(row, col+1);
			} else if (row+1 <= numRows-1 && maze[row+1][col] == 0 && !visited[row+1][col]) {
				_stack.push(new Location(row, col));
				return moveTo(row+1, col);
			} else if (col-1 >= 0 && maze[row][col-1] == 0 && !visited[row][col-1]) {
				_stack.push(new Location(row, col));
				return moveTo(row, col-1);
			} else if (row-1 >= 0 && maze[row-1][col] == 0) {
				maze[row][col] = 1;
				_stack.pop();
				return moveTo(row-1, col);
			} else if (col+1 <= numCols-1 && maze[row][col+1] == 0) {
				maze[row][col] = 1;
				_stack.pop();
				return moveTo(row, col+1);
			} else if (row+1 <= numRows-1 && maze[row+1][col] == 0) {
				maze[row][col] = 1;
				_stack.pop();
				return moveTo(row+1, col);
			} else if (col-1 >= 0 && maze[row][col-1] == 0) {
				maze[row][col] = 1;
				_stack.pop();
				return moveTo(row, col-1);
			} else {
				_stack.pop();

				_stack.pop();	// entry Location을 pop()
				return false;
			}
		}
	}

	public String getPath() {
		StringBuilder builder = new StringBuilder();
		while (!_stack.isEmpty()) {
			builder.append(_stack.pop() + " <-- ");
		}
		builder.append("Start");
		return builder.toString();
	}

}
