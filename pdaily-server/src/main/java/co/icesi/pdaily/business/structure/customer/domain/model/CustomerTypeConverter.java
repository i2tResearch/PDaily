package co.icesi.pdaily.business.structure.customer.domain.model;

import javax.persistence.Converter;

import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

@Converter
public class CustomerTypeConverter extends BaseDBEnumConverter<CustomerType> {
}
