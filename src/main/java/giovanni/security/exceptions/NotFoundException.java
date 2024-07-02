package giovanni.security.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Employee with id " + id + " not found!");
    }

    public NotFoundException(String message) {
        super(message);
    }

}

