package com.jedk1.projectkorra.menu.page;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractPage implements Page {

	private static final ConcurrentHashMap<String, Page> PAGES = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<Player, Page> PREVIOUS = new ConcurrentHashMap<>();
	
	private String title;
	
	public AbstractPage(String title) {
		this.title = title;
		if (isPage(title)) return;
		PAGES.put(title, this);
	}
	
	public static ConcurrentHashMap<String, Page> getPages() {
		return PAGES;
	}
	
	public static boolean isPage(String title) {
		return getPages().containsKey(title);
	}
	
	public static Page getPage(String title) {
		return getPages().get(title);
	}
	
	@Override
	public String getName() {
		return title;
	}
	
	public void setPreviousPage(Player player) {
		PREVIOUS.put(player, this);
	}
	
	public static Page getPreviousPage(Player player) {
		if (!PREVIOUS.containsKey(player)) {
			return null;
		}
		return PREVIOUS.get(player);
	}
	
	public static void clearPreviousPage(Player player) {
		PREVIOUS.remove(player);
	}
	
	public static ConcurrentHashMap<Player, Page> getPreviousPages() {
		return PREVIOUS;
	}
}
