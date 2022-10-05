package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Filter class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Filter {

	
	@SerializedName("type")
	public String type;
	
	@SerializedName("key")
	public String key;
	
	@SerializedName("comparer")
	public String comparer;
	
	@SerializedName("value")
	public String value;
}
