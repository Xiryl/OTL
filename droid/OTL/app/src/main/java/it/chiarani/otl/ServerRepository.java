package it.chiarani.otl;

public class ServerRepository {
    private String message;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
