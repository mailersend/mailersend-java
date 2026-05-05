package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>InboundFilter class.</p>
 *
 * <p>Represents a single filter entry within a {@link MatchFilter} or {@link CatchFilter}.
 * Valid comparer values are: {@code equal}, {@code not-equal}, {@code contains},
 * {@code not-contains}, {@code starts-with}, {@code ends-with}, {@code not-starts-with},
 * {@code not-ends-with}.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class InboundFilter {

    @SerializedName("comparer")
    public String comparer;

    @SerializedName("value")
    public String value;

    /**
     * Optional. Required when the parent filter type is {@code match_header}.
     */
    @SerializedName("key")
    public String key;

    /**
     * <p>Constructor for InboundFilter.</p>
     */
    public InboundFilter() {
        // intentionally left blank
    }

    /**
     * <p>Constructor for InboundFilter.</p>
     *
     * @param comparer a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public InboundFilter(String comparer, String value) {
        this.comparer = comparer;
        this.value = value;
    }

    /**
     * <p>Constructor for InboundFilter.</p>
     *
     * @param comparer a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @param key a {@link java.lang.String} object.
     */
    public InboundFilter(String comparer, String value, String key) {
        this.comparer = comparer;
        this.value = value;
        this.key = key;
    }
}
