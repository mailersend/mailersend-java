/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

/**
 * Reads the bulk email message ids. When some emails of the request were not sent, the API keys
 * the ids by the position of their email in the request instead of returning a list.
 */
class MessagesIdAdapter implements JsonDeserializer<String[]> {

    @Override
    public String[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        ArrayList<String> messagesIds = new ArrayList<String>();

        if (json.isJsonArray()) {

            for (JsonElement messageId : json.getAsJsonArray()) {

                messagesIds.add(messageId.getAsString());
            }
        } else if (json.isJsonObject()) {

            for (Map.Entry<String, JsonElement> messageId : json.getAsJsonObject().entrySet()) {

                int position = Integer.parseInt(messageId.getKey());

                while (messagesIds.size() <= position) {

                    messagesIds.add(null);
                }

                messagesIds.set(position, messageId.getValue().getAsString());
            }
        }

        return messagesIds.toArray(new String[0]);
    }
}
