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

	@SerializedName("match_filter")
	public MatchFilter matchFilter;
	
	@SerializedName("catch_filter")
	public CatchFilter catchFilter;
	
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
		matchFilter = null;
		catchFilter = null;
		forwards = null;
	}
}
