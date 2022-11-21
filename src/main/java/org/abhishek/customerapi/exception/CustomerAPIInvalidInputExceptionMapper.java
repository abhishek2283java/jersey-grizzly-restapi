package org.abhishek.customerapi.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.abhishek.customerapi.model.ErrorMessage;

@Provider
public class CustomerAPIInvalidInputExceptionMapper implements ExceptionMapper<CustomerAPIInvalidInputException> {

	@Override
	public Response toResponse(CustomerAPIInvalidInputException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 401, "http://dummyweb.com");
		return Response.status(Status.BAD_REQUEST)
				.header("PoweredBy", "ABC-Tech")
				.entity(errorMessage)
				.build();
	}
}
