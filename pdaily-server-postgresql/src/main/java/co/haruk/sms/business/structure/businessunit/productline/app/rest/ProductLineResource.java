package co.haruk.sms.business.structure.businessunit.productline.app.rest;

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

import co.haruk.sms.business.structure.businessunit.productline.app.ProductLineAppService;
import co.haruk.sms.business.structure.businessunit.productline.app.ProductLineDTO;

@Path("/business/product-line")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductLineResource {
	@Inject
	ProductLineAppService appService;

	@POST
	public ProductLineDTO saveProductLine(ProductLineDTO dto) {
		return appService.saveProductLine( dto );
	}

	@GET
	@Path("/{id}")
	public ProductLineDTO getProductLine(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@GET
	public List<ProductLineDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/for-business-unit/{businessUnitId}")
	public List<ProductLineDTO> findForBusiness(@PathParam("businessUnitId") String businessUnit) {
		return appService.findForBusiness( businessUnit );
	}

	@DELETE
	@Path("/{id}")
	public void deleteProductLine(@PathParam("id") String id) {
		appService.deleteProductLine( id );
	}
}
