package unsw.dungeon;

public class Boulder extends Movable{

	/**
	 * The constructor of boulder where coords and dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Boulder(int x, int y, Dungeon dungeon) {
		super(x, y, "Boulder", dungeon, true, true);
	}
	
	/**
	 * Pushing the boulder
	 * @param p The player who is pushing the boulder
	 */
	public void pushBoulder(Player p) {
		if (p.getX() == this.getX() - 1) {
			this.moveRight();
		} else if (p.getX() == this.getX() + 1) {
			this.moveLeft();
		} else if (p.getY() == this.getY() - 1) {
			this.moveDown();
		} else if (p.getY() == this.getY() + 1) {
			this.moveUp();
		}
	}

	/**
	 * Template interactEffect function.
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Player")){
			
			Player p = (Player) entity;
			int oldX = this.getX();
			int oldY = this.getY();
			pushBoulder(p);
			if (this.getX() != oldX || this.getY() != oldY) {
				p.setX(oldX);
				p.setY(oldY);
				System.out.println("Player moved with boulder");
			}
		}

		
	}

}
