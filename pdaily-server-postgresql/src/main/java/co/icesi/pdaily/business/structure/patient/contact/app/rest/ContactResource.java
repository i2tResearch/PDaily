package co.icesi.pdaily.business.structure.patient.contact.app.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.business.structure.patient.contact.app.ContactAppService;
import co.icesi.pdaily.business.structure.patient.contact.app.ContactRequestDTO;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.view.ContactReadView;

@Path("/business/patient/contact")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

	@Inject
	ContactAppService appService;

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
