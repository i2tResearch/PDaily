package co.icesi.pdaily.sales.activities.marketing.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.sales.activities.marketing.infrastructure.persistence.MarketingCampaignRepository;

@Dependent
public class MarketingCampaignValidator {
	@Inject
	MarketingCampaignRepository repository;

	public void validate(MarketingCampaign campaign) {
		failIfDuplicatedByName( campaign );
	}

	private void failIfDuplicatedByName(MarketingCampaign campaign) {
		final Optional<MarketingCampaign> found = repository.findByName( campaign.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !campaign.isPersistent() || !existent.equals( campaign );
			if ( mustFail ) {
				throw new DuplicatedRecordException( campaign.name() );
			}
		}
	}
}
