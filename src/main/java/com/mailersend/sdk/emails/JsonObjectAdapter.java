/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Reads an object field that the API sends as null when it is empty. Gson's own JsonObject adapter
 * fails on null, while a {@link com.google.gson.annotations.JsonAdapter} skips it.
 */
class JsonObjectAdapter implements JsonDeserializer<JsonObject> {

    @Override
    public JsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        return json.getAsJsonObject();
    }
}
