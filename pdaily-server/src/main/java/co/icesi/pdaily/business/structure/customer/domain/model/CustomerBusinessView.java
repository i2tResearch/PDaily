package co.icesi.pdaily.business.structure.customer.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.ZoneId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

/**
 * @author cristhiank on 9/12/19
 **/
@Entity
@Table(name = "bs_customers_business_view")
@NamedQuery(name = CustomerBusinessView.findByIdAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerBusinessReadView(v.id.customerId.id,r.id.id,b.id.id,b.name.name,z.id.id,z.name.name) "
		+ "FROM CustomerBusinessView v " +
		"INNER JOIN BusinessUnit b ON v.id.businessUnitId = b.id " +
		"INNER JOIN SalesRep r ON r.id = v.salesRepId " +
		"LEFT JOIN Zone z ON z.id = v.zoneId " +
		"WHERE v.tenant = :company AND v.id = :viewId")
@NamedQuery(name = CustomerBusinessView.findForCustomerAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerBusinessReadView(v.id.customerId.id,r.id.id,b.id.id,b.name.name,z.id.id,z.name.name) "
		+ "FROM CustomerBusinessView v " +
		"INNER JOIN BusinessUnit b ON v.id.businessUnitId = b.id " +
		"INNER JOIN SalesRep r ON r.id = v.salesRepId " +
		"LEFT JOIN Zone z ON z.id = v.zoneId " +
		"WHERE v.tenant = :company AND v.id.customerId = :customerId")
@NamedQuery(name = CustomerBusinessView.countForZone, query = "SELECT COUNT(v.id.customerId) FROM CustomerBusinessView v WHERE v.tenant = :company AND v.zoneId = :zoneId")
public class CustomerBusinessView extends PdailyTenantEntity<BusinessViewId> {
	private static final String PREFIX = "CustomerBusinessView.";
	public static final String findForCustomerAsReadView = PREFIX + "findForCustomerAsReadView";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";
	public static final String countForZone = PREFIX + "countForZone";

	@EmbeddedId
	private BusinessViewId id;
	@ManyToOne
	@MapsId("customerId")
	@JoinColumn(name = "customer_id")
	private Customer customer;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "sales_rep_id"))
	private SalesRepId salesRepId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "zone_id"))
	private ZoneId zoneId;

	protected CustomerBusinessView() {
	}

	private CustomerBusinessView(Customer customer, BusinessUnitId businessUnitId, SalesRepId salesRepId) {
		setId( BusinessViewId.of( customer.id(), businessUnitId ) );
		this.customer = customer;
		setSalesRepId( salesRepId );
	}

	public static CustomerBusinessView of(Customer customer, BusinessUnitId businessUnitId, SalesRepId salesRepId) {
		return new CustomerBusinessView( customer, businessUnitId, salesRepId );
	}

	@Override
	public BusinessViewId id() {
		return this.id;
	}

	@Override
	public void setId(BusinessViewId id) {
		this.id = id;
	}

	public BusinessUnitId businessUnitId() {
		return id.businessUnitId();
	}

	public SalesRepId salesRepId() {
		return salesRepId;
	}

	public void setSalesRepId(SalesRepId salesRepId) {
		this.salesRepId = requireNonNull( salesRepId, "El rep. de ventas es requerido" );
	}

	public CustomerId customer() {
		return id.customerId();
	}

	public ZoneId zoneId() {
		return zoneId;
	}

	public void setZoneId(ZoneId zoneId) {
		this.zoneId = zoneId;
	}
}
