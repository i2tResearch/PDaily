package co.haruk.sms.sales.salesorder.app.rest;

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

import co.haruk.sms.sales.salesorder.app.SalesOrderAppService;
import co.haruk.sms.sales.salesorder.app.SalesOrderDTO;
import co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView;

@Path("/sales/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalesOrderResource {
	@Inject
	SalesOrderAppService appService;

	@POST
	public SalesOrderView saveSalesOrder(SalesOrderDTO dto) {
		return appService.saveSalesOrder( dto );
	}

	@GET
	@Path("/for-sales-rep/{salesRepId}")
	public List<SalesOrderView> findAll(@PathParam("salesRepId") String salesRepId) {
		return appService.findAllBySalesRep( salesRepId );
	}

	@GET
	@Path("/{id}")
	public SalesOrderView findById(@PathParam("id") String id) {
		return appService.findSalesOrderAsReadView( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteSalesOrder(@PathParam("id") String id) {
		appService.deleteSalesOrder( id );
	}
}
