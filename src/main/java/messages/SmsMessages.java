package messages;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

public class SmsMessages {
    
	private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    
    public SmsMessages(MailerSend ref) {
    	apiObjectReference = ref;
    }
    
    public SmsMessages page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    public SmsMessages limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    
    public SmsMessageList getMessages() throws MailerSendException {
    	
    	String endpoint = "/sms-messages".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SmsMessageList response = api.getRequest(endpoint, SmsMessageList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    public SmsMessage getMessage(String messageId) throws MailerSendException {
    
    	String endpoint = "/sms-messages/".concat(messageId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsMessageResponse response = api.getRequest(endpoint, SingleSmsMessageResponse.class);
        
        response.message.postDeserialize();
        
        return response.message;
    }
    
    /**
     * Prepares the query part of the request url
     * @return
     */
    private String prepareParamsUrl() {
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        
        params.add("limit=".concat(String.valueOf(limitFilter)));
  
        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {
            
            String attrSep = "&";
            
            if (i == 0) {
                
                attrSep = "?";
            }
            
            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }
        
        return requestParams;
    }
}
