package co.haruk.sms.startup;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author cristhiank on 21/11/19
 **/
@Path("/health")
public class HealthCheckResource {

	@GET
	public String health() {
		if ( SMSStartup.STARTED ) {
			return "OK";
		}
		throw new IllegalStateException( "System not started" );
	}
}
