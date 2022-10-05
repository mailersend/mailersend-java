/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.vcr;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mailersend.sdk.util.MailerSendHttpClientFactory;

public class VcrRecorder {

	private static VcrTape tape;
	
	/**
	 * Loads the tape and sets up HttpClientVcr to the MaielrSendHttpClientFactory
	 * @param tapeName
	 * @throws IOException
	 */
	public static void useRecording(String tapeName) throws IOException
	{
		Path resourceDirectory = Paths.get("src","test","resources", "fixtures", tapeName + ".json");
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		
		if (tape != null) {
			tape.saveTape();
		}
		
		tape = new VcrTape();
		tape.loadTape(absolutePath);
		
    	HttpClientVcr vcr = new HttpClientVcr();
    	vcr.setTape(tape);
    	
    	MailerSendHttpClientFactory.getInstance().setClient(vcr);
	}
	
	/**
	 * Stops the recording and saves the tape
	 * @throws IOException
	 */
	public static void stopRecording() throws IOException
	{
		if (tape != null) {
			tape.saveTape();
			tape = null;
		}
	}
	
}
