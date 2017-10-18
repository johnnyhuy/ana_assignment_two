package mazeSolver;

import java.util.*;
import maze.Maze;
import maze.Cell;
import mazeGenerator.Utility;
import maze.Navigator;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {

	private Navigator navigator;
	private Cell entrance, exit, t1Cell, t2Cell;
	private ArrayList<Cell> tracker1;
	private ArrayList<Cell> tracker2;
	private boolean trackingFromEntrance = true;
	private static int count = 0;

	@Override
	public void solveMaze(Maze maze) {
		int r = maze.sizeR, c = maze.sizeC;
		navigator = new Navigator(maze.type, r, c);
		tracker1 = new ArrayList<Cell>(r*c);
		tracker2 = new ArrayList<Cell>(r*c);

		entrance = maze.entrance;
		exit = maze.exit;
		t1Cell = entrance;
		t2Cell = exit;

		depthFirstSearch(entrance, maze, tracker1);

	} // end of solveMaze()

	public void depthFirstSearch(Cell root, Maze maze, ArrayList<Cell> tracker) {
		count++;
		tracker.add(root);
		int[] directions = navigator.getDirectionList();
		Utility.shuffle(directions);

		if (root.tunnelTo != null && !tracker.contains(root.tunnelTo)) {
			depthFirstSearch(root.tunnelTo, maze, tracker);
		}

		for (int dir : directions) {
			Cell neigh = root.neigh[dir];
			if (navigator.canEnter(neigh) 
					&& !root.wall[dir].present
					&& !tracker.contains(neigh)) {
				maze.drawFtPrt(neigh);
				if (!isSolved()) {
					trackingFromEntrance = !trackingFromEntrance;
					if (trackingFromEntrance) {
						t1Cell = neigh;
						depthFirstSearch(t2Cell, maze, tracker2);
					} else {
						t2Cell = neigh;
						depthFirstSearch(t1Cell, maze, tracker1);
					}
				}
			}
				
		}
	}


	@Override
	public boolean isSolved() {
		return tracker1.contains(exit) || tracker2.contains(exit);
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		return count;
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
