package com.ibiztechno.app.provider.home;

public class Menu {
	int parentId;
	int id;
	String webUrl;
	String iconCode;
	String menuName;
	String parentType;
	String funcType;
	String funcDesc;
	String webGroup;
	String menuList;

	public Menu() {
		super();
	}

	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	public String getWebGroup() {
		return webGroup;
	}

	public void setWebGroup(String webGroup) {
		this.webGroup = webGroup;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getIconCode() {
		return iconCode;
	}

	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
}
