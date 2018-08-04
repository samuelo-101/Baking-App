package bakingapp.udacity.com.bakingapp.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import bakingapp.udacity.com.bakingapp.R;

public class DialogUtil {

    private static AlertDialog alertDialog;

    public static void showAlertDialogMessage(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showGenericErrorMessage(Context context) {
        showAlertDialogMessage(context, context.getString(R.string.api_generic_error_title), context.getString(R.string.api_generic_error_message));
    }

    public static void showConnectionFailedErrorMessage(Context context) {
        showAlertDialogMessage(context, context.getString(R.string.api_connection_error_title), context.getString(R.string.api_connection_error_message));
    }
}
