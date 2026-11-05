/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

/**
 * Pagination of the {@code emails} object of a detailed verification list.
 *
 * @author mailersend
 */
public class EmailVerificationListPagination {

	@SerializedName("current_page")
	public int currentPage;

	@SerializedName("per_page")
	public int perPage;

	@SerializedName("total")
	public int total;

	@SerializedName("last_page")
	public int lastPage;

	@SerializedName("from")
	public int from;

	@SerializedName("to")
	public int to;
}
