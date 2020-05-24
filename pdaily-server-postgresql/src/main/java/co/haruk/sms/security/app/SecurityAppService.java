package co.haruk.sms.security.app;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import co.haruk.sms.security.authorization.domain.model.AuthorizationConfigurator;
import co.haruk.sms.security.user.app.UserAppService;
import co.haruk.sms.startup.SMSStartedEvent;

/**
 * @author cristhiank on 25/2/20
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
