package co.icesi.pdaily.business.structure.businessunit.productbrand.app.rest;

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

import co.icesi.pdaily.business.structure.businessunit.productbrand.app.ProductBrandAppService;
import co.icesi.pdaily.business.structure.businessunit.productbrand.app.ProductBrandDTO;

@Path("/business/product-brand")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductBrandResource {
	@Inject
	ProductBrandAppService appService;

	@POST
	public ProductBrandDTO saveProductBrand(ProductBrandDTO dto) {
		return appService.saveProductBrand( dto );
	}

	@GET
	@Path("/{id}")
	public ProductBrandDTO getProductBrand(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@GET
	public List<ProductBrandDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/for-business-unit/{businessUnitId}")
	public List<ProductBrandDTO> findForBusiness(@PathParam("businessUnitId") String businessUnit) {
		return appService.findForBusiness( businessUnit );
	}

	@DELETE
	@Path("/{id}")
	public void deleteProductBrand(@PathParam("id") String id) {
		appService.deleteProductBrand( id );
	}
}
