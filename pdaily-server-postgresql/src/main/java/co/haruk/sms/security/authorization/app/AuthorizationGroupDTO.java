package co.haruk.sms.security.authorization.app;

import java.util.List;

import co.haruk.core.StreamUtils;
import co.haruk.sms.security.authorization.domain.model.AuthorizationGroup;

/**
 * @author cristhiank on 1/5/20
 **/
public class AuthorizationGroupDTO {
	public String id;
	public String name;
	public List<ProtectedActivityDTO> activities;

	protected AuthorizationGroupDTO() {
	}

	private AuthorizationGroupDTO(
			String id,
			String name) {
		this.id = id;
		this.name = name;
	}

	public static AuthorizationGroupDTO of(AuthorizationGroup group) {
		return new AuthorizationGroupDTO( group.id().text(), group.name().text() );
	}

	public static AuthorizationGroupDTO ofInclActivities(AuthorizationGroup group) {
		final var result = new AuthorizationGroupDTO( group.id().text(), group.name().text() );
		result.activities = StreamUtils.map( group.activities(), ProtectedActivityDTO::of );
		return result;
	}
}
