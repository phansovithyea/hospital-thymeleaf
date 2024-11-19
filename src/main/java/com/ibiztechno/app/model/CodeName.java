package com.ibiztechno.app.model;

public class CodeName {
	String Code;
	String Name;

	public CodeName() {
		super();
	}

	public CodeName(String code, String name) {
		Code = code;
		Name = name;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
