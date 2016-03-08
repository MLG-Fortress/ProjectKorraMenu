package com.jedk1.projectkorra.menu.command;

import com.jedk1.projectkorra.menu.InventoryBuilder;
import com.jedk1.projectkorra.menu.page.ElementsPage;
import com.jedk1.projectkorra.menu.page.Page;
import com.projectkorra.projectkorra.command.PKCommand;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuCommand extends PKCommand {

	public MenuCommand() {
		super("menu", "/bending menu", "This command opens up the Bending Menu.", new String[] { "menu" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isPlayer(sender) || !correctLength(sender, args.size(), 0, 1) && sender.hasPermission("bending.command.menu")) {
			return;
		}
		openMainMenu((Player) sender);
	}
	
	public static void openMainMenu(final Player player) {
		final Page page = new ElementsPage();
		InventoryBuilder gui = new InventoryBuilder(page.getName(), page.getSize());
		int pos = 0;
		for (ItemStack i : page.getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		gui.setOption((page.getSize() - 1), new ItemStack(Material.REDSTONE), ChatColor.WHITE + "[X] Close", ChatColor.GRAY + "Click here to exit the menu.");
		gui.open(player);
	}
}
