package co.icesi.pdaily.security.authorization.app;

import co.icesi.pdaily.security.authorization.domain.model.ProtectedActivity;

/**
 * @author andres2508 on 1/5/20
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
