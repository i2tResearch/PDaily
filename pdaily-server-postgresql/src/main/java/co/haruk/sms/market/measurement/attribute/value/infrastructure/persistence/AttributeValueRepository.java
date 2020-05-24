package co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValue;
import co.haruk.sms.market.measurement.attribute.value.domain.model.SubjectId;

@ApplicationScoped
public class AttributeValueRepository extends JPARepository<AttributeValue> {

	public List<AttributeValue> findBySubjectId(SubjectId subjectId) {
		requireNonNull( subjectId );
		return findWithNamedQuery(
				AttributeValue.findBySubjectId,
				QueryParameter.with( "subjectId", subjectId ).parameters()
		);
	}

	public Optional<AttributeValue> findByAttributeAndSubject(MarketAttributeId attributeId, SubjectId subjectId) {
		requireNonNull( attributeId );
		requireNonNull( subjectId );
		return findSingleWithNamedQuery(
				AttributeValue.findByAttributeAndSubject,
				QueryParameter.with( "attributeId", attributeId ).and( "subjectId", subjectId )
						.parameters()
		);
	}

	public List<AttributeValue> findByMarketAttribute(MarketAttributeId attributeId) {
		requireNonNull( attributeId );
		return findWithNamedQuery(
				AttributeValue.findByMarketAttribute,
				QueryParameter.with( "attributeId", attributeId ).parameters()
		);
	}

	public boolean existsForMarketAttribute(MarketAttributeId attributeId) {
		requireNonNull( attributeId );
		final var count = executeAggregateQuery(
				AttributeValue.countByMarketAttribute,
				QueryParameter.with( "attributeId", attributeId ).parameters()
		).intValue();
		return count > 0;
	}
}
