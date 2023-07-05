package comp3350.g3.tasteBud.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.application.Main;
import comp3350.g3.tasteBud.data.Messages;

public class MainActivity extends FragmentActivity {
    LinearLayout llSearchButton, llCreateButton, llChefButton;
    TextView tvSearchButton, tvCreateButton, tvChefButton;
    ImageView ivSearchButton, ivCreateButton, ivChefButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect all types of button with the ButtonId in "layout" package
        setContentView(R.layout.activity_main);
        copyDatabaseToDevice();
        llSearchButton = findViewById(R.id.llSearchButton);
        llCreateButton = findViewById(R.id.llCreateButton);
        llChefButton = findViewById(R.id.llChefButton);
        tvSearchButton = findViewById(R.id.tvSearchButton);
        tvCreateButton = findViewById(R.id.tvCreateButton);
        tvChefButton = findViewById(R.id.tvChefButton);
        ivSearchButton = findViewById(R.id.ivSearchButton);
        ivCreateButton = findViewById(R.id.ivCreateButton);
        ivChefButton = findViewById(R.id.ivChefButton);

        addFragment(new SearchActivity());
        llSearchButton.setOnClickListener(v -> addFragment(new SearchActivity()));
        llCreateButton.setOnClickListener(v -> addFragment(new CreateActivity()));
        llChefButton.setOnClickListener(v -> addFragment(new ChefActivity()));
    }

    private void addFragment(Fragment fragment) {
        //Use addFragment Function to add and connect all necessary activities into MainActivity
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }
    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

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
