package org.abhishek.customerapi.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.abhishek.customerapi.model.ErrorMessage;

@Provider
public class CustomerAPIGenericExceptionMapper implements ExceptionMapper<CustomerAPIGenericException> {

	@Override
	public Response toResponse(CustomerAPIGenericException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "http://dummyweb.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.header("PoweredBy", "ABC-Tech")
				.entity(errorMessage)
				.build();
	}
}
