package com.jedk1.projectkorra.menu.page;

import com.jedk1.projectkorra.menu.InventoryBuilder;
import com.jedk1.projectkorra.menu.command.MenuCommand;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.command.ChooseCommand;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AbilityPage extends AbstractPage {

	private Element element;
	private int pageid;

	public AbilityPage(Element element) {
		this(element, 0);
		return;
	}

	public AbilityPage(Element element, int pageid) {
		this.pageid = pageid;
		this.element = element;
		return;
	}

	@Override
	public String getName() {
		return element.getName() + " Abilities";
	}

	@Override
	public int getSize() {
		return 36;
	}

	@Override
	public ItemStack[] getItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (CoreAbility ability : CoreAbility.getAbilitiesByElement(element)) {
			ItemStack i = new ItemStack(Material.EMPTY_MAP, 1);
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(ability.getElement().getColor() + ability.getName());
			List<String> lore = new ArrayList<String>();
			if (ability.isHiddenAbility()) {
				continue;
			}
			if (ability instanceof AddonAbility) {
				lore.add(ChatColor.YELLOW + "* Addon Ability *");
			}
			if (ability.getDescription() != null) {
				String desc = ability.getDescription().replaceAll("\n", "::");
				String wrap = WordUtils.wrap(desc, 40, "::", false);
				if (wrap != null) {
					for (String s : wrap.split("::")) {
						lore.add(ability.getElement().getColor() + s); 
					}
				}
			} else {
				lore.add(ChatColor.WHITE + "This ability has no description.");
			}
			meta.setLore(lore);
			i.setItemMeta(meta);
			items.add(i);
		}
		if (items.size() > 27) {
			List<ItemStack> newitems = new ArrayList<ItemStack>();
			for (int i = (pageid*27); i < (27 + (pageid*27)); i++) {
				if (i < items.size()) {
					newitems.add(items.get(i));
				}
			}
			return newitems.toArray(new ItemStack[newitems.size()]);
		} else {
			return items.toArray(new ItemStack[items.size()]);
		}
	}

	@Override
	public boolean execute(Player player, ItemStack item, int position) {
		Page pageclone = new AbilityPage(element);
		if (position == (pageclone.getSize() - 9)) {
			MenuCommand.openMainMenu(player);
			return true;
		}
		if (position == 34 || position == 35) {
			if (position == (pageclone.getSize() - 1)) {
				pageid++;
				if (pageid > (Math.round((CoreAbility.getAbilitiesByElement(element).size()/27) + 0.5) - 1)) {
					pageid--;
				}
			}
			if (position == (pageclone.getSize() - 2)) {
				pageid--;
				if (pageid < 0) {
					pageid = 0;
				}
			}
			Page page = new AbilityPage(element, pageid);
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
		} else {
			openBindPage(player, item);
			return true;
		}
	}
	
	public void openBindPage(Player player, ItemStack item) {
		CoreAbility ability = CoreAbility.getAbility(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
		if (!BendingPlayer.getBendingPlayer(player).hasElement(ability.getElement())) {
			player.sendMessage(ability.getElement().getColor() + "You can't bind that ability, "
					+ "you aren't a" + (ChooseCommand.isVowel(element.getName().charAt(0)) ? "n " : " ") + element.getName() + element.getType().getBender() + ".");
			refresh(player);
			return;
		}
		if (!BendingPlayer.getBendingPlayer(player).canBind(ability)) {
			player.sendMessage(ability.getElement().getColor() + "You can't bind that ability.");
			refresh(player);
			return;
		}
		Page page = new BindPage(player, ability);
		InventoryBuilder gui = new InventoryBuilder(page.getName(), page.getSize());
		int pos = 0;
		for (ItemStack i : page.getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		gui.setOption(page.getSize()-9, new ItemStack(Material.ARROW, 1), ChatColor.WHITE + "[<] Back", ChatColor.GRAY + "Return to the previous menu.");
		gui.open(player);
	}

	public void refresh(Player player) {
		Page ability = new ElementsPage();
		ItemStack fake = new ItemStack(Material.STONE, 1);
		ItemMeta meta = fake.getItemMeta();
		meta.setDisplayName(element.getName());
		fake.setItemMeta(meta);
		ability.execute(player, fake, 0);
		return;
	}
}
