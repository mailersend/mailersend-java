/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Variable class.</p>
 *
 * @author john
 * @version $Id: $Id
 */
public class Variable {

    @SerializedName("email")
    public String email;

    @SerializedName("substitutions")
    public ArrayList<Substitution> substitutions = new ArrayList<Substitution>();
    
    
    /**
     * Adds or replaces a variable substitution
     *
     * @param substitution a {@link com.mailersend.sdk.emails.Substitution} object.
     */
    public void addSubstitution(Substitution substitution) {
        
        Substitution sub = null;
        for (Substitution s : this.substitutions) {
            
            if (s.variable.equals(substitution.variable)) {
                
                sub = s;
                break;
            }
        }
        
        if (sub != null) {
            
            sub.value = substitution.value;
        } else {
            
            sub = new Substitution();
            sub.variable = substitution.variable;
            sub.value = substitution.value;
            
            this.substitutions.add(sub);
        }
    }
}
