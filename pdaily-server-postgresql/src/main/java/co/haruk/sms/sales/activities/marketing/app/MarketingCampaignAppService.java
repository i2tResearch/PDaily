package co.haruk.sms.sales.activities.marketing.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaign;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaignId;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaignValidator;
import co.haruk.sms.sales.activities.marketing.infrastructure.persistence.MarketingCampaignRepository;

@ApplicationScoped
public class MarketingCampaignAppService {
	@Inject
	MarketingCampaignValidator validator;
	@Inject
	MarketingCampaignRepository repository;

	public List<MarketingCampaignDTO> findAll() {
		List<MarketingCampaign> all = repository.findAll();
		return StreamUtils.map( all, MarketingCampaignDTO::of );
	}

	@Transactional
	public MarketingCampaignDTO saveMarketingCampaign(MarketingCampaignDTO dto) {
		final MarketingCampaign changed = dto.toMarketingCampaign();
		MarketingCampaign saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( MarketingCampaignId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return MarketingCampaignDTO.of( saved );
	}

	@Transactional
	public void deleteMarketingCampaign(String id) {
		MarketingCampaignId campaignId = MarketingCampaignId.ofNotNull( id );
		validator.checkBeforeDelete( campaignId );
		repository.delete( campaignId );
	}

	public MarketingCampaignDTO findById(String id) {
		final var found = repository.findOrFail( MarketingCampaignId.ofNotNull( id ) );
		return MarketingCampaignDTO.of( found );
	}
}
