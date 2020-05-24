package co.haruk.sms.common.infrastructure.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import co.haruk.sms.startup.SMSStartup;

import io.quarkus.runtime.StartupEvent;

/**
 * @author andres2508 on 20/11/19
 **/
@ApplicationScoped
public class SMSQuarkusApp {

	void startApp(@Observes StartupEvent event) {
		SMSStartup.startPlatform();
	}
}
