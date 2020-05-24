package co.haruk.sms.business.structure.businessunit.product.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.product.domain.model.Product;
import co.haruk.sms.business.structure.businessunit.product.domain.model.ProductId;
import co.haruk.sms.business.structure.businessunit.product.domain.model.view.ProductReadView;
import co.haruk.sms.business.structure.businessunit.productbrand.domain.model.ProductBrandId;
import co.haruk.sms.business.structure.businessunit.productgroup.domain.model.ProductGroupId;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLineId;
import co.haruk.sms.common.model.Reference;

@ApplicationScoped
public class ProductRepository extends JPARepository<Product> {

	public Optional<Product> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				Product.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public Optional<Product> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				Product.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public List<ProductReadView> findByBUnitAsReadView(BusinessUnitId businessUnit) {
		requireNonNull( businessUnit );
		return findOtherWithNamedQuery(
				ProductReadView.class,
				Product.findByBUnitAsReadView,
				QueryParameter.with( "businessUnit", businessUnit ).parameters()
		);
	}

	public boolean existsForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		final var count = executeAggregateQuery(
				Product.countByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		).intValue();
		return count > 0;
	}

	public boolean existsForBrand(ProductBrandId brandId) {
		requireNonNull( brandId );
		final var count = executeAggregateQuery(
				Product.countByBrand,
				QueryParameter.with( "brandId", brandId ).parameters()
		).intValue();
		return count > 0;
	}

	public boolean existsForLine(ProductLineId lineId) {
		requireNonNull( lineId );
		final var count = executeAggregateQuery(
				Product.countByLine,
				QueryParameter.with( "lineId", lineId ).parameters()
		).intValue();
		return count > 0;
	}

	public boolean existsForGroup(ProductGroupId groupId) {
		requireNonNull( groupId );
		final var count = executeAggregateQuery(
				Product.countByGroup,
				QueryParameter.with( "groupId", groupId ).parameters()
		).intValue();
		return count > 0;
	}

	public List<ProductReadView> findAllAsReadView() {
		return findOtherWithNamedQuery( ProductReadView.class, Product.findAllAsReadView );
	}

	public ProductReadView findByIdAsReadView(ProductId productId) {
		requireNonNull( productId );
		return findOtherSingleWithNamedQuery(
				ProductReadView.class,
				Product.findByIdAsReadView,
				QueryParameter.with( "id", productId ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}
}
