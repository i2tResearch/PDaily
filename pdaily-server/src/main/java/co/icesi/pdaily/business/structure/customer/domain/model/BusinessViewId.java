package co.icesi.pdaily.business.structure.customer.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;

/**
 * @author cristhiank on 26/12/19
 **/
@Embeddable
public final class BusinessViewId implements Serializable {
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "customer_id", updatable = false))
	private CustomerId customerId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "business_unit_id", updatable = false))
	private BusinessUnitId businessUnitId;

	protected BusinessViewId() {
	}

	private BusinessViewId(CustomerId customerId, BusinessUnitId businessUnitId) {
		setCustomerId( customerId );
		setBusinessUnitId( businessUnitId );
	}

	public static BusinessViewId of(CustomerId customerId, BusinessUnitId businessUnitId) {
		return new BusinessViewId( customerId, businessUnitId );
	}

	public CustomerId customerId() {
		return customerId;
	}

	private void setCustomerId(CustomerId customerId) {
		this.customerId = Guards.requireNonNull( customerId, "El cliente es requerido" );
	}

	public BusinessUnitId businessUnitId() {
		return businessUnitId;
	}

	private void setBusinessUnitId(BusinessUnitId businessUnitId) {
		this.businessUnitId = requireNonNull( businessUnitId, "La und. de negocios es requerida" );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;
		BusinessViewId that = (BusinessViewId) o;
		return customerId.equals( that.customerId ) &&
				businessUnitId.equals( that.businessUnitId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( customerId, businessUnitId );
	}
}
