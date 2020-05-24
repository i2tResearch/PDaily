package co.haruk.sms.sales.salesorder.domain.model.details;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.sms.business.structure.businessunit.product.domain.model.ProductId;
import co.haruk.sms.business.structure.customer.contact.domain.model.ContactId;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrder;

@Entity
@Table(name = "sales_order_details")
@NamedQuery(name = OrderDetail.findOrderDetailsAsReadView, query = "SELECT new co.haruk.sms.sales.salesorder.domain.model.view.OrderDetailView(o.id.id, p.id.id, p.name.name, s.id.id, s.name.name, con.id.id, con.name.name, o.bonification, o.quantity) "
		+ " FROM OrderDetail o INNER JOIN Product p ON p.id = o.productId LEFT JOIN Customer s ON s.id = o.supplierId " +
		" LEFT JOIN Contact con ON con.id = o.supplierSalesRepId WHERE o.salesOrder.id = :salesOrder AND o.tenant = :company")
public class OrderDetail extends PdailyTenantEntity<OrderDetailId> {
	private static final String PREFIX = "OrderDetail.";
	public static final String findOrderDetailsAsReadView = PREFIX + "findOrderDetailsAsReadView";

	@EmbeddedId
	private OrderDetailId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "product_id"))
	private ProductId productId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "supplier_id"))
	private CustomerId supplierId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "supplier_sales_rep_id"))
	private ContactId supplierSalesRepId;
	@Column(name = "is_bonification")
	private boolean bonification;
	@Column(name = "quantity")
	private int quantity;
	@ManyToOne
	@JoinColumn(name = "sales_order_id")
	private SalesOrder salesOrder;

	protected OrderDetail() {
	}

	private OrderDetail(SalesOrder salesOrder, OrderDetailId id, ProductId productId, CustomerId supplierId,
			ContactId supplierSalesRepId, boolean bonification, int quantity) {
		setId( id );
		setProductId( productId );
		setSupplierId( supplierId );
		setBonification( bonification );
		setQuantity( quantity );
		setSupplierSalesId( supplierSalesRepId );
		this.salesOrder = salesOrder;
	}

	public static OrderDetail of(SalesOrder salesOrder, OrderDetailId id, ProductId productId, CustomerId supplierId,
			ContactId supplierSalesRepId, boolean bonification, int quantity) {
		return new OrderDetail( salesOrder, id, productId, supplierId, supplierSalesRepId, bonification, quantity );
	}

	private void setSupplierSalesId(ContactId supplierSalesRepId) {
		this.supplierSalesRepId = supplierSalesRepId;
	}

	private void setQuantity(int quantity) {
		Guards.require( quantity > 0, "La cantidad pedida debe ser mayor a cero." );
		this.quantity = quantity;
	}

	private void setSupplierId(CustomerId supplierId) {
		this.supplierId = supplierId;
	}

	private void setProductId(ProductId productId) {
		this.productId = requireNonNull( productId, "El producto en el detalle de la orden es requerido." );
	}

	@Override
	public OrderDetailId id() {
		return id;
	}

	@Override
	public void setId(OrderDetailId id) {
		this.id = id;
	}

	public void removeAssociationWithDetail() {
		this.salesOrder = null;
	}

	public ProductId productId() {
		return productId;
	}

	public CustomerId supplierId() {
		return supplierId;
	}

	public ContactId supplierSalesRepId() {
		return supplierSalesRepId;
	}

	public boolean isBonification() {
		return bonification;
	}

	private void setBonification(boolean bonification) {
		this.bonification = bonification;
	}

	public int quantity() {
		return quantity;
	}

	public void updateFrom(OrderDetail orderDetail) {
		setProductId( orderDetail.productId );
		setBonification( orderDetail.bonification );
		setQuantity( orderDetail.quantity );
		setSupplierId( orderDetail.supplierId );
		setSupplierSalesId( orderDetail.supplierSalesRepId );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		return super.equalsInternal(
				(OrderDetail) o, that -> Objects.equals( productId, that.productId ) &&
						Objects.equals( supplierId, that.supplierId ) &&
						bonification == that.bonification
		);
	}

	@Override
	public int hashCode() {
		return super.hashCode( () -> Objects.hash( productId, supplierId, bonification ) );
	}
}
