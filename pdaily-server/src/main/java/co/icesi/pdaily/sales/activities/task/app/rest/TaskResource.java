package co.icesi.pdaily.sales.activities.task.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.sales.activities.task.app.TaskAppService;
import co.icesi.pdaily.sales.activities.task.app.TaskDTO;

@Path("/sales/activities/task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
	@Inject
	TaskAppService appService;

	@GET
	public List<TaskDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public TaskDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public TaskDTO saveTask(TaskDTO dto) {
		return appService.saveTask( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteTask( id );
	}
}
