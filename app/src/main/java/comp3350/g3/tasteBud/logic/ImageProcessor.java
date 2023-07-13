package comp3350.g3.tasteBud.logic;

import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageProcessor {
    public Uri buildImageUri(File saveDirectory, InputStream is) throws IOException {
        try {
            int fileNumber = 0; //for checking existence of existing images
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
        }
        catch (Exception e){
            throw e;
        }
    }
}
