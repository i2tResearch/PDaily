package co.haruk.sms.market.measurement.attribute.domain.model.picklist;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttribute;

@Entity
@Table(name = "market_attribute_picklist_details")
@NamedQuery(name = PickListDetail.findByListValue, query = "SELECT a FROM PickListDetail a WHERE UPPER(a.listValue.text) = UPPER(:listValue) AND a.marketAttribute.id = :attributeId AND a.tenant = :company")
@NamedQuery(name = PickListDetail.countById, query = "SELECT count(a.id) FROM PickListDetail a WHERE a.id = :id")
@NamedQuery(name = PickListDetail.findById, query = "SELECT a FROM PickListDetail a WHERE a.id = :id AND a.tenant = :company")
public class PickListDetail extends PdailyTenantEntity<PickListDetailId> {
	private static final String PREFIX = "AttributePickList.";
	public static final String findByListValue = PREFIX + "findByListValue";
	public static final String countById = PREFIX + "countById";
	public static final String findById = PREFIX + "findById";

	@EmbeddedId
	private PickListDetailId id;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "list_value"))
	private Reference listValue;
	@ManyToOne
	@JoinColumn(name = "attribute_id")
	private MarketAttribute marketAttribute;

	protected PickListDetail() {
	}

	private PickListDetail(PickListDetailId id, MarketAttribute marketAttribute, Reference listValue) {
		setId( id );
		setListValue( listValue );
		this.marketAttribute = marketAttribute;
	}

	public static PickListDetail of(PickListDetailId id, MarketAttribute marketAttribute, Reference listValue) {
		return new PickListDetail( id, marketAttribute, listValue );
	}

	public void setListValue(Reference listValue) {
		this.listValue = requireNonNull( listValue, "El valor de seleccion para la lista es obligatorio." );
	}

	public Reference listValue() {
		return listValue;
	}

	public MarketAttribute marketAttribute() {
		return marketAttribute;
	}

	@Override
	public PickListDetailId id() {
		return id;
	}

	@Override
	public void setId(PickListDetailId id) {
		this.id = id;
	}

	public void removeAssociationWithDetail() {
		this.marketAttribute = null;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final var that = (PickListDetail) o;
		return super.equalsInternal(
				that, d -> Objects.equals( marketAttribute.id(), d.marketAttribute.id() )
						&& Objects.equals( listValue, d.listValue )
		);
	}

	@Override
	public int hashCode() {
		return super.hashCode( () -> Objects.hash( marketAttribute.id(), listValue ) );
	}
}
