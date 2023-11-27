package mca.house_keeping_service.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreconditionException extends RuntimeException {

    private static final long serialVersionUID = 1441775048607728999L;

	public PreconditionException() {
        super();
    }

    public PreconditionException(final String message) {
        super(message);
    }

}