package cn.iotlearn.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CustomizeException(String message) {
        super(message);
        this.message = message;
    }
    public CustomizeException(CustomizeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
