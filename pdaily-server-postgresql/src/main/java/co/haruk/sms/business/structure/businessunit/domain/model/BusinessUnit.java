package co.haruk.sms.business.structure.businessunit.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_business_units")
@NamedQuery(name = BusinessUnit.findByIntReference, query = "SELECT f FROM BusinessUnit f WHERE UPPER(f.reference.text) = UPPER(:reference) AND f.tenant = :company")
@NamedQuery(name = BusinessUnit.findByName, query = "SELECT f FROM BusinessUnit f WHERE UPPER(f.name.name) = UPPER(:name) AND f.tenant = :company")
@NamedQuery(name = BusinessUnit.findByIdAsReadView, query = "SELECT new co.haruk.sms.business.structure.businessunit.domain.model.view.BusinessUnitReadView( b.id.id, b.name.name, b.reference.text, b.effectiveActivityThreshold.hours) FROM BusinessUnit b WHERE b.id = :id AND b.tenant = :company")
public class BusinessUnit extends PdailyTenantEntity<BusinessUnitId> {
	private static final String PREFIX = "BusinessUnit.";
	public static final String findByIntReference = PREFIX + "findByIntReference";
	public static final String findByName = PREFIX + "findByName";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	@EmbeddedId
	private BusinessUnitId id;
	@Embedded
	private PlainName name;
	@Embedded
	private Reference reference;
	@Embedded
	@AttributeOverride(name = "hours", column = @Column(name = "effective_activity_threshold "))
	private EffectiveActivityThreshold effectiveActivityThreshold;
	@JoinTable(name = "bs_business_units_sellers", joinColumns = @JoinColumn(name = "business_id"), inverseJoinColumns = @JoinColumn(name = "sales_rep_id"))
	@ManyToMany
	private Set<SalesRep> sellers;

	protected BusinessUnit() {
	}

	private BusinessUnit(
			BusinessUnitId id,
			PlainName name,
			Reference reference,
			EffectiveActivityThreshold effectiveActivityThreshold) {
		setId( id );
		setName( name );
		setReference( reference );
		changeEffectiveThreshold( effectiveActivityThreshold );
		this.sellers = new HashSet<>();
	}

	private BusinessUnit(
			BusinessUnitId id,
			PlainName name,
			Reference reference) {
		setId( id );
		setName( name );
		setReference( reference );
		changeEffectiveThreshold( null );
	}

	public static BusinessUnit of(
			BusinessUnitId id,
			PlainName name,
			Reference reference,
			EffectiveActivityThreshold effectiveActivityThreshold) {
		return new BusinessUnit( id, name, reference, effectiveActivityThreshold );
	}

	public static BusinessUnit of(
			BusinessUnitId id,
			PlainName name,
			Reference reference) {
		return new BusinessUnit( id, name, reference );
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido para una unidad de negocio." );
	}

	@Override
	public void setId(BusinessUnitId businessUnitId) {
		this.id = businessUnitId;
	}

	public void changeEffectiveThreshold(EffectiveActivityThreshold effectiveActivityThreshold) {
		this.effectiveActivityThreshold = effectiveActivityThreshold;
	}

	public EffectiveActivityThreshold effectiveThreshold() {
		return this.effectiveActivityThreshold;
	}

	public void updateFrom(BusinessUnit businessUnit) {
		setName( businessUnit.name() );
		setReference( businessUnit.reference() );
		changeEffectiveThreshold( businessUnit.effectiveThreshold() );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	@Override
	public BusinessUnitId id() {
		return this.id;
	}

	public void addSeller(SalesRep seller) {
		sellers.add( seller );
	}

	public void removeSeller(SalesRepId salesRepId) {
		sellers.removeIf( it -> it.id().equals( salesRepId ) );
	}
}
