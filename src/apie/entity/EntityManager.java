package apie.entity;

import java.util.ArrayList;
import java.util.HashMap;

import apie.gamestate.GameState;

public class EntityManager {
	
	private HashMap<String, Entity> entities;
	private ArrayList<String> entityTrash;
	private HashMap<String, Group> groups;
	private ArrayList<String> groupTrash;
	
	/**
	 * 
	 * Constructs a new game state manager.
	 * 
	 * @param gs - the game state associated with this entity manager
	 */
	public EntityManager() {
		entities = new HashMap<>();
		entityTrash = new ArrayList<>();
		groups = new HashMap<>();
		groupTrash = new ArrayList<>();
	}
	
	/**
	 * 
	 * Updates all entities in this entity manager.
	 * 
	 * @param tick the time passed since the previous frame
	 */
	public void update(float tick) {
		
		for(String e : entityTrash) {
			entities.remove(e);
		}
		
		for(String g : groupTrash) {
			groups.remove(g);
		}
		
		for(Entity e : entities.values()) {
			e.update(tick);
		}
		
		for(Group g : groups.values()) {
			g.update(tick);
		}
	}
	
	/**
	 * 
	 * Draws all entities in this entity manager.
	 * 
	 */
	public void draw() {
		for(Entity e : entities.values()) {
			e.draw();
		}
		
		for(Group g : groups.values()) {
			g.draw();
		}
	}
	
	/**
	 * 
	 * Adds an entity to this entity manager.
	 * 
	 * @param name - the name of the entity
	 * @param entity - the entity to add
	 * @return this entity manager instance
	 */
	public EntityManager addEntity(String name, Entity entity) {
		entities.put(name, entity);
		return this;
	}
	
	/**
	 * 
	 * Removes the specified entities.
	 * 
	 * @param entity - the names of the entities to remove
	 * @return this entity manager instance
	 */
	public EntityManager trashEntities(String... entities) {
		for(String e : entities)
			entityTrash.add(e);
		return this;
	}
	
	/**
	 * 
	 * Removes the specified groups.
	 * 
	 * @param groups - the groups to remove
	 * @return this entity manager instance
	 */
	public EntityManager trashGroups(String... groups) {
		for(String g : groups)
			groupTrash.add(g);
		return this;
	}
	
	/**
	 * 
	 * Returns an entity from this entity manager.
	 * 
	 * @param name - the name of the entity
	 * @return the fetched entity
	 */
	public Entity getEntity(String name) {
		return entities.get(name);
	}
	
	/**
	 * 
	 * Adds new groups to this entity manager.
	 * 
	 * @param names - the name of the groups to add
	 * @return this entity manager instance
	 */
	public EntityManager addGroups(String... names) {
		for(String name : names) {
			groups.put(name, new Group(name));
		}
		
		return this;
	}
	
	/**
	 * 
	 * Returns a list of all entities from the specified group.
	 * 
	 * @param name - the name of the group to get entities from
	 * @return the fetched list
	 */
	public ArrayList<Entity> getEntitiesFromGroup(String name) {
		return groups.get(name).entities;
	}
	
	/**
	 * 
	 * Adds entities to the specified group.
	 * 
	 * @param group - the group to add the entities to
	 * @param entities - the entities to add
	 * @return this entity manager instance
	 */
	public EntityManager addToGroup(String group, Entity... entities) {
		for(Entity e : entities)
			groups.get(group).entities.add(e);
		return this;
	}
	
	/**
	 * 
	 * Currently not used.
	 * 
	 */
	public void destroy() {
//		for(Entity e : entities.values())
//			e.destroy();
		for(Group g : groups.values())
			g.destroy();
		groups.clear();
	}
	
	private class Group {
		final String NAME;
		
		ArrayList<Entity> entities;
		boolean active = true;
		boolean visible = true;
		
		/**
		 * 
		 * Constructs a new group for entity storage.
		 * 
		 * @param name - the name of this group
		 */
		Group(String name) {
			this.NAME = name;
			entities = new ArrayList<>();
		}
		
		/**
		 * 
		 * Updates all entities in this group.
		 * 
		 * @param tick - the time passed since the previous frame
		 */
		void update(float tick) {
			if(active) {
				for(Entity e : entities)
					e.update(tick);
			}
		}
		
		/**
		 * 
		 * Draws all entities contained in this group.
		 * 
		 */
		void draw() {
			if(visible) {
				for(Entity e : entities)
					e.draw();
			}
		}
		
		/**
		 * 
		 * Currently not used.
		 * 
		 */
		void destroy() {
//			for(Entity e : entities)
//				e.destroy();
		}
	}
}