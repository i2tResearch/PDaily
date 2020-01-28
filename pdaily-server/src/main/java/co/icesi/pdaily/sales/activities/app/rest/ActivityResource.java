package co.icesi.pdaily.sales.activities.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.sales.activities.app.ActivityAppService;
import co.icesi.pdaily.sales.activities.app.ActivityRequestDTO;
import co.icesi.pdaily.sales.activities.domain.model.view.ActivityReadView;

@Path("/sales/activities")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
	@Inject
	ActivityAppService appService;

	@POST
	public ActivityReadView saveActivity(ActivityRequestDTO dto) {
		return appService.saveActivity( dto );
	}

	@GET
	@Path("/for-sales-rep/{salesRepId}")
	public List<ActivityReadView> findAll(@PathParam("salesRepId") String salesRepId) {
		return appService.findAllBySalesRep( salesRepId );
	}

	@GET
	@Path("/{id}")
	public ActivityReadView findById(@PathParam("id") String id) {
		return appService.findActivityByIdAsReadView( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteActivity(@PathParam("id") String id) {
		appService.deleteActivity( id );
	}
}
