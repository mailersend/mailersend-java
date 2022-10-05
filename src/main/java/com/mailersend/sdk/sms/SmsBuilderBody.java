package com.mailersend.sdk.sms;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SmsBuilderBody {

	@SerializedName("from")
	public String from;
	
	@SerializedName("to")
	public ArrayList<String> to = new ArrayList<String>();
	
	@SerializedName("text")
	public String text;
	
	@SerializedName("personalization")
	public ArrayList<SmsPersonalization> personalization = new ArrayList<SmsPersonalization>();
}
