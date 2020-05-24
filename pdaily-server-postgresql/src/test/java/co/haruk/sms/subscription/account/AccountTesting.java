package co.haruk.sms.subscription.account;

import co.haruk.sms.common.model.tenancy.TenantId;

/**
 * @author andres2508 on 14/11/19
 **/
public final class AccountTesting {
	public static final String ACCOUNT_ID = "F8523E75-9070-4A60-991A-BF22A46F0866";
	public static final String ACCOUNT_TO_UPDATE = "B99E09F6-2C6C-477F-AB4D-B9FC1543F352";
	public static final String ACCOUNT_DUPLICATED_TAXID = "9ACA5C8C-8A05-4075-8555-4B84EC727AA2";
	public static final String ACCOUNT_TO_DELETE = "75FFBBEA-4EAA-4A55-8799-07EA34CFEFA5";
	public static final TenantId TEST_TENANT = TenantId.of( ACCOUNT_ID );

	private AccountTesting() {
	}
}
