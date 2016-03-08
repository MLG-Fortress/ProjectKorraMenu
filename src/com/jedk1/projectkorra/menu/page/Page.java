package com.jedk1.projectkorra.menu.page;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Page {

	public String getName();
	
	public int getSize();
	
	public ItemStack[] getItems();
	
	public void execute(Player player, ItemStack item, int position);
	
	public void open(Player player, Object... object);
}
