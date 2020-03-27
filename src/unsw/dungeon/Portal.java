package unsw.dungeon;

public class Portal extends Unmovable{
	
	private int id;
	
	/**
	 * Constructor for portal when coords, linking id and current dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param id Links portal to another portal of matching ID.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Portal (int x, int y, int id, Dungeon dungeon) {
		super(x, y, "Portal", dungeon, true, false);
		this.id = id;
	}
	
	/**
	 * Getter of linking id
	 * @return Linking id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for linking id
	 * @param id Linking id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sends player to the other portal on the other side
	 * @param p The player entering the portal
	 */
	public void sendPlayerToOtherPortal(Player p) {
		for (Entity e : this.getDungeon().getEntityList()) {
			if (e.getDescription().equals("Portal") && !e.equals(this)) {
				Portal portal = (Portal) e;
				if (portal.getId() == this.getId()) {
					p.setX(portal.getX());
					p.setY(portal.getY());
					System.out.println("Travelling through portal!");
					break;
				}
			}
		}

		
	}

	/**
	 * Template interact effect
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Player")) {
			sendPlayerToOtherPortal((Player) entity);
			
		}
		
		
	}

}
