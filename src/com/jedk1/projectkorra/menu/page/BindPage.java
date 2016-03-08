package com.jedk1.projectkorra.menu.page;

import com.jedk1.projectkorra.menu.InventoryBuilder;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BindPage extends AbstractPage {
	
	public BindPage() {
		super("Bind to which slot?");
	}

	@Override
	public int getSize() {
		return 18;
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@Override
	public void execute(Player player, ItemStack item, int position) {
		Page pageclone = new BindPage();
		CoreAbility ability = null;
		if (position != (pageclone.getSize() - 9)) {
			ability = CoreAbility.getAbility(ChatColor.stripColor(item.getItemMeta().getLore().get(0).split(": ")[1]));
			GeneralMethods.bindAbility(player, ability.getName(), (position + 1));
			BendingPlayer.getBendingPlayer(player).addCooldown("refresh", 1);
		}
		AbilityPage page = (AbilityPage) getPreviousPage(player);
		page.open(player, page.getElement(), page.getPageId());
	}
	
	@Override
	public void open(Player player, Object... object) {
		CoreAbility ability = (CoreAbility) object[0];
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		InventoryBuilder gui = new InventoryBuilder(getName(), getSize());
		int pos = 0;
		
		for (int i = 0; i < 9; i++) {
			ItemStack item2 = new ItemStack(Material.FEATHER, 1);
			ItemMeta meta = item2.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "Slot " + (i + 1));
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Binding Move: " + ability.getElement().getColor() + ability.getName());
			String bound = ChatColor.GRAY + "No ability bound.";
			if (bPlayer.getAbilities().get(i) != null) {
				bound = CoreAbility.getAbility(bPlayer.getAbilities().get(i)).getElement().getColor() + bPlayer.getAbilities().get(i);
			}
			lore.add(ChatColor.WHITE + "Currently Bound: " + bound);
			lore.add(ChatColor.WHITE + "Click here to bound this move to Slot " + (i + 1));
			meta.setLore(lore);
			item2.setItemMeta(meta);
			gui.setOption(pos++, item2, item2.getItemMeta().getDisplayName(), item2.getItemMeta().getLore().toArray(new String[item2.getItemMeta().getLore().size()]));
		}
		gui.setOption(getSize()-9, new ItemStack(Material.ARROW, 1), ChatColor.WHITE + "[<] Back", ChatColor.GRAY + "Return to the previous menu.");
		gui.open(player);
	}
}
