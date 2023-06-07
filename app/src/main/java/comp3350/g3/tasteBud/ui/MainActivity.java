package comp3350.g3.tasteBud.ui;

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

public class MainActivity extends FragmentActivity {
    LinearLayout llSearchButton, llCreateButton, llChefButton;
    TextView tvSearchButton, tvCreateButton, tvChefButton;
    ImageView ivSearchButton, ivCreateButton, ivChefButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect all types of button with the ButtonId in "layout" package
        setContentView(R.layout.activity_main);
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
}
