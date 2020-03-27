package unsw.dungeon;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Movable {

    private Key key;
    private Sword sword;
    private Potion potion;
    private int treasure;
    private BooleanProperty isInvincible;
    private BooleanProperty isAlive;
    private BooleanProperty hasSword;
    private int potionSecLeft;
    
    /**
     * Constructor for player when coords and current dungeon is provided
     * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
     * @param dungeon The dungeon in which entity exists.
     */
    public Player(int x, int y, Dungeon dungeon) {
    	super(x, y, "Player", dungeon, true, true);
		this.isInvincible = new SimpleBooleanProperty(false);
		this.potion = null;
		this.sword = null;
		this.key = null;
		this.isAlive = new SimpleBooleanProperty(true);
		this.treasure = 0;
		this.hasSword = new SimpleBooleanProperty(false);
		potionSecLeft = 0;
		
		// Listener for when player is invincible.
		isInvincible.addListener(new ChangeListener<Boolean> () {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				ArrayList<Enemy> enemyList = new ArrayList<Enemy> ();
				for (Entity e : dungeon.getEntityList()) {
					if (e.getDescription().equals("Enemy")) {
						enemyList.add((Enemy) e);
					}
				}
				for (Enemy enemy : enemyList) {
					if (isInvincible()) {
						enemy.setMoveBehaviour(new MoveAwayPlayer());
					} else if (!isInvincible()) {
						enemy.setMoveBehaviour(new MoveTowardPlayer());
					}
				}
			}
			
		});
		
		// Listener for when player is dead.
		isAlive.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				System.out.println("Player dead");
			}
			
		});
	}
    
    /**
     * Setter for whether the player is alive
     * @param b Boolean for whether the player is alive
     */
    public void setIsAlive(boolean b) {
    	this.isAlive.setValue(b);
    }
    /**
     * Checks whether the player is alive
     * @return Boolean on whether player is alive
     */
    public boolean isAlive() {
    	return this.isAlive.getValue();
    }
    
    /**
     * Places all enemies from an entity list into an enemy list
     * @param entityList The entity list
     * @param enemyList The enemy list you want to put the enemies in
     */
    public void placeEnemyList(ArrayList<Entity> entityList, ArrayList<Enemy> enemyList) {
    	for (Entity e : entityList) {
	    	if(e != null && e.getDescription().equals("Enemy")) {
	    		Enemy enemy = (Enemy) e;
	    		if (enemy.isAlive()) {
	    			enemyList.add((Enemy) e);
	    		}
	    	}
    	}
    }
    
    /**
     * Gets all surrounded enemies from the perspective of the player
     * @return A list of enemies
     */
    public ArrayList<Enemy> getSurroundedEnemy() {
    	ArrayList<Enemy> enemyList = new ArrayList<Enemy> ();
    	ArrayList<Entity> above = this.EntityOnPath(this.getX(), this.getY() - 1);
    	ArrayList<Entity> below = this.EntityOnPath(this.getX(), this.getY() + 1);
    	ArrayList<Entity> left = this.EntityOnPath(this.getX() - 1, this.getY());
    	ArrayList<Entity> right = this.EntityOnPath(this.getX() + 1, this.getY());
    	ArrayList<Entity> topLeft = this.EntityOnPath(this.getX() - 1, this.getY() - 1);
    	ArrayList<Entity> topRight = this.EntityOnPath(this.getX() + 1, this.getY() -1);
    	ArrayList<Entity> bottomLeft = this.EntityOnPath(this.getX() - 1, this.getY() + 1);
    	ArrayList<Entity> bottomRight = this.EntityOnPath(this.getX() + 1, this.getY() + 1);
    	
    	placeEnemyList(above, enemyList);
    	placeEnemyList(below, enemyList);
    	placeEnemyList(left, enemyList);
    	placeEnemyList(right, enemyList);
    	placeEnemyList(topLeft, enemyList);
    	placeEnemyList(topRight, enemyList);
    	placeEnemyList(bottomLeft, enemyList);
    	placeEnemyList(bottomRight, enemyList);

    	return enemyList;
    }

    /**
     * Swinging the sword
     */
    public void attackEnemy() {
    	System.out.println("Attempting to swing!");
    	if (this.sword != null) {
    		System.out.println("HAS SWORD");
    		for(Enemy e : getSurroundedEnemy()) {
    			e.damageEnemy();
    		}
    		this.sword.reducedDurability();
    		if (sword.getDurability() <= 0) {
    			this.removeSword();
    			System.out.println("SWORD BROKE");
    		}
    	}
    }
    
    /**
     * Add key to the player
     * @param k The key
     */
    public void addKey(Key k) {
    	this.key = k;
    	
    }
    
    /**
     * Removing key from player
     */
    public void removeKey() {
    	this.key = null;
    }
    /**
     * Adding sword to the player
     * @param s The sword
     */
    public void addSword(Sword s) {
    	this.sword = s;
    	this.hasSword.set(true);
    }
    
    /**
     * Returns the sword currently held by player.
     * @return The sword held by player.
     */
    public Sword getSword() {
    	return sword;
    }
    
    /**
     * Removing Sword from player
     */
    public void removeSword() {
    	this.sword = null;
    	this.hasSword.set(false);
    }
    
    /**
     * Returns the boolean property whether player has sword.
     * @return Boolean Property of whether player has sword.
     */
    public BooleanProperty getHasSword() {
    	return this.hasSword;
    }
    
    /**
     * Checks if player has sword.
     * @return True if player has sword, false otherwise.
     */
    public boolean hasSword() {
    	if (sword == null) return false;
    	return true;
    }
    
    /**
     * Adding potion to the player
     * @param p The potion
     */
    public void addPotion(Potion p) {
    	this.potion = p;
    	this.isInvincible.setValue(true);
    	this.potionSecLeft = 10;
    	System.out.println("You are now invincible!");
    	
    }
    
    /**
     * Checks whether player has potion.
     * @return True if player has potion, false otherwise.
     */
    public boolean hasPotion() {
    	if (potion == null) return false;
    	return true;
    }
    
    /**
     * Remove potion from the player
     */
    public void removePotion() {
    	this.potion = null;
    	this.isInvincible.setValue(false);
    	System.out.println("Invincibility wore off!");
    }
    
    public void decreasePotionTime() {
    	potionSecLeft--;
    }
    
    public int getPotTimeLeft() {
    	return this.potionSecLeft;
    }
    
    /**
     * Adding treasure to the treasure count
     */
    public void addTreasure() {
    	this.treasure++;
    }
    
    /**
     * The number of treasure the player is holding.
     * @return The number of treasure the player is holding.
     */
    public int getTreasure() {
    	return treasure;
    }
    
    /**
     * Checks whether player has key
     * @return
     */
    public boolean hasKey() {
    	if (this.key == null) {
    		return false;
    	}
    	return true;
    }
    /**
     * Getter for player's key
     * @return Key
     */
    public Key getKey() {
    	return this.key;
    }
    
    /**
     * Player drops the key
     */
    public void dropKey() {
    	if (this.getKey() == null) {
    		System.out.println("You dont have key");
    	} else {
    		this.key.setX(this.getX());
    		this.key.setY(this.getY());
    		this.key.setVisibility(true);
    		this.key.setPickedUp(false);
    		this.removeKey();
    		System.out.println("Dropped Key at " + this.getX() + ", " + this.getY());
    	}
    }
    
    /**
     * Getter for whether the place is invincible
     * @return Boolean
     */
    public boolean isInvincible() {
    	return this.isInvincible.getValue();
    }
    
    /**
     * Returns the boolean property for whether the player is invincible or not.
     * @return Whether player is invincible or not.
     */
    public BooleanProperty getIsInvincible() {
    	return isInvincible;
    }
    /**
     * Template InteractEffect 
     */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Enemy")) {
			if(this.potion != null) {
				//This is where you kill the enemy
				Enemy enemy = (Enemy) entity;
				enemy.damageEnemy();
			} else {
				this.setIsAlive(false);
			}
		}	
	}
	
	/**
	 * Formats into a string containing the inventory of the player.
	 * @return A formatted string containing the inventory of the player.
	 */
	public String getInventory() {
		String tmp = "";
		if (hasSword()) tmp = tmp + "\tSword\n";
		if (isInvincible()) tmp = tmp + "\tInvincible\n";
		if (hasKey()) tmp = tmp + "\tKey\n";
		return tmp;
	}
	
	/**
	 * Resets the player attributes.
	 */
	@Override
	public void resetOther() {
	    this.key = null;
	    this.potion = null;
	    this.treasure = 0;
	    this.sword = null;
	    isInvincible.setValue(false);
	    isAlive.setValue(true);
	    hasSword.setValue(false);
	    potionSecLeft = 0;
	   
	}
	
	/**
	 * Getter for whether player is alive.
	 * @return A variable containing whether player is alive or not.
	 */
	public BooleanProperty getIsAlive() {
		return isAlive;
	}


}
