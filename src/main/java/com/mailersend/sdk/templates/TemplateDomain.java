/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.domains.Domain;

/**
 * <p>TemplateDomain class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class TemplateDomain extends Domain {

    /**
     * Null when the API returns the totals as an empty list, which it does for the domain of a template
     */
    @SerializedName("totals")
    @JsonAdapter(TotalsDeserializer.class)
    public TemplateDomainTotals totals;


    /**
     * Reads the totals object, or null for the empty list the API returns when the totals are not loaded
     */
    static class TotalsDeserializer implements JsonDeserializer<TemplateDomainTotals> {

        @Override
        public TemplateDomainTotals deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

            if (!json.isJsonObject()) {

                return null;
            }

            return new Gson().fromJson(json, TemplateDomainTotals.class);
        }
    }
}
