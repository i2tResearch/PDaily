package co.icesi.pdaily.common.infrastructure.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import co.icesi.pdaily.startup.SMSStartup;

/**
 * @author cristhiank on 30/10/19
 **/
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SMSEjbStartupService {
	@Inject
	SMSStartup platformStartup;

	@PostConstruct
	void startPlatform() {
		platformStartup.startPlatform();
	}
}
