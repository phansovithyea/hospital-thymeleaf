package com.ibiztechno.app.provider.home;

import java.util.ArrayList;
import java.util.List;

public class MenuGroup {
	Menu menu;
	List<Menu> menus;
	

	public MenuGroup() {
		menu = new Menu();
		menus = new ArrayList<Menu>();
	}

	public List<MenuGroup> createMenuGroups(List<Menu> _menus) {
		List<MenuGroup> _menuGroups = new ArrayList<MenuGroup>();
		MenuGroup menuG = new MenuGroup();

		for (Menu m : _menus) {
			if (m.getMenuList().equals("T")) {
				if (menuG != null) {
					_menuGroups.add(menuG);
				}

				menuG = new MenuGroup();
				menuG.setMenu(m);
			} else {
				menuG.getMenus().add(m);
			}
		}
		_menuGroups.add(menuG);
		return _menuGroups;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
