package co.haruk.sms.sales.salesorder.source.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.sales.salesorder.source.domain.model.OrderSource;
import co.haruk.sms.sales.salesorder.source.domain.model.OrderSourceId;

/**
 * @author cristhiank on 23/12/19
 **/
public final class OrderSourceDTO {
	public String id;
	public String name;

	protected OrderSourceDTO() {
	}

	private OrderSourceDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static OrderSourceDTO of(OrderSource source) {
		return of( source.id().text(), source.name().text() );
	}

	public static OrderSourceDTO of(String id, String name) {
		return new OrderSourceDTO( id, name );
	}

	public OrderSource toOrderSource() {
		return OrderSource.of(
				OrderSourceId.of( id ),
				PlainName.of( name )
		);
	}
}
