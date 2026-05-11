package com.mailersend.sdk.inboundroutes;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>InboundRouteBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class InboundRouteBuilder {

	private MailerSend apiObjectReference;
	private InboundRouteBuilderBody builderBody;
	
	/**
	 * <p>Constructor for InboundRouteBuilder.</p>
	 *
	 * @param ref a {@link com.mailersend.sdk.MailerSend} object.
	 */
	public InboundRouteBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new InboundRouteBuilderBody();
	}
	
	/**
	 * <p>domainId.</p>
	 *
	 * @param domainId a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder domainId(String domainId) {
		builderBody.domainId = domainId;
		return this;
	}

	/**
	 * <p>name.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder name(String name) {
		
		builderBody.name = name;
		
		return this;
	}
	
	/**
	 * <p>domainEnabled.</p>
	 *
	 * @param enabled a boolean.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder domainEnabled(boolean enabled) {
		builderBody.domainEnabled = enabled;
		
		return this;
	}
	
	/**
	 * <p>inboundDomain.</p>
	 *
	 * @param domain a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundDomain(String domain) {
		builderBody.inboundDomain= domain;
		return this;
	}
	
	/**
	 * <p>inboundAddress.</p>
	 *
	 * @param address a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundAddress(String address) {
		builderBody.inboundAddress = address;
		return this;
	}
	
	/**
	 * <p>inboundSubdomain.</p>
	 *
	 * @param subdomain a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundSubdomain(String subdomain) {
		builderBody.inboundSubdomain = subdomain;
		
		return this;
	}

	/**
	 * <p>inboundPriority.</p>
	 *
	 * @param priority a int.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundPriority(int priority) {
		builderBody.inboundPriority = priority;
		return this;
	}
	
	/**
	 * <p>catchFilter.</p>
	 *
	 * @param filter a {@link com.mailersend.sdk.inboundroutes.CatchFilter} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder catchFilter(CatchFilter filter) {
		builderBody.catchFilter = filter;
		return this;
	}

	/**
	 * <p>catchType.</p>
	 *
	 * <p>Sets the catch type. Must be one of: {@code all}, {@code one}.
	 * Required when the catch filter type is {@code catch_recipient}.</p>
	 *
	 * @param catchType a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder catchType(String catchType) {
		builderBody.catchType = catchType;
		return this;
	}

	/**
	 * <p>matchFilter.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder matchFilter(String type) {
		MatchFilter filter = new MatchFilter();
		filter.type = type;
		builderBody.matchFilter = filter;
		return this;
	}

	/**
	 * <p>matchFilter.</p>
	 *
	 * <p>Sets the match filter with a type and an array of sub-filters. The {@code filters}
	 * array is required when type is not {@code match_all}.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param filters an array of {@link com.mailersend.sdk.inboundroutes.InboundFilter} objects.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder matchFilter(String type, InboundFilter[] filters) {
		builderBody.matchFilter = new MatchFilter(type, filters);
		return this;
	}

	/**
	 * <p>matchType.</p>
	 *
	 * <p>Sets the match type. Must be one of: {@code all}, {@code one}.
	 * Required when the match filter type is {@code match_sender}, {@code match_domain},
	 * or {@code match_header}.</p>
	 *
	 * @param matchType a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder matchType(String matchType) {
		builderBody.matchType = matchType;
		return this;
	}

	/**
	 * <p>forwards.</p>
	 *
	 * @param forwards an array of {@link com.mailersend.sdk.inboundroutes.Forward} objects.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder forwards(Forward[] forwards) {
		builderBody.forwards = forwards;
		return this;
	}
	
	
	private static final String[] VALID_CATCH_FILTER_TYPES = {"catch_all", "catch_recipient"};
	private static final String[] VALID_CATCH_TYPES = {"all", "one"};
	private static final String[] VALID_COMPARERS = {
		"equal", "not-equal", "contains", "not-contains",
		"starts-with", "ends-with", "not-starts-with", "not-ends-with"
	};
	private static final String MATCH_HEADER_TYPE = "match_header";

	/**
	 * Validates the builder body before sending a request.
	 *
	 * @throws MailerSendException if any required field is missing or any value is invalid.
	 */
	private void validate() throws MailerSendException {

		if (builderBody.domainEnabled && (builderBody.inboundDomain == null || builderBody.inboundDomain.isBlank())) {
			throw new MailerSendException("inbound_domain is required when domain_enabled is true");
		}

		if (builderBody.inboundPriority < 0 || builderBody.inboundPriority > 100) {
			throw new MailerSendException("inbound_priority must be between 0 and 100");
		}

		if (builderBody.catchFilter != null) {
			CatchFilter cf = builderBody.catchFilter;

			if (cf.type == null || !Arrays.asList(VALID_CATCH_FILTER_TYPES).contains(cf.type)) {
				throw new MailerSendException("catch_filter.type must be one of: catch_all, catch_recipient");
			}

			if (cf.filters != null && cf.filters.length > 5) {
				throw new MailerSendException("catch_filter.filters must not exceed 5 items");
			}

			if (cf.filters != null) {
				for (Filter filter : cf.filters) {
					if (filter.comparer != null && !Arrays.asList(VALID_COMPARERS).contains(filter.comparer)) {
						throw new MailerSendException("catch_filter.filters.comparer must be one of: equal, not-equal, contains, not-contains, starts-with, ends-with, not-starts-with, not-ends-with");
					}
				}
			}
		}

		if (builderBody.catchType != null && !Arrays.asList(VALID_CATCH_TYPES).contains(builderBody.catchType)) {
			throw new MailerSendException("catch_type must be one of: all, one");
		}

		if (builderBody.matchFilter != null) {
			MatchFilter mf = builderBody.matchFilter;

			if (mf.filters != null) {
				for (InboundFilter filter : mf.filters) {
					if (MATCH_HEADER_TYPE.equals(mf.type) && (filter.key == null || filter.key.isBlank())) {
						throw new MailerSendException("match_filter.filters.key is required when match_filter.type is match_header");
					}

					if (filter.comparer != null && !Arrays.asList(VALID_COMPARERS).contains(filter.comparer)) {
						throw new MailerSendException("match_filter.filters.comparer must be one of: equal, not-equal, contains, not-contains, starts-with, ends-with, not-starts-with, not-ends-with");
					}
				}
			}
		}
	}

	/**
	 * <p>addRoute.</p>
	 *
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRoute} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
	public InboundRoute addRoute() throws MailerSendException {

		validate();

		String endpoint = "/inbound";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody.reset();
        
        SingleInboundRouteResponse response = api.postRequest(endpoint, json, SingleInboundRouteResponse.class);
        
        return response.route;
	}
	
	/**
	 * <p>updateRoute.</p>
	 *
	 * @param inboundRouteId a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRoute} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
	public InboundRoute updateRoute(String inboundRouteId) throws MailerSendException {

		validate();

		String endpoint = "/inbound/" + inboundRouteId;
		
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody.reset();
        
        SingleInboundRouteResponse response = api.putRequest(endpoint, json, SingleInboundRouteResponse.class);
        
        return response.route;
	}
}
