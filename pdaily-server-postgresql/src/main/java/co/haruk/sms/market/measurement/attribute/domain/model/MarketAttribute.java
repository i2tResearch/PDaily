package co.haruk.sms.market.measurement.attribute.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetail;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;

@Entity
@Table(name = "market_attributes")
@NamedQuery(name = MarketAttribute.findByBusinessUnit, query = "SELECT m FROM MarketAttribute m WHERE m.tenant = :company AND m.businessId = :businessId")
@NamedQuery(name = MarketAttribute.findByLabel, query = "SELECT m FROM MarketAttribute m WHERE m.tenant = :company AND m.businessId = :businessId AND m.subjectType = :subjectType AND UPPER(m.label.name) = UPPER(:label)")
public class MarketAttribute extends PdailyTenantEntity<MarketAttributeId> {
	private static final String PREFIX = "MarketAttribute.";
	public static final String findByBusinessUnit = PREFIX + "findByBusinessUnit";
	public static final String findByLabel = PREFIX + "findByLabel";

	@EmbeddedId
	private MarketAttributeId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "business_id"))
	private BusinessUnitId businessId;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "label"))
	private PlainName label;
	@Convert(converter = SubjectTypeConverter.class)
	private SubjectType subjectType;
	@Convert(converter = DataTypeConverter.class)
	private DataType dataType;
	@OneToMany(mappedBy = "marketAttribute", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PickListDetail> pickListDetails;

	protected MarketAttribute() {
	}

	private MarketAttribute(MarketAttributeId id, BusinessUnitId businessId, PlainName label, SubjectType subjectType,
			DataType dataType) {
		setId( id );
		setBusinessId( businessId );
		setLabel( label );
		setSubjectType( subjectType );
		setDataType( dataType );
		this.pickListDetails = new HashSet<>();
	}

	public static MarketAttribute of(MarketAttributeId id, BusinessUnitId businessId, PlainName label,
			SubjectType subjectType, DataType dataType) {
		return new MarketAttribute( id, businessId, label, subjectType, dataType );
	}

	private void setDataType(DataType dataType) {
		this.dataType = requireNonNull( dataType, "El tipo del dato a almacenar es obligatorio." );
	}

	private void setSubjectType(SubjectType subjectType) {
		this.subjectType = requireNonNull( subjectType, "El objeto de asignación es obligatorio." );
	}

	private void setLabel(PlainName name) {
		this.label = requireNonNull( name, "El nombre del atributo es obligatorio." );
	}

	private void setBusinessId(BusinessUnitId businessId) {
		this.businessId = requireNonNull( businessId, "La unidad de negocio es obligatoria." );
	}

	public DataType dataType() {
		return dataType;
	}

	public SubjectType subjectType() {
		return subjectType;
	}

	public PlainName label() {
		return label;
	}

	public BusinessUnitId businessId() {
		return businessId;
	}

	public Set<PickListDetail> pickListDetails() {
		return this.pickListDetails;
	}

	public void addPickListDetail(PickListDetail value) {
		var existent = StreamUtils.find( this.pickListDetails, it -> it.listValue().equals( value.listValue() ) );
		if ( existent.isEmpty() ) {
			if ( !value.isPersistent() ) {
				value.setId( PickListDetailId.generateNew() );
			}
			this.pickListDetails.add( value );
		} else {
			throw new DuplicatedRecordException( value.listValue() );
		}
	}

	private List<PickListDetailId> mergePickListDetails(Set<PickListDetail> pickListDetails) {
		Iterator<PickListDetail> iterator = this.pickListDetails.iterator();
		List<PickListDetailId> removed = new ArrayList<>();
		while ( iterator.hasNext() ) {
			PickListDetail pickListDetail = iterator.next();
			if ( !pickListDetails.contains( pickListDetail ) ) {
				pickListDetail.removeAssociationWithDetail();
				removed.add( pickListDetail.id() );
				iterator.remove();
			}
		}
		for ( PickListDetail pickListDetail : pickListDetails ) {
			var found = StreamUtils.find( this.pickListDetails, it -> it.equals( pickListDetail ) );
			if ( found.isPresent() ) {
				found.get().setListValue( pickListDetail.listValue() );
			} else {
				this.addPickListDetail( pickListDetail );
			}
		}
		return removed;
	}

	@Override
	public MarketAttributeId id() {
		return id;
	}

	@Override
	public void setId(MarketAttributeId id) {
		this.id = id;
	}

	public List<PickListDetailId> updateFrom(MarketAttribute marketAttribute) {
		List<PickListDetailId> pickListRemoved = new ArrayList<>();
		setLabel( marketAttribute.label );
		setDataType( marketAttribute.dataType );
		setSubjectType( marketAttribute.subjectType );
		if ( DataType.isListType( marketAttribute.dataType ) ) {
			pickListRemoved = mergePickListDetails( marketAttribute.pickListDetails );
		}
		return pickListRemoved;
	}
}
