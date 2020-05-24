package co.haruk.sms.business.structure.businessunit.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.businessunit.infrastructure.persistence.BusinessUnitRepository;
import co.haruk.sms.business.structure.businessunit.product.infrastructure.persistence.ProductRepository;
import co.haruk.sms.business.structure.businessunit.productbrand.infrastructure.persistence.ProductBrandRepository;
import co.haruk.sms.business.structure.businessunit.productgroup.infrastructure.persistence.ProductGroupRepository;
import co.haruk.sms.business.structure.businessunit.productline.infrastructure.persistence.ProductLineRepository;
import co.haruk.sms.business.structure.businessunit.zone.infrastructure.persistence.ZoneRepository;

@Dependent
public class BusinessUnitValidator {
	@Inject
	BusinessUnitRepository unitRepository;
	@Inject
	ProductBrandRepository brandRepository;
	@Inject
	ProductGroupRepository productGroupRepository;
	@Inject
	ProductLineRepository productLineRepository;
	@Inject
	ZoneRepository zoneRepository;
	@Inject
	ProductRepository productRepository;

	public void validate(BusinessUnit businessUnit) {
		if ( businessUnit.reference() != null ) {
			failIfDuplicateReference( businessUnit );
		}
		failIfDuplicatedName( businessUnit );
	}

	private void failIfDuplicateReference(BusinessUnit businessUnit) {
		var found = unitRepository.findByReference( businessUnit.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !businessUnit.isPersistent() || !existent.equals( businessUnit );
			if ( mustFail ) {
				throw new DuplicatedRecordException( businessUnit.reference() );
			}
		}
	}

	private void failIfDuplicatedName(BusinessUnit businessUnit) {
		var found = unitRepository.findByName( businessUnit.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !businessUnit.isPersistent() || !existent.equals( businessUnit );
			if ( mustFail ) {
				throw new DuplicatedRecordException( businessUnit.reference() );
			}
		}
	}

	public void checkBeforeDelete(BusinessUnitId unitId) {
		boolean hasZones = zoneRepository.existsAnyForBusinessUnit( unitId );
		check( !hasZones, "No se puede eliminar la und. de negocio, tiene zonas asignadas" );
		final boolean hasBrands = brandRepository.existsForBusinessUnit( unitId );
		check( !hasBrands, "No puede eliminar la und. de negocio, tiene marcas asignadas" );
		final boolean hasGroups = productGroupRepository.existsForBusinessUnit( unitId );
		check( !hasGroups, "No puede eliminar la und. de negocio, tiene grupos de producto asignadas" );
		final boolean hasLines = productLineRepository.existsForBusinessUnit( unitId );
		check( !hasLines, "No puede eliminar la und. de negocio, tiene lineas de producto asignadas" );
		final boolean hasProducts = productRepository.existsForBusinessUnit( unitId );
		check( !hasProducts, "No puede eliminar la und. de negocio, tiene productos asignados" );
	}
}
