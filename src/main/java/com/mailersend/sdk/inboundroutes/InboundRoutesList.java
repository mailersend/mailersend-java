package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>InboundRoutesList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class InboundRoutesList extends PaginatedResponse {

	@SerializedName("data")
	public InboundRoute[] routes;
	
	
	/**
	 * Is called to perform any actions after the deserialization of the response
	 * Do not call directly
	 */
	protected void postDeserialize() {
		
		for (InboundRoute route : routes) {
			
			route.postDeserialize();
		}
	}
}
