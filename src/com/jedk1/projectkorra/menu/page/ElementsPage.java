package com.jedk1.projectkorra.menu.page;

import com.jedk1.projectkorra.menu.InventoryBuilder;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.ability.CoreAbility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ElementsPage extends AbstractPage {

	@Override
	public String getName() {
		return "Bind Your Abilities";
	}
	
	@Override
	public int getSize() {
		return (int) ((Math.round((Element.getAllElements().length/9) + 0.5) * 9) + 9);
	}

	@Override
	public ItemStack[] getItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (Element element : Element.getAllElements()) {
			ItemStack i = new ItemStack(Material.BOOK, 1);
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(element.getColor() + element.getName() + " Abilities");
			List<String> lore = new ArrayList<String>();
			int abilities = 0;
			for (CoreAbility ability : CoreAbility.getAbilitiesByElement(element)) {
				if (!ability.isHiddenAbility()) {
					abilities++;
				}
			}
			lore.add(ChatColor.WHITE + "Abilities: " + abilities);
			for (SubElement subs : Element.getSubElements(element)) {
				lore.add(subs.getColor() + "-- " + subs.getName() + subs.getType().getBending());
			}
			meta.setLore(lore);
			i.setItemMeta(meta);
			items.add(i);
		}
		return items.toArray(new ItemStack[items.size()]);
	}

	@Override
	public boolean execute(Player player, ItemStack item, int position) {
		Page pageclone = new ElementsPage();
		if (position == (pageclone.getSize() - 1)) {
			player.closeInventory();
			return true;
		}
		Element element = Element.fromString(ChatColor.stripColor(item.getItemMeta().getDisplayName().split(" ")[0]));
		Page page = new AbilityPage(element);
		InventoryBuilder gui = new InventoryBuilder(page.getName(), page.getSize());
		int pos = 0;
		for (ItemStack i : page.getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		gui.setOption(page.getSize()-9, new ItemStack(Material.ARROW, 1), ChatColor.WHITE + "[<] Back", ChatColor.GRAY + "Return to the previous menu.");
		gui.setOption(page.getSize()-2, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[«] Previous Page", ChatColor.GRAY + "Jump to the previous page.");
		gui.setOption(page.getSize()-1, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[»] Next Page", ChatColor.GRAY + "Jump to the next page.");
		gui.open(player);
		return true;
	}
}
