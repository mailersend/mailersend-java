package com.mailersend.sdk.inboundroutes;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

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

	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		if (dnsCheckedAtString != null && !dnsCheckedAtString.isBlank()) {
			
	        TemporalAccessor ta;
	        Instant instant;
	        
            ta = DateTimeFormatter.ISO_INSTANT.parse(dnsCheckedAtString);
            instant = Instant.from(ta);
            dnsCheckedAt = Date.from(instant);
		}
	}
}
