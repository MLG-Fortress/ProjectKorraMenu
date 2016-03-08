package com.jedk1.projectkorra.menu.page;

import com.jedk1.projectkorra.menu.InventoryBuilder;
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
		super(element.getName() + " Abilities");
		this.pageid = pageid;
		this.element = element;
		return;
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
				String[] parts = ability.getDescription().split("\n");
				for (String desc : parts) {
					String wrap = WordUtils.wrap(desc, 40, "::", false);
					if (wrap != null) {
						for (String s : wrap.split("::")) {
							lore.add(ability.getElement().getColor() + s); 
						}
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
	public void execute(Player player, ItemStack item, int position) {
		Page pageclone = new AbilityPage(element);
		if (position == (pageclone.getSize() - 9)) {
			new ElementsPage().open(player);
			return;
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
			open(player, element, pageid);
		} else {
			openBindPage(player, item);
		}
	}

	public void openBindPage(Player player, ItemStack item) {
		CoreAbility ability = CoreAbility.getAbility(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (!bPlayer.hasElement(ability.getElement())) {
			player.sendMessage(ability.getElement().getColor() + "You can't bind that ability, "
					+ "you aren't a" + (ChooseCommand.isVowel(element.getName().charAt(0)) ? "n " : " ") + element.getName() + element.getType().getBender() + ".");
			return;
		}
		if (!bPlayer.canBind(ability)) {
			player.sendMessage(ability.getElement().getColor() + "You can't bind that ability.");
			return;
		}
		setPreviousPage(player);
		new BindPage().open(player, ability);
	}

	@Override
	public void open(Player player, Object... object) {
		if (object.length == 2) {
			this.element = (Element) object[0];
			this.pageid = (int) object[1];
		}
		InventoryBuilder gui = new InventoryBuilder(getName(), getSize());
		int pos = 0;
		for (ItemStack i : getItems()) {
			gui.setOption(pos++, i, i.getItemMeta().getDisplayName(), i.getItemMeta().getLore().toArray(new String[i.getItemMeta().getLore().size()]));
		}
		gui.setOption(getSize()-9, new ItemStack(Material.ARROW, 1), ChatColor.WHITE + "[<] Back", ChatColor.GRAY + "Return to the previous menu.");
		gui.setOption(getSize()-2, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[«] Previous Page", ChatColor.GRAY + "Jump to the previous page.");
		gui.setOption(getSize()-1, new ItemStack(Material.PAPER, 1), ChatColor.WHITE + "[»] Next Page", ChatColor.GRAY + "Jump to the next page.");
		gui.open(player);
	}

	public Element getElement() {
		return element;
	}

	public int getPageId() {
		return pageid;
	}
}
