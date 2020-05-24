package co.haruk.sms.security.authorization;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.sms.security.authorization.domain.model.AuthorizationGroup;
import co.haruk.sms.security.authorization.domain.model.AuthorizationGroupBuilder;
import co.haruk.sms.security.authorization.domain.model.IAuthorizationProducer;

/**
 * @author cristhiank on 1/5/20
 **/
public final class TestAuthProducer implements IAuthorizationProducer {

	final static AuthorizationGroup CONSTANT_GROUP = AuthorizationGroupBuilder
			.newGroup( "TEST_FIXED", "Grupo fijo para validar" )
			.addActivity( "TEST_FIXED_ACT", "Actividad fija para validar" )
			.addActivity( "TEST_FIXED_ACT2", "Actividad fija para validar" )
			.addActivity( "TEST_FIXED_ACT3", "Actividad fija para validar" )
			.build();

	protected TestAuthProducer() {
	}

	public static Set<AuthorizationGroup> generateAuthorizationGroups() {
		Set<AuthorizationGroup> generated = new HashSet<>();
		// Fake 1-10 random groups
		final var groupCount = ThreadLocalRandom.current().nextInt( 1, 10 );
		for ( int i = 0; i < groupCount; i++ ) {
			final var builder = AuthorizationGroupBuilder.newGroup(
					TestNamesGenerator.generateCode(),
					TestNamesGenerator.generateName()
			);
			// Fake 1-100 random activities
			final var activityCount = ThreadLocalRandom.current().nextInt( 1, 100 );
			for ( int j = 0; j < activityCount; j++ ) {
				builder.addActivity(
						TestNamesGenerator.generateCode(),
						TestNamesGenerator.generateName()
				);
			}
			generated.add( builder.build() );
		}
		return generated;
	}

	@Override
	public Set<AuthorizationGroup> authorizationGroups() {
		return Set.of( CONSTANT_GROUP );
	}
}
