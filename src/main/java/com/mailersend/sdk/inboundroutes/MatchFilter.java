package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>MatchFilter class.</p>
 *
 * <p>Valid type values: {@code match_all}, {@code match_sender}, {@code match_domain},
 * {@code match_header}. The {@code filters} array is required when type is not
 * {@code match_all}.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class MatchFilter {

	@SerializedName("type")
	public String type;

	@SerializedName("filters")
	public InboundFilter[] filters;

	/**
	 * <p>Constructor for MatchFilter.</p>
	 */
	public MatchFilter() {
		// intentionally left blank
	}

	/**
	 * <p>Constructor for MatchFilter.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param filters an array of {@link com.mailersend.sdk.inboundroutes.InboundFilter} objects.
	 */
	public MatchFilter(String type, InboundFilter[] filters) {
		this.type = type;
		this.filters = filters;
	}
}
