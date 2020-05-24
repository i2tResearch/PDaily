package co.haruk.sms.common.infrastructure.session;

import java.util.Locale;

import co.haruk.core.domain.model.session.ITenantId;
import co.haruk.core.domain.model.session.SessionProvider;

/**
 * @author andres2508 on 16/11/19
 **/
public class HarukSessionProvider implements SessionProvider {
	@Override
	public ITenantId getCompanyId() {
		return HarukSession.currentTenant();
	}

	@Override
	public String getUser() {
		return null;
	}

	@Override
	public boolean isDryRun() {
		return false;
	}

	@Override
	public void setDryRun(boolean value) {
		throw new UnsupportedOperationException( "Haruk session does not support dry-run" );
	}

	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}
}
