package co.haruk.sms.market.measurement.attribute.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttribute;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.SubjectType;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetail;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;

@ApplicationScoped
public class MarketAttributeRepository extends JPARepository<MarketAttribute> {

	public List<MarketAttribute> findForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		return findWithNamedQuery(
				MarketAttribute.findByBusinessUnit,
				QueryParameter.with( "businessId", unitId ).parameters()
		);
	}

	public Optional<MarketAttribute> findByLabel(BusinessUnitId businessId, SubjectType subjectType, PlainName label) {
		requireNonNull( businessId );
		requireNonNull( subjectType );
		return findSingleWithNamedQuery(
				MarketAttribute.findByLabel,
				QueryParameter.with( "businessId", businessId )
						.and( "subjectType", subjectType )
						.and( "label", label.text() ).parameters()
		);
	}

	public Optional<PickListDetail> findByListValue(Reference listValue, MarketAttributeId attributeId) {
		requireNonNull( listValue );
		requireNonNull( attributeId );
		return findOtherSingleWithNamedQuery(
				PickListDetail.class,
				PickListDetail.findByListValue,
				QueryParameter.with( "listValue", listValue.text() ).and( "attributeId", attributeId ).parameters()
		);
	}

	public boolean existsAnyPickListValueWith(PickListDetailId id) {
		requireNonNull( id );
		final int count = executeAggregateQuery(
				PickListDetail.countById,
				QueryParameter.with( "id", id ).parameters()
		).intValue();
		return count > 0;
	}

	public PickListDetail findPickListBy(PickListDetailId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				PickListDetail.class,
				PickListDetail.findById,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}
}
