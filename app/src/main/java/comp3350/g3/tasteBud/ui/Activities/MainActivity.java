package comp3350.g3.tasteBud.ui.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.ui.HighCoupled.AndroidCopyDatabase;


public class MainActivity extends FragmentActivity {
    LinearLayout llSearchButton, llCreateButton, llRecommendButton;
    TextView tvSearchButton, tvCreateButton, tvChefButton;
    ImageView ivSearchButton, ivCreateButton, ivRecommendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect all types of button with the ButtonId in "layout" package
        setContentView(R.layout.activity_main);
        AndroidCopyDatabase d = new AndroidCopyDatabase(this);
        d.copyDatabaseToDevice();
        llSearchButton = findViewById(R.id.llSearchButton);
        llCreateButton = findViewById(R.id.llCreateButton);
        llRecommendButton = findViewById(R.id.llRecommendButton);
        tvSearchButton = findViewById(R.id.tvSearchButton);
        tvCreateButton = findViewById(R.id.tvCreateButton);
        tvChefButton = findViewById(R.id.tvChefButton);
        ivSearchButton = findViewById(R.id.ivSearchButton);
        ivCreateButton = findViewById(R.id.ivCreateButton);
        ivRecommendButton = findViewById(R.id.ivRecommendButton);

        addFragment(new SearchActivity());
        llSearchButton.setOnClickListener(v -> addFragment(new SearchActivity()));
        llCreateButton.setOnClickListener(v -> addFragment(new CreateActivity()));
        llRecommendButton.setOnClickListener(v -> addFragment(new RecommendActivity()));
    }

    private void addFragment(Fragment fragment) {
        //Use addFragment Function to add and connect all necessary activities into MainActivity
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
