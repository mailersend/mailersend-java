package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

public class Statistics {

	@SerializedName("valid")
	public int valid;
	
	@SerializedName("catch_all")
	public int catchAll;
	
	@SerializedName("mailbox_full")
	public int mailboxFull;
	
	@SerializedName("role_based")
	public int roleBased;
	
	@SerializedName("unknown")
	public int unknown;
	
	@SerializedName("syntax_error")
	public int syntaxError;
	
	@SerializedName("typo")
	public int typo;

	@SerializedName("mailbox_not_found")
	public int mailboxNotFound;
	
	@SerializedName("disposable")
	public int disposable;
	
	@SerializedName("mailbox_blocked")
	public int mailboxBlocked;
	
	@SerializedName("failed")
	public int failed;
}

