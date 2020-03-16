/*
 * Copyright 2019. All Rights Reserved.
 *
 * Software and code property of SYNEKUS S.A.S.
 *
 * NOTICE: All information contained herein is, and remains the property of
 * SYNEKUS S.A.S and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to SYNEKUS S.A.S and its suppliers
 * and may be covered by Colombian and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from SYNEKUS S.A.S.
 */

package co.icesi.testing;

import java.util.Deque;
import java.util.LinkedList;

import co.haruk.core.testing.data.IDataSet;

/**
 * @author cristhiank on 11/10/19
 **/
public enum DataSets implements IDataSet {
	ACCOUNT( "datasets/account.sql" ),
	LICENSE( "datasets/subscription/license.sql" ),
	USERS( "datasets/subscription/users.sql", ACCOUNT, LICENSE ),
	BUSINESS_UNIT( "datasets/business/business_unit.sql", ACCOUNT ),
	PRODUCTS_LINE( "datasets/business/product_line.sql", BUSINESS_UNIT ),
	PRODUCTS_BRAND( "datasets/business/product_brand.sql", BUSINESS_UNIT ),
	PRODUCTS_GROUP( "datasets/business/product_group.sql", BUSINESS_UNIT ),
	PRODUCTS( "datasets/business/product.sql", BUSINESS_UNIT, PRODUCTS_BRAND, PRODUCTS_LINE, PRODUCTS_GROUP ),
	SUBSIDIARY( "datasets/business/subsidiary.sql", ACCOUNT ),
	SALES_OFFICE( "datasets/business/sales_office.sql", SUBSIDIARY ),
	SALES_REP( "datasets/business/sales_rep.sql", SUBSIDIARY, USERS ),
	ZONE( "datasets/business/zone.sql", SUBSIDIARY ),
	HOLDING_COMPANY( "datasets/business/holding_company.sql", SUBSIDIARY ),
	GEOGRAPHY( "datasets/business/geography.sql" ),
	ADDRESS( "datasets/business/addresses.sql", GEOGRAPHY ),
	CUSTOMER( "datasets/business/customer.sql", HOLDING_COMPANY, ZONE, SALES_OFFICE, SALES_REP, GEOGRAPHY ),
	CUSTOMER_BUSINESS_VIEW( "datasets/business/customer_business_view.sql", BUSINESS_UNIT, CUSTOMER ),
	CONTACT_ROLE( "datasets/business/contact_role.sql", ACCOUNT ),
	CONTACT( "datasets/business/contact.sql", CONTACT_ROLE, CUSTOMER, ADDRESS ),
	ORDER_SOURCE( "datasets/sales/order_source.sql" ),
	TASK( "datasets/sales/activities_task.sql" ),
	PURPOSE( "datasets/sales/activities_purpose.sql" ),
	MARKETING_CAMPAIGN( "datasets/sales/activities_marketing_campaign.sql" ),
	ACTIVITIES( "datasets/sales/activities.sql", PURPOSE, MARKETING_CAMPAIGN, TASK, CUSTOMER, SALES_REP ),
	BODY_PARTS( "datasets/events/body_parts.sql" ),
	INJURY_TYPES( "datasets/events/injury_type.sql" ),
	PHYSICAL_EVENTS( "datasets/events/physical_event.sql", BODY_PARTS, INJURY_TYPES ),
	FOOD_EVENTS( "datasets/events/food_event.sql" );

	private String fileName;
	private IDataSet[] deps;
	private Deque<IDataSet> graph = new LinkedList<>();
	private boolean loaded;

	DataSets(String fileName, IDataSet... deps) {
		this.fileName = fileName;
		this.deps = deps;
	}

	@Override
	public IDataSet[] dependsOn() {
		return deps;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	@Override
	public Deque<IDataSet> getGraph() {
		return graph;
	}

	@Override
	public String toString() {
		return "[DS:" + fileName + "]";
	}
}
