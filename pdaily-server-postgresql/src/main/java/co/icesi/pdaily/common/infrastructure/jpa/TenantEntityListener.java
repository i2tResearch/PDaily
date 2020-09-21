package co.icesi.pdaily.common.infrastructure.jpa;

import javax.persistence.PrePersist;

import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.common.model.tenancy.ITenantEntity;

/**
 * @author andres2508 on 14/11/19
 **/
public class TenantEntityListener {

	@PrePersist
	void prePersist(Object entity) {
		if ( entity instanceof ITenantEntity ) {
			((ITenantEntity) entity).setTenantId( HarukSession.currentTenant() );
		}
	}
}
