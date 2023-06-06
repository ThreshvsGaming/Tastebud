package comp3350.g3.tasteBud.object;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.sql.Array;
import java.util.Arrays;

import comp3350.g3.tasteBud.R;


public class HomePageAdapter extends BaseQuickAdapter<Recipe, BaseViewHolder> {
    //To convert the Bean into Application Layout


    public HomePageAdapter() {

        super(R.layout.view_recipe);
    }

    @Override
    protected void convert(BaseViewHolder helper, Recipe bean) {
        ImageView img=helper.getView(R.id.img);

        // Show the Title of Recipe in HomePage
        helper.setText(R.id.tvTitle,bean.getName());

        // Convert String[] into String
        String tags = Arrays.toString(bean.getTags());

        //Remove the '[' ,']' , ' ' in String[]
        tags = tags.replace("[","").replace("]","".replace(",",","));

        //Set the converted string as text
        helper.setText(R.id.num2, tags);

    }

}