package co.icesi.pdaily.business.structure.customer.contact.role.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.business.structure.customer.contact.role.app.ContactRoleAppService;
import co.icesi.pdaily.business.structure.customer.contact.role.app.ContactRoleDTO;

@Path("/business/customer/contact/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactRoleResource {
	@Inject
	ContactRoleAppService appService;

	@POST
	public ContactRoleDTO saveContactRole(ContactRoleDTO dto) {
		return appService.saveContactRole( dto );
	}

	@GET
	@Path("/{id}")
	public ContactRoleDTO getContactRole(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@GET
	public List<ContactRoleDTO> findAll() {
		return appService.findAll();
	}

	@DELETE
	@Path("/{id}")
	public void deleteContactRole(@PathParam("id") String id) {
		appService.deleteContactRole( id );
	}
}
