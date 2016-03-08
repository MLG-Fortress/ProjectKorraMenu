package com.jedk1.projectkorra.menu;

import com.jedk1.projectkorra.menu.page.AbstractPage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PKMListener implements Listener {
	
	ProjectKorraMenu plugin;

	public PKMListener(ProjectKorraMenu plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClick(InventoryClickEvent event) {
		String title = event.getInventory().getName();
		if (AbstractPage.isPage(title)) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCurrentItem();
			int position = event.getRawSlot();
			AbstractPage.getPage(title).execute(player, item, position);
		}
	}
}
