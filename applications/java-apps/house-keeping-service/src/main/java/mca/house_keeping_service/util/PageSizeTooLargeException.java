package mca.house_keeping_service.util;

public class PageSizeTooLargeException extends RuntimeException {
    private static final long serialVersionUID = -3582698555990832254L;

	public PageSizeTooLargeException(String message) {
        super(message);
    }
}

