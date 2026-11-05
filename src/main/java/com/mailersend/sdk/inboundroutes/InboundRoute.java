package com.mailersend.sdk.inboundroutes;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.DateParser;

/**
 * <p>InboundRoute class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class InboundRoute {
	
	@SerializedName("id")
	public String id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("address")
	public String address;
	
	@SerializedName("domain")
	public String domain;
	
	@SerializedName("dns_checked_at")
	private String dnsCheckedAtString;
	
	public Date dnsCheckedAt;
	
	@SerializedName("enabled")
	public boolean enabled;
	
	@SerializedName("filters")
	public Filter[] filters;
	
	@SerializedName("forwards")
	public Forward[] forwards;
	
	@SerializedName("mxValues")
	public MxValues mxValues;

	@SerializedName("priority")
	public int priority;

	@SerializedName("catch_type")
	public String catchType;
	
	@SerializedName("match_type")
	public String matchType;

	@SerializedName("include_attachments")
	public boolean includeAttachments;

	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		dnsCheckedAt = DateParser.parse(dnsCheckedAtString);
	}
}
