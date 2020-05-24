package co.haruk.sms.sales.activities.marketing.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;
import co.haruk.sms.sales.activities.marketing.infrastructure.persistence.MarketingCampaignRepository;

@Dependent
public class MarketingCampaignValidator {
	@Inject
	MarketingCampaignRepository repository;
	@Inject
	ActivityRepository activityRepository;

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

	public void checkBeforeDelete(MarketingCampaignId campaignId) {
		boolean hasCampaigns = activityRepository.existsForCampaign( campaignId );
		check( !hasCampaigns, "No se puede eliminar la campaña de mercadeo, tiene actividades asignadas" );
	}
}
