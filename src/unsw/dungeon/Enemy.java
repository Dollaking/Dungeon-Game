package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Enemy extends Movable{

	private MoveBehaviour moveBehaviour;
	private BooleanProperty alive;
	private String type;
	private IntegerProperty life;
	private int originalLife;

	/**
	 * The enemy constructor where coords and current dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Enemy(int x, int y, Dungeon dungeon, String type) {
		super(x, y, "Enemy", dungeon, true, true);
		this.moveBehaviour = new MoveTowardPlayer();
		this.alive = new SimpleBooleanProperty(true);
		this.type = type;
		life = new SimpleIntegerProperty();
		
		// Determines life points based on type of enemy.
		if (type.compareTo("knight") == 0) {
			this.life.set(2);
		} else {
			this.life.set(1);
		}
		
		this.originalLife = life.get();
		
		// Add required observers.
		// Observer for whether enemy is alive.
		alive.addListener(new ChangeListener<Boolean> () {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (alive.getValue() == false) {
					dungeon.forceCheckObjectiveParameters("Kill Enemies");
					dungeon.updateObjectives();
					setTangible(false);
				}
			}
		});
		
		// Observer for how much life points the enemy has left.
		life.addListener(new ChangeListener<Number> () {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() == 0) {
					killEnemy();
				}
				
			}
		});
	}
	
	/**
	 * Attacks the player
	 * @param p The player the enemy is attacking
	 */
	public void attackPlayer(Player p) {
		p.setIsAlive(false);
		p.setVisibility(false);
		System.out.println("End game here.");
	}

	/** 
	 * Template interactEffect function
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (this.isAlive() && entity.getDescription().contentEquals("Player")) {
			Player p = (Player) entity;
			if (!p.isInvincible()) {
				attackPlayer(p);
			} else {
				this.killEnemy();
			}
		} else {
			System.out.println("Enemy is dead!");
		}
	}
	
	/**
	 * Setting the movement behaviour
	 * @param mb
	 */
	public void setMoveBehaviour(MoveBehaviour mb) {
		this.moveBehaviour = mb;
	}
	
	//Using strategy pattern here
	/**
	 * Movement strategy depending on the situation
	 * @param p The player, the enemy is targeting
	 */
	public void move(Player p) {
		if (this.isAlive()) {
			moveBehaviour.perform(p, this, this.getDungeon());
		}
	}
	/**
	 * Getter for whether the enemy is still alive
	 * @return True or false based on whether the enemy is alive
	 */
	public boolean isAlive() {
		return alive.get();
	}
	
	/**
	 * Determines whether enemy is still alive.
	 * @return Whether the enemy is alive or not.
	 */
	public BooleanProperty getAlive() {
		return alive;
	}
	
	/**
	 * Kill this enemy entity
	 */
	public void killEnemy() {
		alive.set(false);
		this.setTangible(false);
		System.out.println("Enemy Eliminated!");
	}
	
	/**
	 * Damages the enemy.
	 */
	public void damageEnemy() {
		int currentLife = life.get();
		life.set(currentLife -  1);
	}
	
	/**
	 * Resets entity specific attributes.
	 */
	@Override
	public void resetOther() {
		alive.setValue(true);
		life.set(originalLife);
	}
	
	/**
	 * Getter for the type of enemy.
	 * @return The type of enemy.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Getter for the amount of life the enemy has left.
	 * @return The amount of life the enemy has left.
	 */
	public IntegerProperty getLife() {
		return life;
	}
}
