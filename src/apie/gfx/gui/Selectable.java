package apie.gfx.gui;

public abstract class Selectable extends GUIElement {
	
	private boolean selected = false;
	
	private GUIEvent selectEvent;
	private GUIEvent unselectEvent;
	
	/**
	 * 
	 * Performs a select event on this selectable GUI element.
	 * 
	 * @param selected - when or not to select this GUI element
	 * @return this selectable GUI element instance
	 */
	public Selectable select(boolean selected) {
		this.selected = selected;
		
		if(selected) {
			onSelect(true);
			if(selectEvent != null)
				selectEvent.fire();
		} else if(!selected) {
			onSelect(false);
			if(unselectEvent != null)
				unselectEvent.fire();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Sets an action to perform when a select event is performed on this selectable GUI element.
	 * 
	 * @param e - the action to perform
	 * @return this selectable GUI element instance
	 */
	public final Selectable setSelectEvent(GUIEvent e) {
		selectEvent = e;
		
		return this;
	}
	
	/**
	 * 
	 * Called when a select event is fired.
	 * 
	 * @param selected - true if this selectable GUI element was selected
	 */
	public abstract void onSelect(boolean selected);
	
	/**
	 * 
	 * Returns whether or not this selectable GUI element is selected.
	 * 
	 * @return this selectable GUI element instance
	 */
	public boolean isSelected() {
		return selected;
	}
}