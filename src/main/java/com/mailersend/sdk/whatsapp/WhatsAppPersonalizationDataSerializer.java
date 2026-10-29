/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serializes {@code header} and {@code body} as an array or an object, depending on the template.
 */
class WhatsAppPersonalizationDataSerializer implements JsonSerializer<WhatsAppPersonalizationData> {

    @Override
    public JsonElement serialize(WhatsAppPersonalizationData data, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject json = new JsonObject();

        if (data.mediaHeader != null) {
            json.add("header", context.serialize(data.mediaHeader));
        } else if (data.namedHeader != null) {
            json.add("header", context.serialize(data.namedHeader));
        } else if (data.header != null) {
            json.add("header", context.serialize(data.header));
        }

        if (data.namedBody != null) {
            json.add("body", context.serialize(data.namedBody));
        } else if (data.body != null) {
            json.add("body", context.serialize(data.body));
        }

        if (data.buttons != null) {
            json.add("buttons", context.serialize(data.buttons));
        }

        return json;
    }
}
