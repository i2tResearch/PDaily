package co.icesi.pdaily.common.model.tenancy;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.common.infrastructure.jpa.TenantEntityListener;
import co.icesi.pdaily.common.model.PdailyEntity;

/**
 * @author cristhiank on 14/11/19
 **/
@MappedSuperclass
@EntityListeners(TenantEntityListener.class)
public abstract class PdailyTenantEntity<T> extends PdailyEntity<T> implements ITenantEntity {
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "tenant_id", updatable = false, nullable = false))
	public TenantId tenant;

	@Override
	public void setTenantId(TenantId accountId) {
		this.tenant = Guards.requireNonNull( accountId, "El tenant es requerido" );
	}

	@Override
	public TenantId tenantId() {
		return tenant;
	}
}
