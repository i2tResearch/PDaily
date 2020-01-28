package co.icesi.pdaily.startup;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author cristhiank on 21/11/19
 **/
@Path("/is-alive")
public class HealthCheckResource {

	@GET
	public String health() {
		return "OK";
	}
}
