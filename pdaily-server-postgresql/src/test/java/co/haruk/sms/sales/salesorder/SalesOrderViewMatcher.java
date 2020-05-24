package co.haruk.sms.sales.salesorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.bind.JsonbBuilder;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import co.haruk.core.StreamUtils;
import co.haruk.sms.sales.salesorder.app.OrderDetailDTO;
import co.haruk.sms.sales.salesorder.app.SalesOrderDTO;
import co.haruk.sms.sales.salesorder.domain.model.view.OrderDetailView;
import co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView;

public class SalesOrderViewMatcher extends BaseMatcher<String> {
	private final SalesOrderDTO request;
	private final List<String> errors = new ArrayList<>();

	private SalesOrderViewMatcher(SalesOrderDTO dto) {
		this.request = dto;
	}

	public static SalesOrderViewMatcher of(SalesOrderDTO dto) {
		return new SalesOrderViewMatcher( dto );
	}

	@Override
	public boolean matches(Object actual) {
		final var readView = JsonbBuilder.create().fromJson( actual.toString(), SalesOrderView.class );
		boolean result = readView.buyerId.equalsIgnoreCase( request.buyerId )
				&& readView.salesRepId.equalsIgnoreCase( request.salesRepId )
				&& readView.sourceId.equalsIgnoreCase( request.sourceId ) && readView.orderDate.equals( request.orderDate )
				&& readView.creationDate != null;
		result = request.id != null ? readView.id.equalsIgnoreCase( request.id ) && result : result;
		result = request.comments != null ? readView.comments.equalsIgnoreCase( request.comments ) && result : result;

		addErrorIfFalse( result, "Basic data didn't match" );

		result = result && detailsValidate( request.orderDetails, readView.orderDetails );
		addErrorIfFalse( result, "Details data didn't match" );

		return result;
	}

	private boolean detailsValidate(List<OrderDetailDTO> detailsRequest, List<OrderDetailView> detailsViews) {
		boolean result = detailsRequest.size() == detailsViews.size();
		addErrorIfFalse( result, "Size details arrays is not equals" );
		for ( OrderDetailDTO detail : detailsRequest ) {
			Optional<OrderDetailView> found = StreamUtils
					.find( detailsViews, it -> detail.productId.equalsIgnoreCase( it.productId ) &&
							detail.supplierId.equalsIgnoreCase( it.supplierId ) &&
							detail.quantity == it.quantity
					);
			if ( found.isPresent() ) {
				OrderDetailView detailReadView = found.get();
				result = detail.id != null ? detail.id.equalsIgnoreCase( detailReadView.id ) && result : result;
				result = detail.supSalesRepId != null
						? detail.supSalesRepId.equalsIgnoreCase( detailReadView.supplierSalesRepId )
								&& result
						: result;
				result = result && detail.quantity == detailReadView.quantity
						&& detail.isBonification == detailReadView.isBonification
						&& detail.productId.equalsIgnoreCase( detailReadView.productId );
			} else {
				return false;
			}
		}
		return result;
	}

	private void addErrorIfFalse(boolean result, String message) {
		if ( !result ) {
			errors.add( message );
		}
	}

	@Override
	public void describeTo(Description description) {

	}
}
