package mazeGenerator;

import maze.Maze;
import maze.Cell;
import mazeGenerator.Utility;
import maze.Navigator;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	private Navigator navigator;

	@Override
	public void generateMaze(Maze maze) {
		int r = maze.sizeR, c = maze.sizeC;
		int randR = Utility.getRandom(r), randC = Utility.getRandom(c);
		navigator = new Navigator(maze.type, r, c);

		if (maze.type == maze.HEX) {
			randC += (randR+1)/2;
		}

		Cell root = maze.map[randR][randC];
		depthFirstTraverse(root);
	} // end of generateMaze()

	public void depthFirstTraverse(Cell root) {
		navigator.visit(root.r, root.c);
		int[] directions = navigator.getDirectionList();
		Utility.shuffle(directions);

		if (root.tunnelTo != null && !navigator.hasVisited(root.tunnelTo)) {
			depthFirstTraverse(root.tunnelTo);
		}

		for (int dir : directions) {
			Cell neigh = root.neigh[dir];
			if (navigator.canEnter(neigh) && !navigator.hasVisited(neigh)) {
				root.wall[dir].present = false;
				depthFirstTraverse(neigh);
			}			
		}	
	}
} // end of class RecursiveBacktrackerGenerator
