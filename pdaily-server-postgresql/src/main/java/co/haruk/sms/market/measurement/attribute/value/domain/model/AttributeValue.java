package co.haruk.sms.market.measurement.attribute.value.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;

@Entity
@Table(name = "market_attribute_value")
@NamedQuery(name = AttributeValue.findBySubjectId, query = "SELECT v FROM AttributeValue v WHERE v.tenant = :company AND v.subjectId = :subjectId AND v.isDeleted = false")
@NamedQuery(name = AttributeValue.findByAttributeAndSubject, query = "SELECT v FROM AttributeValue v WHERE v.tenant = :company AND v.marketAttributeId = :attributeId AND v.subjectId = :subjectId AND v.isDeleted = false")
@NamedQuery(name = AttributeValue.findByMarketAttribute, query = "SELECT v FROM AttributeValue v WHERE v.tenant = :company AND v.marketAttributeId = :attributeId AND v.isDeleted = false")
@NamedQuery(name = AttributeValue.countByMarketAttribute, query = "SELECT COUNT( v.id ) FROM AttributeValue v WHERE v.tenant = :company AND v.marketAttributeId = :attributeId AND v.isDeleted = false")
public class AttributeValue extends PdailyTenantEntity<AttributeValueId> {
	private static final String PREFIX = "AttributeValue.";
	public static final String findBySubjectId = PREFIX + "findBySubjectId";
	public static final String findByAttributeAndSubject = PREFIX + "findByAttributeAndSubject";
	public static final String findByMarketAttribute = PREFIX + "findByMarketAttribute";
	public static final String countByMarketAttribute = PREFIX + "countByMarketAttribute";

	@EmbeddedId
	private AttributeValueId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "attribute_id"))
	private MarketAttributeId marketAttributeId;
	@Embedded
	private AttributeContent value;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subject_id"))
	private SubjectId subjectId;
	@Column(name = "is_deleted")
	private boolean isDeleted;

	protected AttributeValue() {
	}

	private AttributeValue(AttributeValueId id, MarketAttributeId marketAttributeId, AttributeContent value,
			SubjectId subjectId) {
		setId( id );
		setMarketAttributeId( marketAttributeId );
		setValue( value );
		setSubjectId( subjectId );
		isDeleted = false;
	}

	public static AttributeValue of(AttributeValueId id, MarketAttributeId marketAttributeId, AttributeContent value,
			SubjectId subjectId) {
		return new AttributeValue( id, marketAttributeId, value, subjectId );
	}

	public MarketAttributeId attributeId() {
		return marketAttributeId;
	}

	public AttributeContent value() {
		return value;
	}

	public SubjectId subjectId() {
		return subjectId;
	}

	private void setSubjectId(SubjectId subjectId) {
		this.subjectId = requireNonNull( subjectId, "El sujeto asociado a la respuesta del atributo es obligatorio." );
	}

	public void setValue(AttributeContent value) {
		this.value = value;
	}

	private void setMarketAttributeId(MarketAttributeId marketAttributeId) {
		this.marketAttributeId = requireNonNull( marketAttributeId, "El atributo de la consulta es necesario." );
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void markAsDeleted() {
		this.isDeleted = true;
	}

	@Override
	public AttributeValueId id() {
		return id;
	}

	@Override
	public void setId(AttributeValueId id) {
		this.id = id;
	}

}
