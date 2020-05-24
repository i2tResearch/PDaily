package co.haruk.sms.market.measurement.attribute.value.app.rest;

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

import co.haruk.sms.market.measurement.attribute.value.app.AttributeValueAppService;
import co.haruk.sms.market.measurement.attribute.value.app.AttributeValueDTO;

@Path("/market/attribute/value")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AttributeValueResource {
	@Inject
	AttributeValueAppService appService;

	@POST
	public AttributeValueDTO saveAttributeValue(AttributeValueDTO dto) {
		return appService.saveAttributeValue( dto );
	}

	@GET
	@Path("/for-subject/{subjectId}")
	public List<AttributeValueDTO> findBySubjectId(@PathParam("subjectId") String subjectId) {
		return appService.findBySubjectId( subjectId );
	}

	@GET
	@Path("/{id}")
	public AttributeValueDTO findOrFail(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteAttributeValue(@PathParam("id") String id) {
		appService.deleteAttributeValue( id );
	}

}
