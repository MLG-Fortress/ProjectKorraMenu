package com.jedk1.projectkorra.menu.page;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.SubElement;
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

	private Player player;
	private CoreAbility ability;
	
	public BindPage(Player player, CoreAbility ability) {
		this.player = player;
		this.ability = ability;
	}
	
	public BindPage() {}
	
	@Override
	public String getName() {
		return "Bind to which slot?";
	}

	@Override
	public int getSize() {
		return 18;
	}

	@Override
	public ItemStack[] getItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		BendingPlayer bplayer = BendingPlayer.getBendingPlayer(player);
		for (int i = 0; i < 9; i++) {
			ItemStack item = new ItemStack(Material.FEATHER, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "Slot " + (i + 1));
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Binding Move: " + ability.getElement().getColor() + ability.getName());
			String bound = ChatColor.GRAY + "No ability bound.";
			if (bplayer.getAbilities().get(i) != null) {
				bound = CoreAbility.getAbility(bplayer.getAbilities().get(i)).getElement().getColor() + bplayer.getAbilities().get(i);
			}
			lore.add(ChatColor.WHITE + "Currently Bound: " + bound);
			lore.add(ChatColor.WHITE + "Click here to bound this move to Slot " + (i + 1));
			meta.setLore(lore);
			item.setItemMeta(meta);
			items.add(item);
		}
		return items.toArray(new ItemStack[items.size()]);
	}

	@Override
	public boolean execute(Player player, ItemStack item, int position) {
		Page pageclone = new BindPage(player, null);
		if (position != (pageclone.getSize() - 9)) {
			GeneralMethods.bindAbility(player, ability.getName(), (position + 1));
			BendingPlayer.getBendingPlayer(player).addCooldown("refresh", 1);
		}
		previousPage();
		return true;
	}

	public void previousPage() {
		Page ability = new ElementsPage();
		ItemStack fake = new ItemStack(Material.STONE, 1);
		ItemMeta meta = fake.getItemMeta();
		Element e = this.ability.getElement();
		if (e instanceof SubElement) {
			e = ((SubElement) e).getParentElement();
		}
		meta.setDisplayName(e.getName());
		fake.setItemMeta(meta);
		ability.execute(player, fake, 0);
		return;
	}
	
}
