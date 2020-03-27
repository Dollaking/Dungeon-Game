package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DungeonTest extends Application {
	
	
	public static void main(String args[]) throws IOException {
		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("test.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	}
	
	/**
	 * Tests player movement through the maze map. Tests all movements and
	 * requires player to reach the exit in the maze.
	 * @throws IOException
	 */
	@Test
	void testPlayerMovement() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("maze.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		// Following performs required movements to complete dungeon.
		for (int i = 0; i < 2; i++) p.moveDown();
		
		for (int i = 0; i < 5; i++) p.moveRight();
		
		for (int i = 0; i < 2; i++) { p.moveDown(); p.moveRight(); }

		for (int i = 0; i < 6; i++) p.moveDown();

		for (int i = 0; i < 2; i++) p.moveRight();

		for (int i = 0; i < 5; i++) p.moveDown();
		
		for (int i = 0; i < 4; i++) p.moveRight();
		
		for (int i = 0; i < 10; i++) p.moveUp();
		
		for (int i = 0; i < 5; i++) p.moveRight();
		
		for (int i = 0; i < 10; i++) p.moveDown();
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	/**
	 * Tests player movement and interaction between player, boulder and floor
	 * switch. Requries player to place all boulders on floor switches to 
	 * complete the objective.
	 * @throws IOException
	 */
	@Test
	void testBoulderObjective() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("boulders.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		// Following performs required movements to complete dungeon.
		p.moveRight();
		p.moveUp();
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		for (int i = 0; i < 4; i++) p.moveDown();

		p.moveLeft();
		p.moveDown();
		p.moveRight();
		
		for (int i = 0; i < 2; i++) p.moveLeft();
		
		p.moveDown();
		
		for (int i = 0; i < 2; i++) p.moveLeft();

		for (int i = 0; i < 2; i++) p.moveUp();
		
		p.moveDown();
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		p.moveUp();
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		p.moveDown();
		p.moveLeft();
		p.moveUp();
		p.moveRight();
		
		for (int i = 0; i < 3; i++) p.moveUp();
		
		for (int i = 0; i < 3; i++) p.moveLeft();
		
		p.moveRight();
		p.moveDown();
		p.moveRight();
		p.moveDown();
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	/**
	 * Tests the boulder objective by moving it across in a straight line to
	 * the switch.
	 * @throws IOException
	 */
	@Test
	void testBoulderStraightObjective() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightBoulders.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		for (int i = 0; i < 5; i++) p.moveRight();
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	/**
	 * Tests the player walking into a portal then having its x and y coordinate
	 * being changed to the required coordinates infront of the other portal.
	 * @throws IOException
	 */
	@Test
	void testPortalStraight() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightPortal.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Checks player position.
		assertTrue(p.getX() == 1);
		assertTrue(p.getY() == 1);
		
		// Enters portal.
		p.moveRight();
		
		// Checks player position since portal doesn't have a matching portal.
		assertTrue(p.getX() == 2);
		assertTrue(p.getY() == 1);
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		// Checks player position after entering position.
		assertTrue(p.getX() == 8);
		assertTrue(p.getY() == 1);
	}
	
	/**
	 * Tests player collecting all treasure then getting to an exit.
	 * @throws IOException
	 */
	@Test
	void testTreasureStraight() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightTreasure.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
				
		// Checks if number of treasure player has collected is 0.
		assertTrue(p.getTreasure() == 0);
		
		p.moveRight();
		
		// Checks if number of treasure player has collected is 1.
		assertTrue(p.getTreasure() == 1);
		
		for (int i = 0; i < 5; i++) p.moveRight();
		
		// Checks if number of treasure player has collected is 2.
		assertTrue(p.getTreasure() == 2);
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		p.moveRight();
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	/**
	 * Test is player can pick up and drop different keys. Also tests
	 * whether player can open door with keys of different ID's.
	 * @throws IOException
	 */
	@Test
	void testDoorKey() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightDoorKey.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Check if player does not have key.
		assertFalse(p.hasKey());
		
		p.moveRight();
		
		// Check if player has key.
		assertTrue(p.hasKey());
		
		for (int i = 0; i < 5; i++) p.moveRight();
		
		// Gets door and checks if it is not open.
		Door door = (Door) dungeon.getSpecificEntity("Door");
		assertFalse(door.equals(null));
		assertFalse(door.isOpen());
		
		for (int i = 0; i < 3; i++) p.moveLeft();
		p.dropKey();
		
		// Check if player doesn't have key.
		assertFalse(p.hasKey());
		
		p.moveRight();
		
		// Check if player has key.
		assertTrue(p.hasKey());
		
		for (int i = 0; i < 3; i++) p.moveRight();
		
		// Check if door is open and player no longer holds key.
		assertTrue(door.isOpen());
		assertFalse(p.hasKey());
	}
	
	/**
	 * Tests player picking up sword and attacking enemy. Tests if player
	 * can kill enemy with a swod. Also tests sword durability and the
	 * objective Kill Enemies. Also tests the composite objective of type 
	 * AND.
	 * @throws IOException
	 */
	@Test
	void testEnemySword() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightEnemySword.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		AndCompositeObjective objectives = (AndCompositeObjective) dungeon.getObjective();
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is not complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		// Check if player doesn't have sword.
		assertFalse(p.hasSword());
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		// Check if player has sword.
		assertTrue(p.hasSword());
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		
		p.moveRight();
		p.attackEnemy();
		
		// Check if enemy is not alive.
		assertFalse(enemy.isAlive());
		
		// Check if player has sword.
		assertTrue(p.hasSword());
		
		for (int i = 0; i < 5; i++) p.attackEnemy();
		
		// Check if player doesn't have sword.
		assertFalse(p.hasSword());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			}
		}
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		for (int i = 0; i < 4; i++) p.moveRight();
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is complete.
				assertTrue(obj.getStatusBoolean());
			}
		}
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	/**
	 * Tests player picking up potion and moving toward enemy. Tests if 
	 * player can kill enemy with a contact. Also tests the objective
	 * Kill Enemies. Also tests the composite objective of type AND.
	 * @throws IOException
	 */
	@Test
	void testPotionEnemy() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightEnemyPotion.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		AndCompositeObjective objectives = (AndCompositeObjective) dungeon.getObjective();
		
		// Check if player does not have potion and is not invincible.
		assertFalse(p.hasPotion());
		assertFalse(p.isInvincible());
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		// Check if player does have potion and is invincible.
		assertTrue(p.hasPotion());
		assertTrue(p.isInvincible());
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is not complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		// Check if enemy is not alive.
		assertFalse(enemy.isAlive());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			}
		}
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Collect Treasure") == 0) {
				// Check if Collect Treasure objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		p.moveRight();
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	@Test
	void testPotionTimer() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightEnemyPotion.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		AndCompositeObjective objectives = (AndCompositeObjective) dungeon.getObjective();
				
		// Check if player does not have potion and is not invincible.
		assertFalse(p.hasPotion());
		assertFalse(p.isInvincible());
				
		for (int i = 0; i < 2; i++) p.moveRight();
				
		// Check if player does have potion and is invincible.
		assertTrue(p.hasPotion());
		assertTrue(p.isInvincible());
		
		// Checks if player is not invincible after a period of time
		// after getting potion.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				assertFalse(p.hasPotion());
				assertFalse(p.isInvincible());
			}
			
		}, 10001);
	}
	
	/**
	 * Test whether enemy moves towards player and if it will kill player
	 * upon contact.
	 * @throws IOException
	 */
	@Test
	void testEnemyMoveTowards() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("advanced.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		assertTrue(enemy.getX() == 3);
		assertTrue(enemy.getY() == 5);
		
		for (int i = 0; i < 5; i++) dungeon.fullTick();
		
		// Check if enemy move towards player.
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 2);
		
		dungeon.fullTick();
		
		// Check if player is dead.
		assertFalse(p.isAlive());
	}
	
	/**
	 * Tests when enemy will move towards player before player picks up potion.
	 * Once player picks potion, enemy moves away from player. Tests once potion
	 * runs out enemy moves towards player.
	 * @throws IOException
	 */
	@Test
	void testEnemyRunAway() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("advanced.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		
		for (int i = 0; i < 10; i++) { p.moveRight(); dungeon.fullTick(); }
		for (int i = 0; i < 10; i++) { p.moveDown(); dungeon.fullTick(); }
		
		// Check if player does have potion and is invincible.
		assertTrue(p.hasPotion());
		assertTrue(p.isInvincible());
		
		// Check player position.
		assertTrue(p.getX() == 11);
		assertTrue(p.getY() == 11);
		
		// Check enemy position
		assertTrue(enemy.getX() == 5);
		assertTrue(enemy.getY() == 5);
		
		for (int i = 0; i < 3; i++) dungeon.fullTick();
		
		// Check enemy moved further away after player picked potion.
		assertTrue(enemy.getX() == 2);
		assertTrue(enemy.getY() == 5);
		
		// Check player position same.
		assertTrue(p.getX() == 11);
		assertTrue(p.getY() == 11);
		
		// Checks if player is not invincible after a period of time
		// after getting potion.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				assertFalse(p.hasPotion());
				assertFalse(p.isInvincible());
				
				for (int i = 0; i < 3; i++) dungeon.fullTick();
				
				// Check enemy position is closer.
				assertTrue(enemy.getX() == 5);
				assertTrue(enemy.getY() == 5);
			}
					
		}, 10001);
	}
	
	/**
	 * Tests that player cannot pick up more than two swords.
	 * @throws IOException
	 */
	@Test
	void testMultipleSword() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightTwoSwords.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Check if player does not have sword.
		assertFalse(p.hasSword());
		
		for (int i = 0; i < 2; i++) p.moveRight();
				
		// Check if player does have sword.
		assertTrue(p.hasSword());
		
		p.attackEnemy();
		
		// Check durability is now 4.
		assertTrue(p.getSword().getDurability() == 4);
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		// Check if player does have sword.
		assertTrue(p.hasSword());
		
		// Check durability is still 4.
		assertTrue(p.getSword().getDurability() == 4);
	}
	
	/**
	 * Tests that all objectives within an AND objective need to be completed
	 * for game to finish.
	 * @throws IOException
	 */
	@Test 
	void testAndObjective() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightEnemyPotionTreasure.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		AndCompositeObjective objectives = (AndCompositeObjective) dungeon.getObjective();
		
		// Gets exit and check if it closed.
		Exit exit = (Exit) dungeon.getSpecificEntity("Exit");
		assertFalse(exit.getVisibility().get());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is not complete.
				assertFalse(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Collect Treasure") == 0) {
				// Check if Collect Treasure objective is not complete.
				assertFalse(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is not complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		// Check if exit is not visible.
		assertFalse(exit.getVisibility().get());
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		
		for (int i = 0; i < 4; i++) p.moveRight();
		
		// Check if enemy is dead.
		assertFalse(enemy.isAlive());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Collect Treasure") == 0) {
				// Check if Collect Treasure objective is not complete.
				assertFalse(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is not complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		// Check if exit is not visible.
		assertFalse(exit.getVisibility().get());
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Collect Treasure") == 0) {
				// Check if Collect Treasure objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is not complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		// Check if exit is visible.
		assertTrue(exit.getVisibility().get());
		
		for (int i = 0; i < 2; i++) p.moveRight();
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Collect Treasure") == 0) {
				// Check if Collect Treasure objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is complete.
				assertTrue(obj.getStatusBoolean());
			}
		}
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
	}
	
	/**
	 * Tests if only one objective needs to be completed for an OR objective.
	 * @throws IOException
	 */
	@Test 
	void testOrObjective() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightEnemyPotionTreasureOR.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		OrCompositeObjective objectives = (OrCompositeObjective) dungeon.getObjective();
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		
		for (int i = 0; i < 4; i++) p.moveRight();
		
		assertFalse(enemy.isAlive());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Collect Treasure") == 0) {
				// Check if Collect Treasure objective is not complete.
				assertFalse(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("Get Exit") == 0) {
				// Check if Get Exit objective is not complete.
				assertFalse(obj.getStatusBoolean());
			}
		}
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
		
	}
	
	/**
	 * Test an AND objective with an OR objective inside.
	 * @throws IOException
	 */
	@Test
	void testANDORObjective() throws IOException {
		DungeonTestLoader dungeonLoader = new DungeonTestLoader("straightEnemyPotionTreasureANDOR.json");
		DungeonController controller = dungeonLoader.loadController();
		Dungeon dungeon = dungeonLoader.getDungeon();
	        
		Player p = dungeon.getPlayer();
		
		// Ensures dungeon objective hasn't been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		AndCompositeObjective objectives = (AndCompositeObjective) dungeon.getObjective();
		
		// Ensures dungeon objective has not been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
		
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is not complete.
				assertFalse(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("OR") == 0) {
				// Check if OR objectives is not complete.
				assertFalse(obj.getStatusBoolean());
				OrCompositeObjective objOR = (OrCompositeObjective) obj;
				for (Objective obj2 : objOR.getObjectives()) {
					if (obj2.getDescription().compareTo("Collect Treasure") == 0) {
						// Check if Collect Treasure objective is not complete.
						assertFalse(obj2.getStatusBoolean());
					} else if (obj2.getDescription().compareTo("Get Exit") == 0) {
						// Check if Get Exit objective is not complete.
						assertFalse(obj2.getStatusBoolean());
					}
				}
			} 
		}
		
		// Gets Enemy and checks if it is alive.
		Enemy enemy = (Enemy) dungeon.getSpecificEntity("Enemy");
		assertFalse(enemy.equals(null));
		assertTrue(enemy.isAlive());
		
		for (int i = 0; i < 4; i++) p.moveRight();
		
		// Check if enemy is dead.
		assertFalse(enemy.isAlive());
		
		// Ensures dungeon objective has not been completed.
		assertFalse(dungeon.getObjective().getStatusBoolean());
				
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("OR") == 0) {
				// Check if OR objectives is not complete.
				assertFalse(obj.getStatusBoolean());
				OrCompositeObjective objOR = (OrCompositeObjective) obj;
				for (Objective obj2 : objOR.getObjectives()) {
					if (obj2.getDescription().compareTo("Collect Treasure") == 0) {
						// Check if Collect Treasure objective is not complete.
						assertFalse(obj2.getStatusBoolean());
					} else if (obj2.getDescription().compareTo("Get Exit") == 0) {
						// Check if Get Exit objective is not complete.
						assertFalse(obj2.getStatusBoolean());
					}
				}
			} 
		}
		
		p.moveRight();
		
		assertTrue(p.getTreasure() == 1);
		
		// Ensures dungeon objective has been completed.
		assertTrue(dungeon.getObjective().getStatusBoolean());
						
		for (Objective obj : objectives.getObjectives()) {
			if (obj.getDescription().compareTo("Kill Enemy") == 0) {
				// Check if Kill Enemy objective is complete.
				assertTrue(obj.getStatusBoolean());
			} else if (obj.getDescription().compareTo("OR") == 0) {
				// Check if OR objectives is complete.
				assertTrue(obj.getStatusBoolean());
				OrCompositeObjective objOR = (OrCompositeObjective) obj;
				for (Objective obj2 : objOR.getObjectives()) {
					if (obj2.getDescription().compareTo("Collect Treasure") == 0) {
						// Check if Collect Treasure objective is complete.
						assertTrue(obj2.getStatusBoolean());
					} else if (obj2.getDescription().compareTo("Get Exit") == 0) {
						// Check if Get Exit objective is not complete.
						assertFalse(obj2.getStatusBoolean());
					}
				}
			} 
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
