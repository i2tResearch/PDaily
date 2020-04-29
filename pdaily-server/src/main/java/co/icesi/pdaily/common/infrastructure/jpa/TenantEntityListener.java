package co.icesi.pdaily.common.infrastructure.jpa;

import javax.persistence.PrePersist;

import co.icesi.pdaily.common.infrastructure.session.PdailySession;
import co.icesi.pdaily.common.model.tenancy.ITenantEntity;

/**
 * @author cristhiank on 14/11/19
 **/
public class TenantEntityListener {

	@PrePersist
	void prePersist(Object entity) {
		if ( entity instanceof ITenantEntity ) {
			((ITenantEntity) entity).setTenantId( PdailySession.currentTenant() );
		}
	}
}
