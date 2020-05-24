package co.haruk.sms.business.structure.customer.app.rest;

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

import co.haruk.sms.business.structure.customer.app.CustomerAppService;
import co.haruk.sms.business.structure.customer.app.CustomerBusinessViewRequest;
import co.haruk.sms.business.structure.customer.app.CustomerRequestDTO;
import co.haruk.sms.business.structure.customer.domain.model.view.CustomerBusinessReadView;
import co.haruk.sms.business.structure.customer.domain.model.view.CustomerReadView;

/**
 * @author andres2508 on 9/12/19
 **/
@Path("/business/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

	@Inject
	CustomerAppService appService;

	@GET
	@Path("/for-subsidiary/{subId}")
	public List<CustomerReadView> findForSubsidiary(@PathParam("subId") String subId) {
		return appService.findForSubsidiary( subId );
	}

	@GET
	@Path("/{id}")
	public CustomerReadView findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteById( id );
	}

	@POST
	@Path("/{id}/disable")
	public void disableCustomer(@PathParam("id") String id) {
		appService.disableCustomer( id );
	}

	@POST
	@Path("/{id}/enable")
	public void enableCustomer(@PathParam("id") String id) {
		appService.enableCustomer( id );
	}

	@POST
	public CustomerReadView saveCustomer(CustomerRequestDTO dto) {
		return appService.saveCustomer( dto );
	}

	@POST
	@Path("/business-view/{customerId}")
	public CustomerBusinessReadView saveBusinessView(
			@PathParam("customerId") String customerId,
			CustomerBusinessViewRequest request) {
		return appService.saveBusinessView( customerId, request );
	}

	@GET
	@Path("/business-view/{customerId}")
	public List<CustomerBusinessReadView> findCustomerBusinessViews(@PathParam("customerId") String customerId) {
		return appService.findCustomerBusinessViews( customerId );
	}

	@DELETE
	@Path("/business-view/{customerId}/{businessId}")
	public void deleteCustomerBusinessView(@PathParam("customerId") String customerId,
			@PathParam("businessId") String businessId) {
		appService.deleteBusinessView( customerId, businessId );
	}

	@GET
	@Path("/business-view/{customerId}/{businessId}")
	public CustomerBusinessReadView findCustomerBusinessView(@PathParam("customerId") String customerId,
			@PathParam("businessId") String businessId) {
		return appService.findCustomerBusinessView( customerId, businessId );
	}

	@GET
	@Path("/suppliers")
	public List<CustomerReadView> findForSupplier() {
		return appService.findSuppliersAsReadView();
	}

	@GET
	@Path("/end-buyers")
	public List<CustomerReadView> findForBuyer() {
		return appService.findEndBuyersAsReadView();
	}

	@GET
	public List<CustomerReadView> allForSalesRep() {
		return appService.findForCurrentSalesRep();
	}
}
