package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>InboundRouteBuilderBody class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class InboundRouteBuilderBody {

	@SerializedName("domain_id")
	public String domainId;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("domain_enabled")
	public boolean domainEnabled;
	
	@SerializedName("inbound_domain")
	public String inboundDomain;
	
	@SerializedName("inbound_address")
	public String inboundAddress;
	
	@SerializedName("inbound_subdomain")
	public String inboundSubdomain;
	
	@SerializedName("inbound_priority")
	public int inboundPriority;

	@SerializedName("catch_filter")
	public CatchFilter catchFilter;

	/**
	 * Optional. Must be one of: {@code all}, {@code one}.
	 * Required when {@code catch_filter.type} is {@code catch_recipient}.
	 */
	@SerializedName("catch_type")
	public String catchType;

	@SerializedName("match_filter")
	public MatchFilter matchFilter;

	/**
	 * Optional. Must be one of: {@code all}, {@code one}.
	 * Required when {@code match_filter.type} is {@code match_sender}, {@code match_domain},
	 * or {@code match_header}.
	 */
	@SerializedName("match_type")
	public String matchType;

	@SerializedName("forwards")
	public Forward[] forwards;

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		domainId = null;
		name = null;
		domainEnabled = false;
		inboundDomain = null;
		inboundAddress = null;
		inboundSubdomain = null;
		inboundPriority = 0;
		catchFilter = null;
		catchType = null;
		matchFilter = null;
		matchType = null;
		forwards = null;
	}
}
