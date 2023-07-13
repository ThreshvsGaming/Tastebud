package comp3350.g3.tasteBud.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.ui.DeleteInteraction;

public class Messages {
    public static void warning(Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.warning));
        alertDialog.setMessage(message);

        alertDialog.show();
    }

    public static void buildWarningDeleteDialogue(Context context, String message, DeleteInteraction deleteInteraction) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    deleteInteraction.delete();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
