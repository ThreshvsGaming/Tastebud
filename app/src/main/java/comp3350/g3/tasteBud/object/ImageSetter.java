package comp3350.g3.tasteBud.object;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;

import comp3350.g3.tasteBud.R;

public class ImageSetter {
    public void setImageResource(Context context, Recipe recipe, ImageView recipeImage) {
        //Handling image viewing
        String imagePath = recipe.getImageUri();

        if (imagePath != null && !imagePath.isEmpty()) {
            int imageId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());

            if (imageId != 0) {
                // This image is a default image located in the drawable folder.
                Glide.with(context).load(imageId).into(recipeImage);
            } else {
                // This image is a user-picked one located in the device file system.
                Glide.with(context).load(Uri.parse(imagePath)).into(recipeImage);
            }
        } else {
            // No image available, so a placeholder image.
            recipeImage.setImageResource(R.mipmap.recipedefault);
        }
    }
}
