package co.haruk.sms.sales.activities.task.app.rest;

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

import co.haruk.sms.sales.activities.task.app.TaskAppService;
import co.haruk.sms.sales.activities.task.app.TaskDTO;

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
