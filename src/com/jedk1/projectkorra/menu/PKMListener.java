package com.jedk1.projectkorra.menu;

import com.jedk1.projectkorra.menu.page.AbstractPage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
			if (item != null && item.getType() != Material.AIR) {
				AbstractPage.getPage(title).execute(player, item, position);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent event) {
		String title = event.getInventory().getName();
		if (AbstractPage.isPage(title)) {
			final Player player = (Player) event.getPlayer();
			new BukkitRunnable() {
				public void run() {
					if (!player.isOnline() || !AbstractPage.isPage(player.getOpenInventory().getTitle())) {
						AbstractPage.clearPreviousPage(player);
					}
				}
			}.runTaskLater(plugin, 5);
		}
	}
}
