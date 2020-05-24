package co.haruk.sms.sales.activities.app;

import co.haruk.sms.common.model.Reference;
import co.haruk.sms.sales.activities.domain.model.Activity;
import co.haruk.sms.sales.activities.domain.model.details.CampaignDetail;
import co.haruk.sms.sales.activities.domain.model.details.CampaignDetailId;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaignId;

public final class CampaignDetailDTO {
	public String id;
	public String campaignId;
	public String detail;

	protected CampaignDetailDTO() {
	}

	private CampaignDetailDTO(String id, String campaignId, String detail) {
		this.id = id;
		this.campaignId = campaignId;
		this.detail = detail;
	}

	public static CampaignDetailDTO of(String id, String campaignId, String detail) {
		return new CampaignDetailDTO( id, campaignId, detail );
	}

	public CampaignDetail toCampaignDetail(Activity activity) {
		CampaignDetailId campaignDetailId = id == null ? CampaignDetailId.generateNew() : CampaignDetailId.ofNotNull( id );
		return CampaignDetail.of(
				activity,
				campaignDetailId,
				MarketingCampaignId.ofNotNull( campaignId ),
				Reference.of( detail )
		);
	}
}
