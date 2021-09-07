/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

/**
 * Handles the serialization and deserialization strategy for all API objects (Email, Activity, etc.).
 * If a field does not have the @Expose annotiation, it gets de/serialized normally.
 * If it has the @Expose annotation, the annotation's parameters determine the behaviour
 */
public class JsonSerializationDeserializationStrategy implements ExclusionStrategy {

    private boolean deserialize = false;

    /**
     * 
     * @param isDeserialize
     */
    public JsonSerializationDeserializationStrategy(boolean isDeserialize) {
        
        this.deserialize = isDeserialize;
    }
    
    
    /**
     * Just keeping it here because it is required by the ExclusionStrategy interface
     * Always returns false
     * @return false
     */
    @Override
    public boolean shouldSkipClass(Class<?> sClass) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /**
     * Implements the field exclusion strategy
     * If a field does not have the @Expose annotiation, it gets de/serialized normally.
     * If it has the @Expose annotation, the annotation's parameters determine the behaviour
     */
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        
        if (field.getAnnotation(Expose.class) == null) {
            
            return false;
        }
        
        if (this.deserialize) {
            
            return field.getAnnotation(Expose.class).deserialize();
        } else {
            
            return field.getAnnotation(Expose.class).serialize();
        }
    }
}
