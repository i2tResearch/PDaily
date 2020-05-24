package co.haruk.sms.sales.activities.app.rest;

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

import co.haruk.sms.sales.activities.app.ActivityAppService;
import co.haruk.sms.sales.activities.app.ActivityRequestDTO;
import co.haruk.sms.sales.activities.domain.model.view.ActivityReadView;

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
	public List<ActivityReadView> findAll() {
		return appService.findAllForCurrentUser();
	}

	@GET
	@Path("/for-sales-rep/{salesRepId}")
	public List<ActivityReadView> findAllBySalesRep(@PathParam("salesRepId") String salesRepId) {
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
