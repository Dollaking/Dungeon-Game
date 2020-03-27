package unsw.dungeon;

import java.util.ArrayList;
import java.util.Stack;

public class MoveTowardPlayer implements MoveBehaviour{
	
	public MoveTowardPlayer() {
		
	}
	
	/*
	@Override
	public void perform(Player p, Enemy e, Dungeon dungeon) {
		// TODO Auto-generated method stub
		Dummy start = new Dummy(e.getX(), e.getY(), dungeon);
		Dummy dest = new Dummy(p.getX(), p.getY(), dungeon);
		ArrayList<Dummy> visited = new ArrayList <Dummy> ();
		
		Dummy left = new Dummy(start.getX() - 1, start.getY(), dungeon);
		Dummy right = new Dummy(start.getX() + 1, start.getY(), dungeon);
		Dummy up = new Dummy(start.getX(), start.getY() - 1, dungeon);
		Dummy down = new Dummy(start.getX(), start.getY() + 1, dungeon);
		
		if (simpleAlgo(left, dest, dungeon, visited, 0) == true) {
			e.moveLeft();
		} else if (simpleAlgo(right, dest, dungeon, visited, 0) == true) {
			e.moveRight();
		} else if (simpleAlgo(up, dest, dungeon, visited, 0) == true) {
			e.moveUp();
		} else if (simpleAlgo(down,dest,dungeon,visited, 0) == true) {
			e.moveDown();
		}			
		
	}*/
	
	public boolean simpleAlgo(Dummy start, Dummy dest, Dungeon dungeon, ArrayList<Dummy> visited, int steps) {
		
		// Checks if arrived at destination (i.e. Player).
		if (start.equals(dest)) {
			return true;
		}
		
		// Checks if square has already been visited.
		if (visited.contains(start)) {
			return false;
		}
		
		// Checks if future position is not a tangible object.
		if (start.isPathBlocked(start.getX(), start.getY())) {
			visited.add(start);
			return false;
		}
		
		// Adds position to visited.
		visited.add(start);
		
		if (steps == 100) {
			return true;
		}
		
		// Creates new set of dummies.
		Dummy left = new Dummy(start.getX() - 1, start.getY(), dungeon);
		Dummy right = new Dummy(start.getX() + 1, start.getY(), dungeon);
		Dummy up = new Dummy(start.getX(), start.getY() - 1, dungeon);
		Dummy down = new Dummy(start.getX(), start.getY() + 1, dungeon);
		
		if (simpleAlgo(left, dest, dungeon, visited, steps++) == true) {
			return true;
		} else if (simpleAlgo(right, dest, dungeon, visited, steps++) == true) {
			return true;
		} else if (simpleAlgo(up, dest, dungeon, visited, steps++) == true) {
			return true;
		} else if (simpleAlgo(down,dest,dungeon,visited, steps++) == true) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Gets the minimum manhattan result in order to run towards the player
	 */
	@Override
	public void perform(Player p, Enemy e, Dungeon dungeon) {
		// TODO Auto-generated method stub
		Dummy left = new Dummy(e.getX() - 1, e.getY(), dungeon);
		Dummy right = new Dummy(e.getX() + 1, e.getY(), dungeon);
		Dummy up = new Dummy(e.getX(), e.getY() - 1, dungeon);
		Dummy down = new Dummy(e.getX(), e.getY() + 1, dungeon);
		
		Dummy min = null;
		int minValue = Integer.MAX_VALUE;
		
		
		int currentDistance = manhattan(p, e);
		
		if (manhattan(p, left) < minValue) {
			minValue = manhattan(p, left);
			min = left;
		}		
		if (manhattan(p, right) < minValue) {
			minValue = manhattan(p, right);
			min = right;
		}	
		if (manhattan(p, up) < minValue) {
			minValue = manhattan(p, up);
			min = up;
		}	
		if (manhattan(p, down) < minValue) {
			minValue = manhattan(p, down);
			min = down;
		}
		if (minValue < currentDistance) {
			if (min.equals(down)) {
				e.moveDown();
			} else if (min.equals(up)) {
				e.moveUp();
			} else if (min.equals(right)) {
				e.moveRight();
			} else if (min.equals(left)) {
				e.moveLeft();
			}
		} 
	}
	
	/*public boolean DFS (Player p, Dummy e) {
		Dummy temp;
		stack.add(e);
		while (!stack.empty()) {
			temp = stack.pop();
			if (isDestination(p, temp)) {
				return true;
			} else {
				Dummy left = new Dummy(temp.getX() - 1, temp.getY(), dungeon);
				Dummy right = new Dummy(temp.getX() + 1, temp.getY(), dungeon);
				Dummy up = new Dummy(temp.getX(), temp.getY() - 1, dungeon);
				Dummy down = new Dummy(temp.getX(), temp.getY() + 1, dungeon);
				
				searchNeighbour(left);
				searchNeighbour(right);
				searchNeighbour(up);
				searchNeighbour(down);
				
				visited[temp.getX()][temp.getY()] = true;
				
			}
		}
		return false;
	}

	private void searchNeighbour(Dummy left) {
		if (!visited[left.getX()][left.getY()]) {
			visited[left.getX()][left.getY()] = true;
			stack.add(left);
		}
	}
	
	public boolean isDestination(Player p, Dummy e) {
		if (p.getX() == e.getX() && p.getY() == e.getY()) {
			return true;
			
		} else {
			return false;
		}
	}*/
	
	/**
	 * Performs a basic move for enemy AI when they are blocked by
	 * a tangible object.
	 * @param e The enemy entity.
	 * @param dungeon The dungeon entity.
	 */
	private void moveDefault(Enemy e, Dungeon dungeon) {
		//System.out.println("Gets here default");
		Dummy left = new Dummy(e.getX() - 1, e.getY(), dungeon);
		Dummy right = new Dummy(e.getX() + 1, e.getY(), dungeon);
		Dummy up = new Dummy(e.getX(), e.getY() - 1, dungeon);
		Dummy down = new Dummy(e.getX(), e.getY() + 1, dungeon);
		if (left.isPathBlocked(left.getX(), left.getY())) {
			if (right.isPathBlocked(right.getX(), right.getY())) {
				if (up.isPathBlocked(up.getX(), up.getY())) {
					if (!down.isPathBlocked(down.getX(), down.getY())) {
						e.moveDown();
					}
				} else {
					e.moveUp();
				}
			} else {
				e.moveRight();
			}
		} else {
			e.moveLeft();
		}
	}
	
	/**
	 * Manhattan formula
	 * @param p The player entity
	 * @param e The enemy entity
	 * @return The int manhattan result
	 */
	private int manhattan(Movable p, Movable e) {
		if (e.EntityOnPath(e.getX(), e.getY()).contains(p)) {
			return Integer.MIN_VALUE;
		}
		if (e.isPathBlocked(e.getX(), e.getY())) {
			return Integer.MAX_VALUE;
		}
		return Math.abs(p.getX() - e.getX()) + Math.abs(p.getY() - e.getY());
	}
	
}
