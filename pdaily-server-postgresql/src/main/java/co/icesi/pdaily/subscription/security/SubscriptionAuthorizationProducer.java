package co.icesi.pdaily.subscription.security;

import java.util.Map;
import java.util.Set;

import javax.enterprise.context.Dependent;

import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroup;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroupBuilder;
import co.icesi.pdaily.security.authorization.domain.model.IAuthorizationProducer;

/**
 * @author andres2508 on 1/5/20
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
