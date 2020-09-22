package co.icesi.pdaily.security.app;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import co.icesi.pdaily.security.authorization.domain.model.AuthorizationConfigurator;
import co.icesi.pdaily.security.user.app.UserAppService;
import co.icesi.pdaily.startup.SMSStartedEvent;

/**
 * @author andres2508 on 25/2/20
 **/
public class SecurityAppService {
	@Inject
	UserAppService userAppService;
	@Inject
	AuthorizationConfigurator authorizationConfigurator;

	void listenStartedEvent(@Observes SMSStartedEvent event) {
		userAppService.createDefaultAdmin();
		authorizationConfigurator.configureAuthorization();
	}
}
