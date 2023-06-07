package Stuff;

public class ErrorMessage {
    private final String _message;
    private final boolean _success;

    public ErrorMessage(String message,boolean success) {
        _message = message;
        _success = success;
    }


    public String get_message() {
        return _message;
    }


    public boolean is_success() {
        return _success;
    }
}
