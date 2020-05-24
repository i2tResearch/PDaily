package co.haruk.sms.security.authorization.app;

import co.haruk.sms.security.authorization.domain.model.ProtectedActivity;

/**
 * @author cristhiank on 1/5/20
 **/
public class ProtectedActivityDTO {
	public String id;
	public String description;

	protected ProtectedActivityDTO() {
	}

	private ProtectedActivityDTO(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public static ProtectedActivityDTO of(ProtectedActivity activity) {
		return new ProtectedActivityDTO( activity.id().text(), activity.description().text() );
	}
}
