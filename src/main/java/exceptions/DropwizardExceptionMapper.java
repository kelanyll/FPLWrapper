package exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DropwizardExceptionMapper implements ExceptionMapper<DropwizardException> {
	public Response toResponse(DropwizardException exception) {
		return Response.status(Response.Status.BAD_REQUEST)
			.entity(exception.getMessage())
			.type(MediaType.TEXT_PLAIN)
			.build();
	}
}
