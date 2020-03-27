package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private int originalX, originalY;
    private Dungeon dungeon;
    private Boolean tangible, originalTangible, originalVisibility;
    private String description;
    private BooleanProperty visibility;
    
    /**
     * For empty Entity, mainly for play. E.g. For when there is no key in player inventory
     */
    public Entity() {
    	
    }
    
    /**
     * Create an entity positioned in square (x,y)
     * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
     * @param desc The description of the entity
     * @param dungeon The dungeon in which entity exists.
     */
    public Entity(int x, int y, String desc, Dungeon dungeon, boolean visibility, boolean tangible) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        originalX = getX();
        originalY = getY();
        originalVisibility = visibility;
        originalTangible = tangible;
        this.setTangible(tangible);
        this.description = desc;
        this.dungeon = dungeon;
        this.visibility = new SimpleBooleanProperty(visibility);
    }
    /**
     * Get function for InterProperty of x coords
     * @return InterProperty for x coords
     */
    public IntegerProperty x() {
        return x;
    }
    /**
     * Get function for IntergerProperty of y coords
     * @return IntegerProperty for y coords
     */
    public IntegerProperty y() {
        return y;
    }
    /**
     * Get function for y coords
     * @return y coords
     */
    public int getY() {
        return y().get();
    }
    /**
     * Get function for x coords
     * @return x coords
     */
    public int getX() {
        return x().get();
    }
    /**
     * Get description of the entity
     * @return Description in string type
     */
    public String getDescription() {
    	return description;
    }

    /**
     * Setting x coords
     * @param x x int coords
     */
    public void setX(int x) {
    	this.x.setValue(x);
    }
    /**
     * Setting y coords
     * @param y y int coords
     */
    public void setY(int y) {
    	this.y.setValue(y);
    }
    /**
     * Getter function for dungeon
     * @return Dungeon
     */
    public Dungeon getDungeon() {
    	return this.dungeon;
    }
    /**
     * Setter function for dungeon
     * @param dungeon The dungeon your character is currently in
     */
    public void setDungeon(Dungeon dungeon) {
    	this.dungeon = dungeon;
    }
    /**
     * Setter function for Tangibility
     * @param b
     */
    public void setTangible(Boolean b) {
    	this.tangible = b;
    }
    /**
     * Check if the current entity is Tangible
     * @return boolean based on whether it is tangible
     */
    public boolean isTangible() {
    	return this.tangible;
    }
    /**
     * It gets and activates the effect from the entity that the movable object as obtain
     * @param entity
     */
    public void interactEffect(Entity entity) {
    }
    /**
     * Set the description of the entity
     * @param desc The description as a string
     */
    public void setDescription(String desc) {
    	this.description = desc;
    }
    
    /**
     * Returns the visibility of the entity.
     * @return The visibility of the entity.
     */
    public BooleanProperty getVisibility() {
    	return visibility;
    }
    
    /**
     * Sets the visibility to true or false.
     * @param status A boolean containing true or false.
     */
    public void setVisibility(boolean status) {
    	visibility.set(status);
    }
    
    /**
     * Resets the entity properties.
     */
    public void resetEntity() {
    	this.setX(this.originalX);
    	this.setY(this.originalY);
    	this.setTangible(originalTangible);
    	this.setVisibility(originalVisibility);
    	resetOther();
    }
    
    /**
     * Resets any unique attributes.
     */
    public void resetOther() {
    	
    }


}
