package hu.transferwise.phonebookapi.util.hashedid.exception;

public class HashedIdException extends RuntimeException {

    public HashedIdException(String message) {
        super(message);
    }

    public HashedIdException(String message, Throwable t) {
        super(message, t);
    }

}
