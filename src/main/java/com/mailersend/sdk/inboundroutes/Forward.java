package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>Forward class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Forward {

	/**
	 * @deprecated The API neither accepts nor returns a forward id, so it is not sent and is always null.
	 */
	@Expose(serialize = false)
	@SerializedName("id")
	public String id;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("value")
	public String value;
	
	/**
	 * Returned by the API for webhook forwards. The API does not accept it, so it is not sent.
	 */
	@Expose(serialize = false)
	@SerializedName("secret")
	public String secret;
}
