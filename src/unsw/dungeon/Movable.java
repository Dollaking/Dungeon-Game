package unsw.dungeon;

import java.util.ArrayList;

public abstract class Movable extends InteractableEntities {
	
	/**
	 * Constructor for movable where coords, description and current dungeon is given
	 * @param x The x coords
	 * @param y The y coords
	 * @param desc The description in string form
	 * @param dungeon The current dungeon
	 */
	public Movable(int x, int y, String desc, Dungeon dungeon, boolean visibility, boolean tangible) {
		super(x, y, desc, dungeon, visibility, tangible);
	}

	
	/**
	 * This checks if there is a tangible entity on the way
	 * @param futurePosX The x coords that the entity wants to move to
	 * @param futurePosY The y coords that the entity wants to move to
	 * @return Whether the path is blocked
	 */
	public boolean isPathBlocked(int futurePosX, int futurePosY) {
		if (futurePosX < 0 || futurePosX > (this.getDungeon().getWidth() - 1) ||
				futurePosY < 0 || futurePosY > (this.getDungeon().getHeight() -1) ) {
			return true;
		}
		
		ArrayList <Entity> e = EntityOnPath(futurePosX, futurePosY);
		for (Entity entity : e) {
			if (entity != null && entity.isTangible()) {
				return true;
			}
			
		}
		return false;

	}
	
	/**
	 * Gathers All entity on that location
	 * @param futurePosX X coord of where entity is searched
	 * @param futurePosY Y coord of where entity is searched
	 * @return a list of entities on the coords
	 */
	public ArrayList<Entity> EntityOnPath(int futurePosX, int futurePosY) {
		ArrayList<Entity> entityList = new ArrayList<Entity> ();
		for (Entity e : this.getDungeon().getEntityList()) {
			
			if (e.getX() == futurePosX && e.getY() == futurePosY) {
				
				entityList.add(e);
			}
		}
			
		return entityList;
	}
	
	/**
	 * Moving entity up
	 */
	
	public void moveUp() {
		ArrayList<Entity> entityList = EntityOnPath(this.getX(), this.getY() - 1);
		boolean result = true;
	
		for(Entity e : entityList) {
			interactEntity(e);
			
			if ( !(this.getY() > 0 && !this.isPathBlocked(this.getX(), this.getY() - 1))) {
				result = false;
			} 
		}
		if (result == true) {
			this.setY(this.getY() - 1);
		}
	}
	/**
	 * Moving entity down
	 */
	public void moveDown() {
		ArrayList <Entity> entityList = EntityOnPath(this.getX(), this.getY() + 1);
		boolean result = true;
		for(Entity e : entityList) {
			interactEntity(e);
			
			if ( !(this.getY() < (this.getDungeon().getHeight() - 1) && !this.isPathBlocked(this.getX(), this.getY() + 1)) ) {
				result = false;
			}
		}
		if (result == true) {
			this.setY(this.getY() + 1);
		}

	}
	/**
	 * Moving entity left
	 */
	public void moveLeft() {
		ArrayList<Entity> entityList = EntityOnPath(this.getX() - 1, this.getY());
		boolean result = true;
		for (Entity e : entityList) {
			interactEntity(e);
			
			if ( !(this.getX() > 0 && !this.isPathBlocked(this.getX() - 1, this.getY())) ) {
				result = false;
			}
		}
		
		if (result  == true) {
			this.setX(this.getX() - 1);
		}
		
	}
	/**
	 * Move entity right
	 */
	public void moveRight() {
		ArrayList<Entity> entityList = EntityOnPath(this.getX() + 1, this.getY());
		boolean result = true;
		
		for (Entity e : entityList) {
			interactEntity(e);
			
			if ( !(this.getX() < (this.getDungeon().getWidth() - 1) && !this.isPathBlocked(this.getX() + 1, this.getY())) ) {
				result = false;
			}
		}
		
		if (result == true) {
			this.setX(this.getX() + 1);
		}

		
	}
	/**
	 * Extract and activate effect of entity, if there is entity around the movable entity
	 * @param e The entity that is around the movable entity
	 */
	private void interactEntity(Entity e) {
		if (e != null) {
			e.interactEffect(this);
			System.out.println("You have interacted with " + e.getDescription());
		}
	}
	
	


}
