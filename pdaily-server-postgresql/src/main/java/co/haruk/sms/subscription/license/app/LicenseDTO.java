package co.haruk.sms.subscription.license.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.subscription.license.domain.model.License;
import co.haruk.sms.subscription.license.domain.model.LicenseId;

/**
 * @author cristhiank on 15/11/19
 **/
public final class LicenseDTO {
	public String id;
	public String name;

	protected LicenseDTO() {
	}

	private LicenseDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static LicenseDTO of(String id, String name) {
		return new LicenseDTO( id, name );
	}

	public static LicenseDTO of(License license) {
		return new LicenseDTO( license.id().text(), license.name().text() );
	}

	public License toLicense() {
		return License.of( LicenseId.of( id ), PlainName.of( name ) );
	}
}
