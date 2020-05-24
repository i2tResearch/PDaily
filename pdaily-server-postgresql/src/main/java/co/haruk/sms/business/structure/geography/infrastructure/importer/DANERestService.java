package co.haruk.sms.business.structure.geography.infrastructure.importer;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author cristhiank on 2/12/19
 **/
@Path("")
@Singleton
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
public interface DANERestService {

	@GET
	List<DaneStateDTO> selectStates(@QueryParam("$select") String select);

	@GET
	List<DaneCityDTO> citiesForState(@QueryParam("c_digo_dane_del_departamento") String stateId,
			@QueryParam("$select") String select);
}
