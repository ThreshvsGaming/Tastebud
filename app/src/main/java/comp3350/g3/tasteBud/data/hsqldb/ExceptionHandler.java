package comp3350.g3.tasteBud.data.hsqldb;

public class ExceptionHandler extends RuntimeException {
    private Exception cause;

    public ExceptionHandler(Exception cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return "An exception occurred: " + cause.getMessage();
    }

    public Exception getCauseException() {
        return cause;
    }
}
