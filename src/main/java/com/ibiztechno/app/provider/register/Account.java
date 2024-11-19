package com.ibiztechno.app.provider.register;

import javax.validation.constraints.NotEmpty;

public class Account {
    String sidcard;
    String FName;
    String LName;
    String Email;
    String UserID;
    String Password;
    String RemoteAddr;
    int retval;
    String FullName;
    
    public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getSidcard() {
        return sidcard;
    }

    public void setSidcard(String sidcard) {
        this.sidcard = sidcard;
    }

    public String getFName() {
	return FName;
    }

    public void setFName(String fName) {
	FName = fName;
    }

    public String getLName() {
	return LName;
    }

    public void setLName(String lName) {
	LName = lName;
    }

    public String getEmail() {
	return Email;
    }

    public void setEmail(String email) {
	Email = email;
    }

    public String getUserID() {
	return UserID;
    }

    public void setUserID(String userID) {
	UserID = userID;
    }

    public String getPassword() {
	return Password;
    }

    public void setPassword(String password) {
	Password = password;
    }

    public String getRemoteAddr() {
	return RemoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
	RemoteAddr = remoteAddr;
    }

    public int getRetval() {
	return retval;
    }

    public void setRetval(int retval) {
	this.retval = retval;
    }

}
