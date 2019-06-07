package it.chiarani.otlsmartcontroller.exceptions;

import android.content.Context;
import android.widget.Toast;

public class NoConnectionException extends Error {

    public NoConnectionException() {
        super();
    }

    public NoConnectionException(String msg) {
        super(msg);
    }

    public NoConnectionException(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

}
