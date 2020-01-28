package co.icesi.pdaily.common.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.domain.model.entity.Identity;
import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.SQLLoader;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.common.model.tenancy.TenantId;
import co.icesi.testing.SMSTest;

/**
 * @author cristhiank on 14/11/19
 **/
@SMSTest
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
		var entity = TestTenantEntity.of( "TESTING" );
		HarukSession.setCurrentTenant( tenant );
		var saved = repository.create( entity );
		assertEquals( tenant, saved.tenantId() );
	}

	@Test
	@DisplayName("Assigns tenant to entity named query")
	void tenantInNamedQuery() {
		final TenantId tenant = TenantId.of( Identity.generateNew() );
		HarukSession.setCurrentTenant( tenant );
		var list = repository.findWithQuery();
		assertNotNull( list );
	}
}
