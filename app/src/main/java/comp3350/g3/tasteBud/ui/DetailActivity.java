package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.object.Recipe;

public class DetailActivity extends FragmentActivity {
    // This page is intent to show the detail of each recipe:
    // *Title
    // *Description
    // *Tags
    // *Ingredients
    TextView recipeTitle;
    TextView recipeDescription;
    TextView recipeTags;
    TextView recipeIngredients;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connect with Layout File:"detail_activity"
        setContentView(R.layout.detail_activity);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeTags = findViewById(R.id.recipeTags);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeDescription = findViewById(R.id.recipeDescription);

        //Create the instance of Recipe to get information of Each recipe
        Recipe bean = (Recipe) getIntent().getSerializableExtra("bean");
        recipeTitle.setText(bean.getName());
        recipeDescription.setText(bean.getDesc());
        String detailText="";
        //Use For loop to go over the length of Tags and
        for (int n=0;n<bean.getTags().length;n++){
            String tag=TextUtils.isEmpty(detailText)?"":""+detailText+",";
            detailText=tag+(bean.getTags()[n]);
        }

        recipeTags.setText(detailText);
        String Ing="";
        for (int n=0;n<bean.getIngredients().length;n++){
            String Ingredients=TextUtils.isEmpty(Ing)?"":""+Ing+",";
            Ing=Ingredients+(bean.getIngredients()[n]);
        }
        recipeIngredients.setText(Ing);
        findViewById(R.id.ivBack).setOnClickListener(v -> {
            finish();
        });
    }
    public static String byteArrayToString(byte[] bytes)
    {

        return new String(bytes);
    }
}
