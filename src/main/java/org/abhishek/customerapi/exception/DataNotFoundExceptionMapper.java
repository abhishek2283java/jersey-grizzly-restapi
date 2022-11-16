package org.abhishek.customerapi.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.abhishek.customerapi.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "http://dummyweb.com");
		return Response.status(Status.NOT_FOUND)
				.header("PoweredBy", "X-SAFE Tech")
				.entity(errorMessage)
				.build();
	}
}
