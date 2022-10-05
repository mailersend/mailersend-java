package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Forward class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Forward {

	@SerializedName("id")
	public String id;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("value")
	public String value;
	
	@SerializedName("secret")
	public String secret;
}
