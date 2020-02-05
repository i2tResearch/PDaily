package co.icesi.pdaily.common.infrastructure.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import co.icesi.pdaily.startup.SMSStartup;

import io.quarkus.runtime.StartupEvent;

/**
 * @author cristhiank on 20/11/19
 **/
@ApplicationScoped
public class SMSQuarkusApp {
	@Inject
	SMSStartup platformStartup;

	void startApp(@Observes StartupEvent event) {
		platformStartup.startPlatform();
	}
}
