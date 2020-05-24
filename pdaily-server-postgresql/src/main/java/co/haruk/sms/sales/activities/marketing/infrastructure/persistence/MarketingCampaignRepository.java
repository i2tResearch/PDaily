package co.haruk.sms.sales.activities.marketing.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaign;

@ApplicationScoped
public class MarketingCampaignRepository extends JPARepository<MarketingCampaign> {
	public Optional<MarketingCampaign> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				MarketingCampaign.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
