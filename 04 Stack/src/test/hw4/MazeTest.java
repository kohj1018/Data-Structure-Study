package hw4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    // System Under Test
    private Maze maze;

    private int[][] testMaze = new int[][] {
            { 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 1 },
            { 1, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0 }
    };

    @Test
    void testMaze1_1() {
        maze = new Maze(testMaze, new Location(0, 0), new Location(4, 4));
        String expectedPath = "[4,4] <-- [3,4] <-- [3,3] <-- [2,3] <-- [1,3] <-- [1,2] <-- [1,1] <-- [1,0] <-- [0,0] <-- Start";

        assertTrue(maze.findPath());
        assertEquals(expectedPath, maze.getPath());
    }

    @Test
    void testMaze1_2() {
        maze = new Maze(testMaze, new Location(4, 4), new Location(0, 0));
        String expectedPath = "[0,0] <-- [1,0] <-- [1,1] <-- [1,2] <-- [1,3] <-- [2,3] <-- [3,3] <-- [3,4] <-- [4,4] <-- Start";

        assertTrue(maze.findPath());
        assertEquals(expectedPath, maze.getPath());
    }

    @Test
    void testMaze1_3() {
        maze = new Maze(testMaze, new Location(2, 0), new Location(4, 4));
        String expectedPath = "[4,4] <-- [3,4] <-- [3,3] <-- [2,3] <-- [1,3] <-- [1,2] <-- [1,1] <-- [1,0] <-- [2,0] <-- Start";

        assertTrue(maze.findPath());
        assertEquals(expectedPath, maze.getPath());
    }

    @Test
    void testMaze1_4() {
        maze = new Maze(testMaze, new Location(0, 4), new Location(4, 4));
        String expectedPath = "[4,4] <-- [3,4] <-- [3,3] <-- [2,3] <-- [1,3] <-- [1,4] <-- [0,4] <-- Start";

        assertTrue(maze.findPath());
        assertEquals(expectedPath, maze.getPath());
    }

    @Test
    void testMaze1_5() {
        maze = new Maze(testMaze, new Location(0, 4), new Location(4, 0));
        String expectedPath = "Start";  // No paths exist

        assertFalse(maze.findPath());
        assertEquals(expectedPath, maze.getPath());
    }
}
