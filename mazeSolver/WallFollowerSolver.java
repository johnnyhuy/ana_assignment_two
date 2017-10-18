package mazeSolver;

import maze.Maze;
import maze.Cell;
import maze.Navigator;

/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver {
	private Navigator navigator;
	private int[] directionList; 
	Cell currPosition, target;
	private static int cellsExplored = 0;

	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub
		navigator = new Navigator(maze.type, maze.sizeR, maze.sizeC);

		directionList = navigator.getDirectionList();
		currPosition = maze.entrance;
		target = maze.exit;
		int n = 0, face = Maze.NORTH, left, right;
		int[] priorityList = { Maze.EAST, Maze.NORTH };

		maze.drawFtPrt(maze.entrance);

		while (!isSolved()) {
			makeMove(maze, priorityList);
		}
        
	} // end of solveMaze()

	public void makeMove(Maze maze, int[] priorityList) {
		int[] pL = priorityList;
		for (int n=0; n<priorityList.length; n++) {
			int dir = priorityList[n];
			System.out.println("DEBUG: " + dir);
			if (!currPosition.wall[dir].present
					&& navigator.canEnter(currPosition.neigh[dir])) {
				currPosition = currPosition.neigh[dir];
				maze.drawFtPrt(currPosition);
				cellsExplored++;
				System.out.printf("CurrPosition: %d,%d\n", currPosition.r, currPosition.c);
				for (int i=0; i<priorityList.length; i++) {
					priorityList[i] = rightRotation(priorityList[i]);
				}
				return;
			} 
			
		}
		System.out.println("Left Rotation");
		for (int i=0; i<priorityList.length; i++) {
			priorityList[i] = leftRotation(priorityList[i]);
		}
	}

	public int rightRotation(int dir) {
		switch (dir) {
			case Maze.NORTH: return Maze.EAST;
			case Maze.NORTHEAST: return Maze.SOUTHEAST;
			case Maze.EAST: return Maze.SOUTH;
			case Maze.SOUTHWEST: return Maze.NORTHWEST;
			case Maze.SOUTH: return Maze.WEST;
			case Maze.WEST: return Maze.NORTH;
		}
		return 0;
	}

	public int leftRotation(int dir) {
		switch (dir) {
			case Maze.NORTH: return Maze.WEST;
			case Maze.NORTHEAST: return Maze.NORTHWEST;
			case Maze.EAST: return Maze.NORTH;
			case Maze.SOUTH: return Maze.EAST;
			case Maze.SOUTHWEST: return Maze.NORTHWEST;
			case Maze.WEST: return Maze.SOUTH;
		}
		return 0;
	}

	public void normalAlgorithm(Maze maze) {

		/*int n = 0, right, face, left;
		
		while (!isSolved()) {
			right = directionList[rotateRight(n)];
			face = directionList[n];
			left = directionList[rotateLeft(n)];
			
			if (!currPosition.wall[right].present
					&& navigator.canEnter(currPosition.neigh[right])) {
				n = rotateRight(n);
				currPosition = currPosition.neigh[right];
				maze.drawFtPrt(currPosition);
				cellsExplored++;
			} 
			else if (!currPosition.wall[face].present
					&& navigator.canEnter(currPosition.neigh[face])) {
				currPosition = currPosition.neigh[face];
				maze.drawFtPrt(currPosition);
				cellsExplored++;
			}
			else {
				n = rotateLeft(n);
			}
		}*/
	}

	public void hexAlgorithm(Maze maze) {
		int n = 0, temp, rearRight, right, topRight;

		while (!isSolved()) {
			temp = rotateRight(n); 
			topRight = directionList[temp];
			temp = rotateRight(temp); 
			right = directionList[temp];
			temp = rotateRight(temp); 
			rearRight = directionList[temp];

			if (!currPosition.wall[topRight].present
					&& navigator.canEnter(currPosition.neigh[topRight])) {
				
					n = rotateRight(n);


				currPosition = currPosition.neigh[right];
				maze.drawFtPrt(currPosition);
				cellsExplored++;
			} 
			else if (!currPosition.wall[right].present
					&& navigator.canEnter(currPosition.neigh[right])) {
				
				for (int i=0; i<2; i++) {
					n = rotateRight(n);
				}

				currPosition = currPosition.neigh[right];
				maze.drawFtPrt(currPosition);
				cellsExplored++;
			} 
			else if (!currPosition.wall[rearRight].present
					&& navigator.canEnter(currPosition.neigh[rearRight])) {
				
				for (int i=0; i<3; i++) {
					n = rotateRight(n);
				}

				currPosition = currPosition.neigh[rearRight];
				maze.drawFtPrt(currPosition);
				cellsExplored++;
			} 
			else {
				n = rotateLeft(n);
			}

		}
	}

	public int rotateRight(int dir) {
		if (dir == directionList.length-1) {
			return 0;
		}
		return ++dir;
	}

	public int rotateLeft(int dir) {
		if (dir == 0) {
			return directionList.length-1;
		}
		return --dir;
	}
    
	@Override
	public boolean isSolved() {
		return (currPosition.equals(target));
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		return cellsExplored;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
