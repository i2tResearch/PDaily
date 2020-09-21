package co.icesi.pdaily.business.structure.subsidiary.doctor.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.business.structure.subsidiary.doctor.app.DoctorAppService;
import co.icesi.pdaily.business.structure.subsidiary.doctor.app.DoctorRequestDTO;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView;

/**
 * @author andres2508 on 25/11/19
 **/
@Path("/business/sales-rep")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

	@Inject
	DoctorAppService appService;

	@GET
	public List<DoctorReadView> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/available-users-for-subsidiary/{subsidiaryId}")
	public List<DoctorReadView> findAvailableForSubsidiary(@PathParam("subsidiaryId") String subsidiaryId) {
		return appService.findAvailableUsersForSubsidiary( subsidiaryId );
	}

	@GET
	@Path("/for-subsidiary/{subsidiaryId}")
	public List<DoctorReadView> findForSubsidiary(@PathParam("subsidiaryId") String subsidiaryId) {
		return appService.findForSubsidiary( subsidiaryId );
	}

	@GET
	@Path("/{id}")
	public DoctorReadView findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteEntity(@PathParam("id") String id) {
		appService.delete( id );
	}

	@POST
	@Path("/save-for-user/{userId}")
	public DoctorReadView createForUser(@PathParam("userId") String userId, DoctorRequestDTO request) {
		appService.saveForUser( userId, request );
		return appService.findById( userId );
	}
}
