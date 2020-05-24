package co.haruk.sms.sales.activities.marketing.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaign;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaignId;

public class MarketingCampaignDTO {
	public String id;
	public String name;

	protected MarketingCampaignDTO() {
	}

	private MarketingCampaignDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static MarketingCampaignDTO of(String id, String name) {
		return new MarketingCampaignDTO( id, name );
	}

	public static MarketingCampaignDTO of(MarketingCampaign campaign) {
		return new MarketingCampaignDTO( campaign.id().text(), campaign.name().text() );
	}

	public MarketingCampaign toMarketingCampaign() {
		return MarketingCampaign.of(
				MarketingCampaignId.of( id ),
				PlainName.of( name )
		);
	}
}
