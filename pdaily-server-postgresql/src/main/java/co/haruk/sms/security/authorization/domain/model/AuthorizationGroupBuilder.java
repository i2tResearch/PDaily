package co.haruk.sms.security.authorization.domain.model;

import java.util.Map;

import co.haruk.core.domain.model.entity.PlainName;

/**
 * @author cristhiank on 1/5/20
 **/
public class AuthorizationGroupBuilder {
	private final AuthorizationGroup group;

	private AuthorizationGroupBuilder(AuthorizationGroup group) {
		this.group = group;
	}

	public static AuthorizationGroupBuilder newGroup(String groupId, String groupName) {
		return new AuthorizationGroupBuilder(
				AuthorizationGroup.of( AuthorizationGroupId.of( groupId ), PlainName.of( groupName ) )
		);
	}

	public AuthorizationGroupBuilder addActivity(String activityId, String activityName) {
		group.addActivity( activityId, activityName );
		return this;
	}

	public AuthorizationGroupBuilder addActivities(Map<String, String> activities) {
		activities.forEach( group::addActivity );
		return this;
	}

	public AuthorizationGroup build() {
		return group;
	}
}
