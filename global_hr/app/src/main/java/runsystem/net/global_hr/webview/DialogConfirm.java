package runsystem.net.global_hr.webview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.Toast;

import runsystem.net.global_hr.BaseActivity;
import runsystem.net.global_hr.LoginActivity;
import runsystem.net.global_hr.api.APILogin;
import runsystem.net.global_hr.api.APILogout;

/**
 * Created by LuanDang on 12/31/2015.
 */
public class DialogConfirm extends DialogFragment {

    private Context context;

    public DialogConfirm(Context context) {
        this.context = context;
    }

    private void gotoLogin(){
        LoginActivity.startActivity(this.context);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** Defining an event listener for the Yes button click */
        OnClickListener positiveClick = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                APILogout api = new APILogout();
                api.token = ((BaseActivity)getActivity()).getRegistrationToken();
                api.startAPI(context);
                gotoLogin();
                ((BaseActivity)getActivity()).removeProfile();
                ((BaseActivity)getActivity()).clearAllActivityTop(true);
            }
        };

        /** Defining an event listener for the No button click */
        OnClickListener negativeClick = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        /** Creating a builder object for the AlertDialog*/
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        /**  Setting the message */
        b.setMessage("Do you want to log out?");

        /** Setting the Negative button */
        b.setNegativeButton("Cancel", negativeClick);

        /** Setting the Positive button */
        b.setPositiveButton("OK", positiveClick);

        /** Setting a title for the dialog */
        b.setTitle("Confirmation");

        /** Creating the AlertDialog , from the builder object */
        Dialog d = b.create();

        /** Returning the alert window */
        return d;
    }
}
