package com.jedk1.projectkorra.menu;

import com.jedk1.projectkorra.menu.command.MenuCommand;
import com.jedk1.projectkorra.menu.page.AbilityPage;
import com.jedk1.projectkorra.menu.page.BindPage;
import com.jedk1.projectkorra.menu.page.ElementsPage;
import com.projectkorra.projectkorra.Element;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ProjectKorraMenu extends JavaPlugin {

	public static ProjectKorraMenu plugin;
	public static Logger log;

	@Override
	public void onEnable() {
		plugin = this;
		ProjectKorraMenu.log = this.getLogger();
		new MenuCommand();
		for (Element element : Element.getAllElements()) {
			new AbilityPage(element);
		}
		new BindPage();
		new ElementsPage();
		getServer().getPluginManager().registerEvents(new PKMListener(this), this);
	}

	@Override
	public void onDisable() {
	}
}
