package com.jedk1.projectkorra.menu.command;

import com.jedk1.projectkorra.menu.page.ElementsPage;
import com.projectkorra.projectkorra.command.PKCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
	
	public static void openMainMenu(Player player) {
		new ElementsPage().open(player);
	}
}
