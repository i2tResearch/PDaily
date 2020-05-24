package co.haruk.sms.business.structure.businessunit.productgroup.app.rest;

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

import co.haruk.sms.business.structure.businessunit.productgroup.app.ProductGroupAppService;
import co.haruk.sms.business.structure.businessunit.productgroup.app.ProductGroupDTO;

@Path("/business/product-group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductGroupResource {
	@Inject
	ProductGroupAppService appService;

	@POST
	public ProductGroupDTO saveProductGroup(ProductGroupDTO dto) {
		return appService.saveProductGroup( dto );
	}

	@GET
	@Path("/{id}")
	public ProductGroupDTO getProductGroup(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@GET
	public List<ProductGroupDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/for-business-unit/{businessUnitId}")
	public List<ProductGroupDTO> findForBusiness(@PathParam("businessUnitId") String businessUnit) {
		return appService.findForBusiness( businessUnit );
	}

	@DELETE
	@Path("/{id}")
	public void deleteProductGroup(@PathParam("id") String id) {
		appService.deleteProductGroup( id );
	}
}
