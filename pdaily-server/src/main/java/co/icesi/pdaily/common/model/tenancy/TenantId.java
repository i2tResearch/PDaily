package co.icesi.pdaily.common.model.tenancy;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;
import co.haruk.core.domain.model.session.ITenantId;

/**
 * @author cristhiank on 15/11/19
 **/
@Embeddable
public class TenantId extends Identity implements ITenantId {
	protected TenantId() {
	}

	private TenantId(String id) {
		super( id );
	}

	public static TenantId of(Identity model) {
		return new TenantId( model.text() );
	}

	public static TenantId of(String id) {
		return new TenantId( id );
	}
}
