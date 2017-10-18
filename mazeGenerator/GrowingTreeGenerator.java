package mazeGenerator;

import java.util.*;

import java.util.*;
import maze.Maze;
import maze.Cell;
import maze.Navigator;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	double threshold = 0.1;
	static int count = -1;
	private int numVisited = 0;
	private Navigator navigator;
	private ArrayList<Cell> visited;

	@Override
	public void generateMaze(Maze maze) {
		
		int r = maze.sizeR, c = maze.sizeC;
		int numCells = r * c;
		int randR = Utility.getRandom(r), randC = Utility.getRandom(c);
		navigator = new Navigator(maze.type, r, c);
		visited = new ArrayList<Cell>(numCells);

		if (maze.type == maze.HEX) {
			randC += (randR+1)/2;
		}

		Cell currCell = maze.map[randR][randC];

		depthFirstTraverse(currCell);

	}

	public boolean underThreshold() {
		count++;
		double percent = count  * threshold;
		if ((percent == Math.floor(percent)) && !Double.isInfinite(percent)) {
			return true;
		}
		return false;
	}

	public void visit(Cell root) {
		visited.add(root);
		numVisited++;
	}

	public void depthFirstTraverse(Cell root) {
		visit(root);
		int[] directions = navigator.getDirectionList();
		Utility.shuffle(directions);

		if (!underThreshold()) {
			int n = Utility.getRandom(visited.size());
			depthFirstTraverse(visited.get(n));
		}

		for (int dir : directions) {
			Cell neigh = root.neigh[dir];
			if (navigator.canEnter(neigh) && !visited.contains(neigh)) {
				root.wall[dir].present = false;
				depthFirstTraverse(neigh);
			}			
		}	
	}
/*	public Cell carvePath(Cell root) {
		int[] directions = navigator.getDirectionList();
		Utility.shuffle(directions);

		for (int dir : directions) {
			Cell neigh = root.neigh[dir];
			if (navigator.canEnter(neigh) && !visited.contains(neigh)) {
				root.wall[dir].present = false;
				visit(root);
				return neigh;
			}			
		}
		return null;
	}*/

}
