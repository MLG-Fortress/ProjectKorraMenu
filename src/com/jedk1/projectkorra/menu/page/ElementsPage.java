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

	public ElementsPage() {
		super("Bind Your Abilities");
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
	public void execute(Player player, ItemStack item, int position) {
		Page pageclone = new ElementsPage();
		if (position == (pageclone.getSize() - 1)) {
			player.closeInventory();
			return;
		}
		Element element = Element.fromString(ChatColor.stripColor(item.getItemMeta().getDisplayName().split(" ")[0]));
		Page page = new AbilityPage(element);
		InventoryBuilder gui = new InventoryBuilder(page.getName(), page.getSize());
		int pos = 0;
		for (ItemStack i : page.getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Previous Menu: " + player.getOpenInventory().getTitle());
		lore.add(ChatColor.GRAY + "Return to the previous menu.");
		gui.setOption(page.getSize()-9, new ItemStack(Material.ARROW, 1), ChatColor.WHITE + "[<] Back", lore.toArray(new String[lore.size()]));
		gui.setOption(page.getSize()-2, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[«] Previous Page", ChatColor.GRAY + "Jump to the previous page.");
		gui.setOption(page.getSize()-1, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[»] Next Page", ChatColor.GRAY + "Jump to the next page.");
		setPreviousPage(player);
		gui.open(player);
	}

	@Override
	public void open(Player player, Object... object) {
		InventoryBuilder gui = new InventoryBuilder(getName(), getSize());
		int pos = 0;
		for (ItemStack i : getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		gui.setOption((getSize() - 1), new ItemStack(Material.REDSTONE), ChatColor.WHITE + "[X] Close", ChatColor.GRAY + "Click here to exit the menu.");
		gui.open(player);
	}
}
