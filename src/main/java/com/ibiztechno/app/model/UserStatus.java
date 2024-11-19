package com.ibiztechno.app.model;

import javax.validation.constraints.NotEmpty;

public class UserStatus {

	@NotEmpty
	private String accountId;
	@NotEmpty
	private String userId;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
