package co.icesi.pdaily.testing;

import java.util.Deque;
import java.util.LinkedList;

import co.haruk.core.testing.data.IDataSet;

/**
 * @author andres2508 on 11/10/19
 **/
public enum DataSets implements IDataSet {
	ACCOUNT( "datasets/account.sql" ),
	LICENSE( "datasets/subscription/license.sql" ),
	USERS( "datasets/security/users.sql", ACCOUNT, LICENSE ),
	BUSINESS_UNIT( "datasets/business/business_unit.sql", ACCOUNT ),
	PRODUCTS_LINE( "datasets/business/product_line.sql", BUSINESS_UNIT ),
	PRODUCTS_BRAND( "datasets/business/product_brand.sql", BUSINESS_UNIT ),
	PRODUCTS_GROUP( "datasets/business/product_group.sql", BUSINESS_UNIT ),
	PRODUCTS( "datasets/business/product.sql", BUSINESS_UNIT, PRODUCTS_BRAND, PRODUCTS_LINE, PRODUCTS_GROUP ),
	SUBSIDIARY( "datasets/business/subsidiary.sql", ACCOUNT ),
	SALES_REP( "datasets/business/sales_rep.sql", SUBSIDIARY, USERS, BUSINESS_UNIT ),
	ZONE( "datasets/business/zone.sql", BUSINESS_UNIT ),
	GEOGRAPHY( "datasets/business/geography.sql" ),
	ADDRESS( "datasets/business/addresses.sql", GEOGRAPHY ),
	CONTACT_ROLE( "datasets/business/contact_role.sql", ACCOUNT ),
	CONTACT( "datasets/business/contact.sql", CONTACT_ROLE, ADDRESS ),
	MARKET_ATTRIBUTES( "datasets/market_measurement/market_attribute.sql" ),
	PICK_LIST( "datasets/market_measurement/attribute_picklist.sql", MARKET_ATTRIBUTES ),
	ATTRIBUTE_VALUES( "datasets/market_measurement/attribute_value.sql", PICK_LIST ),
	AUTHORIZATION( "datasets/security/authorization.sql", USERS ),
	PICK_LIST_SERVICE( "datasets/market_measurement/picklist_service.sql" ),
	MARKET_ATTRIBUTE_DASHBOARD( "datasets/analytics/market-attribute-dashboard.sql", CONTACT ),

	PATIENTS( "datasets/business/patients.sql" ),
	BODY_PARTS( "datasets/events/body_parts.sql" ),
	INJURY_TYPES( "datasets/events/injury_type.sql" ),
	PHYSICAL_EVENTS( "datasets/events/physical_event.sql", BODY_PARTS, INJURY_TYPES ),
	FOOD_EVENTS( "datasets/events/food_event.sql" ),
	LEVODOPA_TYPE( "datasets/clinical.base/levodopa_type.sql" ),
	LEVODOPA_MEDICINE( "datasets/clinical.base/medicine_levodopa.sql", LEVODOPA_TYPE ),
	ANIMIC_TYPE( "datasets/clinical.base/animic_type.sql" ),
	ANIMIC_EVENT( "datasets/events/animic_event.sql", ANIMIC_TYPE ),
	FOOD_SCHEDULE( "datasets/schedules/food_schedule.sql" ),
	LEVODOPA_SCHEDULE( "datasets/schedules/levodopa_schedules.sql", LEVODOPA_MEDICINE ),
	ROUTINE_TYPE( "datasets/clinical.base/routine_type.sql" ),
	ROUTINE_SCHEDULE( "datasets/schedules/routine_schedule.sql", ROUTINE_TYPE ),
	LEVODOPA_EVENT( "datasets/events/levodopa_events.sql", LEVODOPA_MEDICINE ),
	RESUME_DASHBOARD( "datasets/analytics/doctor-dashboard.sql", LEVODOPA_MEDICINE, INJURY_TYPES, BODY_PARTS, ANIMIC_TYPE );

	private final String fileName;
	private final IDataSet[] deps;
	private final Deque<IDataSet> graph = new LinkedList<>();
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
