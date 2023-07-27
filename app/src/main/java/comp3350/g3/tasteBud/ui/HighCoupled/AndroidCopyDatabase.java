package comp3350.g3.tasteBud.ui.HighCoupled;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.g3.tasteBud.application.Main;
import comp3350.g3.tasteBud.ui.Displays.Messages;

/*
Due to android studio's dependencies being the only way to get our database and copy it to a device's local files, we have
to make a class that is highly coupled to Android Studio and we cannot put the methods below to a Service/configuration/helper layer
Because they require methods from the activity classes in the UI Layer
Reasons we can't put this into a service/configuration/helper layer
Android Studio has they own pathing that only Android Studio Dependencies can read so we cannot get the file location of the database of the local file we created to
Store the Database in the device.
 */
public class AndroidCopyDatabase {

    private Activity owner;

    public AndroidCopyDatabase(Activity activity) {
        this.owner = activity;
    }

    public void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        Context context = owner.getApplicationContext();
        AssetManager assetManager = owner.getAssets();

        String[] assetNames;
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Messages.warning(owner, "Unable to access application data: " + ioe.getMessage());
        }
    }

    private void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = owner.getAssets();
        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
