package co.haruk.sms.subscription.security;

import java.util.Map;
import java.util.Set;

import javax.enterprise.context.Dependent;

import co.haruk.sms.security.authorization.domain.model.AuthorizationGroup;
import co.haruk.sms.security.authorization.domain.model.AuthorizationGroupBuilder;
import co.haruk.sms.security.authorization.domain.model.IAuthorizationProducer;

/**
 * @author cristhiank on 1/5/20
 **/
@Dependent
public class SubscriptionAuthorizationProducer implements IAuthorizationProducer {
	@Override
	public Set<AuthorizationGroup> authorizationGroups() {
		final var result = AuthorizationGroupBuilder.newGroup( "SUBSCRIPTIONS", "Subscripciones" )
				.addActivities(
						Map.of(
								"ACCOUNTS_ADMIN", "Administrar Cuentas de Empresas",
								"LICENSES_ADMIN", "Administrar Licencias",
								"USERS_ADMIN", "Administrar Usuarios"
						)
				)
				.build();
		return Set.of( result );
	}
}
