package co.haruk.sms.sales.salesorder.source.app.rest;

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

import co.haruk.sms.sales.salesorder.source.app.OrderSourceAppService;
import co.haruk.sms.sales.salesorder.source.app.OrderSourceDTO;

/**
 * @author andres2508 on 23/12/19
 **/
@Path("/sales/order/source")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderSourceResource {
	@Inject
	OrderSourceAppService appService;

	@GET
	public List<OrderSourceDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public OrderSourceDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public OrderSourceDTO saveSource(OrderSourceDTO dto) {
		return appService.saveSource( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteSource( id );
	}
}
