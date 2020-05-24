package co.haruk.sms.common.infrastructure;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.infrastructure.persistence.jpa.JPARepository;

/**
 * @author andres2508 on 19/11/19
 **/
@ApplicationScoped
public class TestRepository extends JPARepository<TestTenantEntity> {

	public List<TestTenantEntity> findWithQuery() {
		return findWithNamedQuery( "TestTenantEntity.findByTenant" );
	}
}
