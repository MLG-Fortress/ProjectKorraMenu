package com.jedk1.projectkorra.menu.page;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractPage implements Page {

	private static final ConcurrentHashMap<String, Page> PAGES = new ConcurrentHashMap<>();
	
	public AbstractPage() {
		if (isPage(this.getName())) return;
		PAGES.put(this.getName(), this);
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
}
