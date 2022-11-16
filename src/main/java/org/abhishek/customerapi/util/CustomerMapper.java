package org.abhishek.customerapi.util;

import org.abhishek.customerapi.dto.CustomerDTO;
import org.abhishek.customerapi.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	
	@Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    CustomerDTO toDto(Customer customer);
    
    @Mapping(target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "active", constant = "true")
    Customer toEntity(CustomerDTO customerDTO);
}
