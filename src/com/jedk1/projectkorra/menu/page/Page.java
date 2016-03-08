package com.jedk1.projectkorra.menu.page;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Page {

	/**
	 * The name that will be displayed in the Inventory.
	 * @return Inventory Title.
	 */
	public String getName();
	
	/**
	 * Size of the Inventory.
	 * Inventory sizes are in factors of 9.
	 * @return Size of the Inventory.
	 */
	public int getSize();
	
	/**
	 * Items to be displayed in the Inventory.
	 * @return ItemStack array of items.
	 */
	public ItemStack[] getItems();
	
	/**
	 * Run when a player clicks on an Icon in an Inventory.
	 * @param player Player who clicked.
	 * @param item ItemStack that was clicked.
	 * @param position Position in the Inventory.
	 */
	public void execute(Player player, ItemStack item, int position);
	
	public void open(Player player, Object... object);
}
