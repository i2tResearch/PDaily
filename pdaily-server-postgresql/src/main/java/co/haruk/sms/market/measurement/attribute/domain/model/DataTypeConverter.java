package co.haruk.sms.market.measurement.attribute.domain.model;

import javax.persistence.Converter;

import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

@Converter
public class DataTypeConverter extends BaseDBEnumConverter<DataType> {
}
