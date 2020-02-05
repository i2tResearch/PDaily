package co.icesi.pdaily.sales.activities.domain.model.details;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class CampaignDetailId extends Identity {
	protected CampaignDetailId() {
	}

	private CampaignDetailId(String id) {
		super( id );
	}

	private CampaignDetailId(UUID id) {
		super( id );
	}

	public static CampaignDetailId ofNotNull(String id) {
		return new CampaignDetailId( id );
	}

	public static CampaignDetailId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new CampaignDetailId( id );
	}

	public static CampaignDetailId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new CampaignDetailId( id );
	}

	public static CampaignDetailId generateNew() {
		return of( UUID.randomUUID() );
	}
}
