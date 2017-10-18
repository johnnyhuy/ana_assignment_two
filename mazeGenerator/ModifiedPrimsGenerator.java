package mazeGenerator;

import java.util.*;
import maze.Maze;
import maze.Cell;
import mazeGenerator.Utility;
import maze.Navigator;

public class ModifiedPrimsGenerator implements MazeGenerator {
	
	private int sizeR, sizeC;
	private int numCells, numVisited = 0;
	private Navigator navigator;
	private final ArrayList<Cell> frontier = new ArrayList<Cell>(numCells);
	private final HashSet<Cell> visited = new HashSet<Cell>();
	private Cell currCell;

	@Override
	public void generateMaze(Maze maze) {
		int r = Utility.getRandom(maze.sizeR);
		int c = Utility.getRandom(maze.sizeC);
		int numCells = maze.sizeR * sizeC;
		navigator = new Navigator(maze.type, maze.sizeR, maze.sizeC);

		if (maze.type == Maze.HEX) {
			c += (r+1)/2;
		}
		currCell = maze.map[r][c];
		visited.add(currCell);
		updateFrontier(currCell, frontier);
		while (!frontier.isEmpty()) {
			increaseSpan(frontier.remove(Utility.getRandom(frontier.size())));
		}
	} // end of generateMaze() 


	private void increaseSpan(Cell root) {
		currCell = root;
			
		int nextDir = chooseRandomNeighbour(currCell);

		if (nextDir == -1) {
			return;
		}
		currCell.wall[nextDir].present = false;
		visited.add(currCell);
		updateFrontier(currCell, frontier);
			
	}

	private int chooseRandomNeighbour(Cell root) {
		int[] directionList = navigator.getDirectionList();
		Utility.shuffle(directionList);

		for (int dir : directionList) {
			Cell neigh = root.neigh[dir];
			if (navigator.canEnter(neigh) && visited.contains(neigh)) {
				return dir;
			}
		}
		return -1;
	}

	/**
	 * Very important to not allow duplicates to be entered in frontier
	 * collection, or they fail to remove and carve the path, could be prevented 
	 * with set. Same issue if the cells are re-inserted into visited set
	 */
	private void updateFrontier(Cell root, ArrayList<Cell> frontier) {
		int[] directionList = navigator.getDirectionList();
		for (int dir : directionList) {
			Cell neigh = root.neigh[dir];
			if (navigator.canEnter(neigh) 
					&& !visited.contains(neigh)
					&& !frontier.contains(neigh)) {
				frontier.add(neigh);
			}
		}
	}

} // end of class ModifiedPrimsGenerator
