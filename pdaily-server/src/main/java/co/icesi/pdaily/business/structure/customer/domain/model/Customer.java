package co.icesi.pdaily.business.structure.customer.domain.model;

import static co.haruk.core.StreamUtils.find;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.customer.holding.domain.model.HoldingCompanyId;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.common.infrastructure.persistence.ActiveInactiveStateConverter;
import co.icesi.pdaily.common.model.ActiveInactiveState;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.TaxID;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

/**
 * @author cristhiank on 9/12/19
 **/
@Entity
@Table(name = "bs_customers")
@NamedQuery(name = Customer.findForSubsidiaryAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerReadView(c.id.id,c.taxID.text,c.mainEmailAddress.email,c.name.name,h.id.id,h.name.name,s.id.id,s.name.name,c.reference.text,c.state, c.type)"
		+ " FROM Customer c INNER JOIN Subsidiary s ON c.subsidiaryId = s.id" +
		" LEFT JOIN HoldingCompany h ON c.holdingId = h.id" +
		" WHERE c.tenant = :company AND c.subsidiaryId = :subsidiary")
@NamedQuery(name = Customer.findByIdAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerReadView(c.id.id,c.taxID.text,c.mainEmailAddress.email,c.name.name,h.id.id,h.name.name,s.id.id,s.name.name,c.reference.text,c.state, c.type)"
		+ " FROM Customer c INNER JOIN Subsidiary s ON c.subsidiaryId = s.id" +
		" LEFT JOIN HoldingCompany h ON c.holdingId = h.id" +
		" WHERE c.tenant = :company AND c.id = :id")
@NamedQuery(name = Customer.countForSubsidiary, query = "SELECT COUNT(c.id) FROM Customer c WHERE c.tenant = :company AND c.subsidiaryId = :subsidiary")
public class Customer extends PdailyTenantEntity<CustomerId> {
	private static final String PREFIX = "Customer.";
	public static final String findForSubsidiaryAsReadView = PREFIX + "findForSubsidiaryAsReadView";
	public static final String countForSubsidiary = PREFIX + "countForSubsidiary";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	@EmbeddedId
	private CustomerId id;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CustomerBusinessView> businessViews;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "holding_id"))
	private HoldingCompanyId holdingId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subsidiary_id"))
	private SubsidiaryId subsidiaryId;
	@Embedded
	private Reference reference;
	@Embedded
	private TaxID taxID;
	@Embedded
	@AttributeOverride(name = "email", column = @Column(name = "main_email"))
	private EmailAddress mainEmailAddress;
	@Embedded
	private PlainName name;
	@Convert(converter = ActiveInactiveStateConverter.class)
	private ActiveInactiveState state = ActiveInactiveState.ACTIVE;
	@Convert(converter = CustomerTypeConverter.class)
	private CustomerType type;

	protected Customer() {
	}

	private Customer(
			CustomerId id,
			TaxID taxID,
			HoldingCompanyId holdingId,
			EmailAddress mainEmailAddress,
			PlainName name,
			SubsidiaryId subsidiaryId,
			Reference reference,
			CustomerType type) {
		setId( id );
		setTaxID( taxID );
		setHoldingId( holdingId );
		setMainEmailAddress( mainEmailAddress );
		setName( name );
		setSubsidiaryId( subsidiaryId );
		setReference( reference );
		setCustomerType( type );
	}

	public static Customer of(
			CustomerId id,
			TaxID taxID,
			HoldingCompanyId holdingId,
			EmailAddress mainEmailAddress,
			PlainName name,
			SubsidiaryId subsidiaryId,
			Reference reference,
			CustomerType type) {
		return new Customer( id, taxID, holdingId, mainEmailAddress, name, subsidiaryId, reference, type );
	}

	@Override
	public CustomerId id() {
		return this.id;
	}

	@Override
	public void setId(CustomerId id) {
		this.id = id;
	}

	public TaxID taxID() {
		return taxID;
	}

	private void setTaxID(TaxID taxID) {
		this.taxID = taxID;
	}

	public HoldingCompanyId holdingId() {
		return holdingId;
	}

	private void setHoldingId(HoldingCompanyId holdingId) {
		this.holdingId = holdingId;
	}

	public EmailAddress mainEmailAddress() {
		return mainEmailAddress;
	}

	private void setMainEmailAddress(EmailAddress mainEmailAddress) {
		this.mainEmailAddress = mainEmailAddress;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del cliente es requerido" );
	}

	public boolean isActive() {
		return state == ActiveInactiveState.ACTIVE;
	}

	private void setState(ActiveInactiveState state) {
		this.state = requireNonNull( state, "El estado es requerido" );
	}

	public void updateFrom(Customer changed) {
		setName( changed.name );
		setHoldingId( changed.holdingId );
		setTaxID( changed.taxID );
		setMainEmailAddress( changed.mainEmailAddress );
		setReference( changed.reference );
		setCustomerType( changed.type );
	}

	public void disable() {
		setState( ActiveInactiveState.INACTIVE );
	}

	public void enable() {
		setState( ActiveInactiveState.ACTIVE );
	}

	public SubsidiaryId getSubsidiaryId() {
		return subsidiaryId;
	}

	private void setSubsidiaryId(SubsidiaryId subsidiaryId) {
		this.subsidiaryId = requireNonNull( subsidiaryId, "La filial del cliente es requerida" );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public CustomerType type() {
		return this.type;
	}

	private void setCustomerType(CustomerType type) {
		this.type = Guards.requireNonNull( type, "Es necesario indicar el tipo de cliente." );
	}

	public CustomerBusinessView viewForBusiness(BusinessUnitId businessUnitId) {
		return find( businessViews, it -> it.businessUnitId().equals( businessUnitId ) )
				.orElseThrow( () -> new EntityNotFoundException( "No existe la vista de negocios para cliente" ) );
	}

	public boolean hasViewForBusiness(BusinessUnitId businessUnitId) {
		return find( businessViews, it -> it.businessUnitId().equals( businessUnitId ) ).isPresent();
	}

	public CustomerBusinessView createViewForBusiness(BusinessUnitId businessUnitId, SalesRepId salesRepId) {
		final var newView = CustomerBusinessView.of( this, businessUnitId, salesRepId );
		businessViews.add( newView );
		return newView;
	}
}
