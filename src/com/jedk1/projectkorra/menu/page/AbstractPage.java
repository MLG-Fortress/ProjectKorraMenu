package com.jedk1.projectkorra.menu.page;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractPage implements Page {

	private static final ConcurrentHashMap<String, Page> PAGES = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<Player, Page> PREVIOUS = new ConcurrentHashMap<>();
	
	private String title;
	
	/**
	 * Creates a new registered Page.
	 * @param title Title of the page. (What is displayed in the Inventory).
	 */
	public AbstractPage(String title) {
		this.title = title;
		if (isPage(title)) return;
		PAGES.put(title, this);
	}
	
	/**
	 * Returns all registered pages.
	 * @return ConcurrentHashMap of Pages.
	 */
	public static ConcurrentHashMap<String, Page> getPages() {
		return PAGES;
	}
	
	/**
	 * Checks if an Inventory is a registered Page.
	 * @param title Title of the Inventory.
	 * @return true if the page is registered.
	 */
	public static boolean isPage(String title) {
		return getPages().containsKey(title);
	}
	
	/**
	 * Returns a page from its title.
	 * @param title Title of the page.
	 * @return Page from title.
	 */
	public static Page getPage(String title) {
		return getPages().get(title);
	}
	
	@Override
	public String getName() {
		return title;
	}
	
	/**
	 * Set the previously viewed inventory page.
	 * Sets the page to what is currently being viewed.
	 * @param player Player viewing the Inventory Page.
	 */
	public void setPreviousPage(Player player) {
		PREVIOUS.put(player, this);
	}
	
	/**
	 * Get the previous page the player was viewing.
	 * @param player Player to check for.
	 * @return Page the player was viewing.
	 */
	public static Page getPreviousPage(Player player) {
		if (!PREVIOUS.containsKey(player)) {
			return null;
		}
		return PREVIOUS.get(player);
	}
	
	/**
	 * Remove the player's previous pages.
	 * @param player Player to remove.
	 */
	public static void clearPreviousPage(Player player) {
		PREVIOUS.remove(player);
	}
	
	public static ConcurrentHashMap<Player, Page> getPreviousPages() {
		return PREVIOUS;
	}
}
