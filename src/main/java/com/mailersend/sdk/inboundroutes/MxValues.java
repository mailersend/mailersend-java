package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>MxValues class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class MxValues {

	@SerializedName("priority")
	public int priority;
	
	@SerializedName("target")
	public String target;
	
	/**
	 * <p>Constructor for MxValues.</p>
	 */
	public MxValues() {
		// intentionally left blank
	}
	
	/**
	 * <p>Constructor for MxValues.</p>
	 *
	 * @param priority a int.
	 * @param target a {@link java.lang.String} object.
	 */
	public MxValues(int priority, String target) {
		this.priority = priority;
		this.target = target;
	}
}
