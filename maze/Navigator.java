package maze;

import maze.Maze;
import maze.Cell;

/**
 * Implements Navigator
 */

public class Navigator {
	
	private final int TWO_NEIGHBOURS = 2;
	private final int THREE_NEIGHBOURS = 3;
	private final int FOUR_NEIGHBOURS = 4;
	private final int SIX_NEIGHBOURS = 6;
	
	private boolean visited[][];
	private int sizeR, sizeC;
	private int type;

	public Navigator(int type, int r, int c) {
		this.type = type;
		this.sizeR = r;
		this.sizeC = c;
		visited = new boolean[sizeR][sizeC];
	}

	private boolean canEnter(int r, int c) {
		return r >= 0 && r < sizeR && c >= 0 && c < sizeC;
	}

	public boolean canEnter(Cell cell) {
		if (cell == null)
			return false;
		return true;
	}

	public boolean isAtCorner(int r, int c) {
		if (type == Maze.HEX) {
			c -= (r+1)/2;
		}
		if (canEnter(r, c) && ((r==0 && c==0) ||
					(r==0 && c==sizeC-1) || (r==sizeR-1 && c==0) ||
					(c==sizeC-1 && r==sizeR-1)))
			return true;
		return false;
	}

	private int getNumAdjacencies(Cell root) {
		if (type == Maze.HEX) {
			int c = (root.r+1)/2;
			if (isAtCorner(root.r, root.c) && root.r == root.c-c) {
				return TWO_NEIGHBOURS;
			}
			else if (isOnEdge(root.r, root.c))
				return FOUR_NEIGHBOURS;
			else
				return SIX_NEIGHBOURS;
		}  
		
		if (isAtCorner(root.c, root.r))
			return TWO_NEIGHBOURS;
		if (isOnEdge(root.r, root.c))
			return THREE_NEIGHBOURS;
		else
			return FOUR_NEIGHBOURS;
	}

	public boolean isOnEdge(int r, int c) {
		if (type == Maze.HEX) {
			c -= (r+1)/2;
		}
		if (canEnter(r, c) && (r==0 || r==sizeR-1 || c==0 || c==sizeC-1))
			return true;
		return false;
	}

	public boolean hasVisited(Cell cell) {
		if (type == Maze.HEX) {
			return visited[cell.r][cell.c - (cell.r + 1) /2];
		}
		return visited[cell.r][cell.c];
	}

	public void visit(int r, int c) {
		if (type == Maze.HEX) {
			c -= (r+1)/2;
		}
		visited[r][c] = true;
	}

	public int[] getDirectionList() {
		if (type == Maze.HEX) {
			int[] directions = { Maze.NORTHEAST, Maze.EAST,
				Maze.SOUTHEAST, Maze.SOUTHWEST, Maze.WEST, Maze.NORTHWEST };
			return directions;
		}
		int[] directions = { Maze.NORTH, Maze.EAST, Maze.SOUTH, Maze.WEST };
		return directions;
	}

	public int[] getAdjacencyList(Cell root) {
		int[] directionList = getDirectionList(), adjacencyList = null;
		int n = 0;

		adjacencyList = new int[getNumAdjacencies(root)];

		for (int dir : directionList) {
			if (canEnter(root.neigh[dir])) {
				adjacencyList[n] = dir;
				n++;
			}
		}
		return adjacencyList;
	}

} // end of class Navigator
