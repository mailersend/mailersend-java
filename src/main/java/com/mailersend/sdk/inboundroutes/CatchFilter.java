package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>CatchFilter class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class CatchFilter {

	@SerializedName("type")
	public String type;
	
	@SerializedName("filters")
	public Filter[] filters;
	
	/**
	 * <p>Constructor for CatchFilter.</p>
	 */
	public CatchFilter() {
		// intentionally left blank
	}
	
	/**
	 * <p>Constructor for CatchFilter.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param filters an array of {@link com.mailersend.sdk.inboundroutes.Filter} objects.
	 */
	public CatchFilter(String type, Filter[] filters) {
		this.type = type;
		this.filters = filters;
	}
}
