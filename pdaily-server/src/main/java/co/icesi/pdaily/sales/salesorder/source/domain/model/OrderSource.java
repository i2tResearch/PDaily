package co.icesi.pdaily.sales.salesorder.source.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

/**
 * @author cristhiank on 23/12/19
 **/
@Entity
@Table(name = "sales_order_sources")
@NamedQuery(name = OrderSource.findByName, query = "SELECT o FROM OrderSource o WHERE o.tenant = :company AND UPPER(o.name) = UPPER(:name)")
public class OrderSource extends HarukTenantEntity<OrderSourceId> {
	private static final String PREFIX = "OrderSource.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private OrderSourceId id;
	@Embedded
	private PlainName name;

	protected OrderSource() {
	}

	private OrderSource(OrderSourceId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public static OrderSource of(OrderSourceId id, PlainName name) {
		return new OrderSource( id, name );
	}

	@Override
	public OrderSourceId id() {
		return this.id;
	}

	@Override
	public void setId(OrderSourceId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}
}
