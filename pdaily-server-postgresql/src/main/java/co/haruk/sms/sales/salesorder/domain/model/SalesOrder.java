package co.haruk.sms.sales.salesorder.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;
import co.haruk.sms.sales.salesorder.domain.model.details.OrderDetail;
import co.haruk.sms.sales.salesorder.source.domain.model.OrderSourceId;

@Entity
@Table(name = "sales_orders")
@NamedQuery(name = SalesOrder.findSalesOrderAsReadView, query = "SELECT new co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView(s.id.id, o.id.id, o.name.name, c.id.id, c.name.name, s.salesRepId.id, s.creationDate.date, s.orderDate.date, s.comments.text) "
		+ " FROM SalesOrder s INNER JOIN OrderSource o ON o.id = s.sourceId INNER JOIN Customer c ON c.id = s.buyerId " +
		" WHERE s.id = :salesOrder AND s.tenant = :company")
@NamedQuery(name = SalesOrder.findBySalesRepAsReadView, query = "SELECT new co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView(s.id.id, o.id.id, o.name.name, c.id.id, c.name.name, s.salesRepId.id, s.creationDate.date, s.orderDate.date, s.comments.text) "
		+ " FROM SalesOrder s INNER JOIN OrderSource o ON o.id = s.sourceId INNER JOIN Customer c ON c.id = s.buyerId " +
		" WHERE s.salesRepId = :salesRepId AND s.tenant = :company")
@NamedQuery(name = SalesOrder.countOrdersForCustomerInRange, query = "SELECT COUNT(o.id) FROM SalesOrder o WHERE o.tenant = :company AND o.buyerId = :customerId AND o.salesRepId = :salesRepId AND o.orderDate.date BETWEEN :startDate AND :endDate")
public class SalesOrder extends PdailyTenantEntity<SalesOrderId> {
	private static final String PREFIX = "SalesOrder.";
	public static final String findSalesOrderAsReadView = PREFIX + "findSalesOrderAsReadView";
	public static final String findBySalesRepAsReadView = PREFIX + "findBySalesRepAsReadView";
	public static final String countOrdersForCustomerInRange = PREFIX + "countOrdersForCustomerInRange";

	@EmbeddedId
	private SalesOrderId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "source_id"))
	private OrderSourceId sourceId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "sales_rep_id"))
	private SalesRepId salesRepId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "buyer_id"))
	private CustomerId buyerId;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "creation_date"))
	private UTCDateTime creationDate;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "order_date"))
	private UTCDateTime orderDate;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "comments"))
	private Reference comments;
	@OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderDetail> orderDetails;

	protected SalesOrder() {
	}

	private SalesOrder(SalesOrderId id, OrderSourceId sourceId, SalesRepId salesRepId, CustomerId buyerId,
			UTCDateTime orderDate, Reference comments) {
		setId( id );
		setSourceId( sourceId );
		setSalesRepId( salesRepId );
		setBuyerId( buyerId );
		setCreationDate( UTCDateTime.now() );
		setOrderDate( orderDate );
		setComments( comments );
		this.orderDetails = new HashSet<>();
	}

	public static SalesOrder of(SalesOrderId id, OrderSourceId sourceId, SalesRepId salesRepId, CustomerId buyerId,
			UTCDateTime orderDate, Reference comments) {
		return new SalesOrder( id, sourceId, salesRepId, buyerId, orderDate, comments );
	}

	public OrderSourceId sourceId() {
		return sourceId;
	}

	public SalesRepId salesRepId() {
		return salesRepId;
	}

	public CustomerId buyerId() {
		return buyerId;
	}

	public UTCDateTime creationDate() {
		return creationDate;
	}

	public UTCDateTime orderDate() {
		return orderDate;
	}

	public Reference comments() {
		return comments;
	}

	public Set<OrderDetail> orderDetails() {
		return orderDetails;
	}

	private void setOrderDate(UTCDateTime orderDate) {
		this.orderDate = requireNonNull( orderDate, "La fecha en la que se realizo la transferencia es requerida." );
	}

	private void setCreationDate(UTCDateTime creationDate) {
		this.creationDate = requireNonNull( creationDate, "La fecha de creacion de la transferencia es requerida." );
	}

	private void setBuyerId(CustomerId buyer) {
		this.buyerId = requireNonNull( buyer, "El cliente que genero la transferencia es requerido" );
	}

	private void setSalesRepId(SalesRepId salesRepId) {
		this.salesRepId = requireNonNull( salesRepId, "El representante de ventas que realizo la transferencia es requerido." );
	}

	private void setSourceId(OrderSourceId sourceId) {
		this.sourceId = requireNonNull( sourceId, "El origen de la trasnferencia es requerido." );
	}

	private void setComments(Reference comments) {
		this.comments = comments;
	}

	@Override
	public SalesOrderId id() {
		return id;
	}

	@Override
	public void setId(SalesOrderId id) {
		this.id = id;
	}

	public void addOrderDetail(OrderDetail orderDetail) {
		this.orderDetails.add( orderDetail );
	}

	private void setOrderDetails(Set<OrderDetail> orderDetails) {
		Iterator<OrderDetail> iterator = this.orderDetails.iterator();
		while ( iterator.hasNext() ) {
			OrderDetail orderDetail = iterator.next();
			if ( !orderDetails.contains( orderDetail ) ) {
				iterator.remove();
				orderDetail.removeAssociationWithDetail();
			}
		}

		for ( OrderDetail orderDetail : orderDetails ) {
			var found = StreamUtils.find( this.orderDetails, it -> it.equals( orderDetail ) );
			if ( found.isPresent() ) {
				found.get().updateFrom( orderDetail );
			} else {
				this.addOrderDetail( orderDetail );
			}
		}
	}

	public void updateFrom(SalesOrder salesOrder) {
		setSourceId( salesOrder.sourceId );
		setBuyerId( salesOrder.buyerId );
		setOrderDate( salesOrder.orderDate );
		setOrderDetails( salesOrder.orderDetails );
		setComments( salesOrder.comments );
	}
}
