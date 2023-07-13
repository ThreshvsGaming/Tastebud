package comp3350.g3.tasteBud.object;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class ValidationStatusSetter {
    public void setIllegalArgumentValidation(TextView validationStatus, Exception e) {
        validationStatus.setText("Recipe Editing Failed: " + e.getMessage());
        validationStatus.setVisibility(View.VISIBLE);
        validationStatus.setTextColor(Color.RED);
        new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 10000); //Show dialog for 10 seconds
    }

    public void setSystemErrorValidation(TextView validationStatus, Exception e) {
        validationStatus.setText("System Error: " + e.getMessage());
        validationStatus.setVisibility(View.VISIBLE);
        validationStatus.setTextColor(Color.RED);
        new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 10000); //Show dialog for 10 seconds
    }

    public void setAndroidSystemFailure(TextView validationStatus, String validationError) {
        validationStatus.setText(validationError);
        validationStatus.setVisibility(View.VISIBLE);
        validationStatus.setTextColor(Color.RED);
        new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 3000); //Show dialog for 3 seconds
    }

}
