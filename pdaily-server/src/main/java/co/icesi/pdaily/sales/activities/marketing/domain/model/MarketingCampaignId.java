package co.icesi.pdaily.sales.activities.marketing.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class MarketingCampaignId extends Identity {

	protected MarketingCampaignId() {
	}

	private MarketingCampaignId(String id) {
		super( id );
	}

	private MarketingCampaignId(UUID id) {
		super( id );
	}

	public static MarketingCampaignId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new MarketingCampaignId( id );
	}

	public static MarketingCampaignId ofNotNull(String id) {
		return new MarketingCampaignId( id );
	}

	public static MarketingCampaignId generateNew() {
		return new MarketingCampaignId( UUID.randomUUID() );
	}
}
