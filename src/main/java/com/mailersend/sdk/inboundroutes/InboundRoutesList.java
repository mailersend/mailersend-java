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
}
