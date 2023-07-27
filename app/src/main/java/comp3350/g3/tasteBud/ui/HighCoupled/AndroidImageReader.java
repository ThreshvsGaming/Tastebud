package comp3350.g3.tasteBud.ui.HighCoupled;

import android.app.Activity;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/*
Due to android dependencies being the only way for us to grab said selected image, we cannot
Create a class in the Persistence/Data Layer because we require methods that exist in the activities classes.
Therefore we have to create a high coupled class in order to get the selected image, get the local file that android studio can make,
and save the selected image in the local file
Reason why we can't put this in the persistence/data layer
Android Studio has their own pathing to read so we can't get the desired file we want
Android Studio has their own methods to read the selected file, but it won't give us the absolute path
 */

public class AndroidImageReader {

    private Activity owner;

    public AndroidImageReader(Activity owner) {
        this.owner = owner;
    }

    public Uri buildImageUri(Uri selectedImage) throws IOException {
        try {
            int fileNumber = 0; //for checking existence of existing images
            InputStream is = Objects.requireNonNull(owner).getContentResolver().openInputStream(selectedImage);
            File saveDirectory = Objects.requireNonNull(owner).getFilesDir();
            File saveFile;

            //Keep incrementing the image number so that we can have multiple images stored in the local app storage
            do {
                saveFile = new File(saveDirectory, "recipe_Image" + fileNumber++ + ".jpg");
            }
            while (saveFile.exists());

            // For moving image info to phone in-memory
            try (OutputStream os = new FileOutputStream(saveFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }

            // Close the InputStream
            is.close();
            // Get the Uri from the File that we just saved
            return Uri.fromFile(saveFile);
        } catch (Exception e) {
            throw e;
        }
    }

}
