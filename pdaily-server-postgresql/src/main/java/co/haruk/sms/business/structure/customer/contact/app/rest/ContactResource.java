package co.haruk.sms.business.structure.customer.contact.app.rest;

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

import co.haruk.sms.business.structure.customer.contact.app.ContactAppService;
import co.haruk.sms.business.structure.customer.contact.app.ContactRequestDTO;
import co.haruk.sms.business.structure.customer.contact.domain.model.view.ContactReadView;

@Path("/business/customer/contact")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

	@Inject
	ContactAppService appService;

	@GET
	@Path("/for-customer/{customerId}")
	public List<ContactReadView> findForCustomer(@PathParam("customerId") String customerId) {
		return appService.findForCustomer( customerId );
	}

	@GET
	@Path("/sales-contacts-for/{customerId}")
	public List<ContactReadView> findForSalesContact(@PathParam("customerId") String customerId) {
		return appService.findSalesContactsFor( customerId );
	}

	@GET
	@Path("/{id}")
	public ContactReadView findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteById( id );
	}

	@POST
	public ContactReadView saveContact(ContactRequestDTO dto) {
		return appService.saveContact( dto );
	}
}
