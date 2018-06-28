package org.incoder.security;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * @author Jerry xu
 * @date 6/24/2018 9:56 PM.
 */
public class AboutDialogFragment extends DialogFragment {

    private DialogInterface.OnClickListener positiveCallback;

    private String title;

    private String message;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_security);
        builder.setMessage(message);
        builder.setPositiveButton("确定", positiveCallback);
        return builder.create();
    }

    public void show(String title, String message, DialogInterface.OnClickListener positiveCallback
            , FragmentManager fragmentManager) {
        this.title = title;
        this.message = message;
        this.positiveCallback = positiveCallback;
        show(fragmentManager, "DialogFragment");
    }

}
