package co.icesi.pdaily.security.authorization;

import static co.haruk.core.StreamUtils.findOrFail;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.core.testing.jta.NonTransactional;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationConfigurator;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroup;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroupId;
import co.icesi.pdaily.security.authorization.domain.model.ProtectedActivity;
import co.icesi.pdaily.security.authorization.domain.model.ProtectedActivityId;
import co.icesi.pdaily.security.authorization.infraestructure.persistence.AuthorizationGroupRepository;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

/**
 * @author andres2508 on 1/5/20
 **/
@PDailyTest
@DisplayName("Authorization tests")
class AuthorizationConfiguratorIT implements IDataSetDependent, ICDIContainerDependent {
	@Inject
	AuthorizationConfigurator configurator;
	@Inject
	AuthorizationGroupRepository groupRepository;

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.AUTHORIZATION );
	}

	@Override
	public List<Class<?>> requiredBeans() {
		return List.of( TestAuthProducer.class );
	}

	@Test
	@NonTransactional
	@DisplayName("Saves authorization groups correctly")
	void savesAuthorizationGroups() {
		final var groups = TestAuthProducer.generateAuthorizationGroups();
		groups.forEach( configurator::saveAuthorizationGroup );
		for ( AuthorizationGroup expected : groups ) {
			final var found = groupRepository.findOrFail( expected.id() );
			assertEquals( expected.name(), found.name() );
			assertIterableEquals( expected.activities(), found.activities() );
		}
	}

	@Test
	@NonTransactional
	@DisplayName("Load producer from CDI correctly")
	void configureUsingCDI() {
		// GIVEN a CDI Producer
		final var expected = TestAuthProducer.CONSTANT_GROUP;
		// WHEN the configurator discovers the producers
		final var discovered = configurator.discoverGroups();
		// THEN it contains the test producer data 
		final var saved = assertDoesNotThrow( () -> findOrFail( discovered, it -> it.id().equals( expected.id() ) ) );
		assertEquals( expected.name(), saved.name() );
		assertIterableEquals( expected.activities(), saved.activities() );
	}

	@Test
	@NonTransactional
	@DisplayName("Fails if group is empty")
	void failsIfGroupEmpty() {
		assertThrows( IllegalArgumentException.class, () -> {
			configurator.saveAuthorizationGroup(
					AuthorizationGroup.of(
							AuthorizationGroupId.of( "EMPTY" ),
							PlainName.of( "TEST EMPTY" )
					)
			);
		} );
	}

	@Test
	@NonTransactional
	@DisplayName("Removes group activities")
	void removesActivitiesFromGroup() {
		final AuthorizationGroupId groupId = AuthorizationGroupId.of( "EXISTENT" );
		// Encuentra un grupo existente de pruebas, debe tener dos actividades
		final var found = groupRepository.findOrFailWithActivities( groupId );
		// Remueve asociación a la tx actual
		groupRepository.currentEM().detach( found );
		// Elimina una actividad previa
		found.removeActivity( ProtectedActivityId.of( "existent2" ) );
		// Guarda el grupo de nuevo en otra transacción
		configurator.saveAuthorizationGroup( found );
		// El estado debió cambiar
		final var changed = groupRepository.findOrFail( groupId );
		assertEquals( found.activities().size(), changed.activities().size() );
	}

	@Test
	@NonTransactional
	@DisplayName("Changes group activity name")
	void updatesActivity() {
		final AuthorizationGroupId groupId = AuthorizationGroupId.of( "EXISTENT" );
		// Encuentra un grupo existente de pruebas, debe tener dos actividades
		final var found = groupRepository.findOrFailWithActivities( groupId );
		// Remueve asociación a la tx actual
		groupRepository.currentEM().detach( found );
		// Cambia una actividad previa
		ProtectedActivity expected = found.getActivity( ProtectedActivityId.of( "existent" ) );
		expected.setDescription( PlainName.of( TestNamesGenerator.generateName() ) );
		// Guarda el grupo de nuevo en otra transacción
		configurator.saveAuthorizationGroup( found );
		// El estado debió cambiar
		final var changed = groupRepository.findOrFail( groupId );
		ProtectedActivity saved = changed.getActivity( ProtectedActivityId.of( "existent" ) );
		assertEquals( expected.description(), saved.description() );
	}

	@Test
	@DisplayName("Find authorization groups correctly")
	void findsAuthGroups() {
		given().get( "/security/authz/groups" )
				.then()
				.statusCode( 200 )
				.body(
						"size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Find authorization groups including activities correctly")
	void findsAuthGroupsWithActivities() {
		given().queryParam( "iacts", true )
				.get( "/security/authz/groups" )
				.then()
				.statusCode( 200 )
				.body(
						"size", greaterThan( 0 ),
						"[0].activities.size()", greaterThan( 0 )
				);
	}

	@Test
	@NonTransactional
	@DisplayName("Removes authorization groups")
	void removesAuthorizationGroup() {
		// GIVEN the existing groups
		final var all = groupRepository.findAll();
		// GIVEN an group for removal
		final var groupId = AuthorizationGroupId.of( AuthorizationTesting.GROUP_TO_REMOVE_ID );
		// WHEN we remove one group
		all.removeIf( it -> it.id().equals( groupId ) );
		// WHEN we update the groups
		configurator.saveAuthorizationGroups( all );
		// THE group doesn't exist anymore
		final var found = groupRepository.find( groupId );
		assertTrue( found.isEmpty() );
	}
}
