package co.haruk.sms.common.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import co.haruk.core.domain.model.entity.Identity;
import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.SQLLoader;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.testing.SMSTest;

/**
 * @author andres2508 on 14/11/19
 **/
@SMSTest
@Tag("cdi")
@DisplayName("Tenant entity tests")
class TenantEntityListenerIT implements ICDIContainerDependent {

	@Inject
	TestRepository repository;

	@BeforeAll
	static void initialize() {
		SQLLoader.loadSQLDataSet( "db/tenant_test.sql" );
	}

	@Test
	@DisplayName("Assigns tenant to entity")
	void prePersist() {
		final TenantId tenant = TenantId.of( Identity.generateNew() );
		final var entity = TestTenantEntity.of( "TESTING" );
		final var saved = HarukSession.executeInTenant( tenant, () -> {
			var s = repository.create( entity );
			repository.currentEM().flush();
			return s;
		} );
		assertEquals( tenant, saved.tenantId() );
	}

	@Test
	@DisplayName("Assigns tenant to entity named query")
	void tenantInNamedQuery() {
		final TenantId tenant = TenantId.of( Identity.generateNew() );
		final var list = HarukSession.executeInTenant( tenant, () -> {
			var res = repository.findWithQuery();
			repository.currentEM().flush();
			return res;
		} );
		assertNotNull( list );
	}
}