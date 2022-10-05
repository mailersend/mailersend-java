package com.mailersend.sdk.sms;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class SmsPersonalization {

    @SerializedName("phone_number")
    public String phoneNumber;

    @SerializedName("data")
    public HashMap<String, Object> data = new HashMap<String, Object>();
}
