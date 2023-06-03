package comp3350.g3.tasteBud.object;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import comp3350.g3.tasteBud.R;


public class homePageAdapter extends BaseQuickAdapter<FirstBean, BaseViewHolder> {
    //To convert the Bean into Application Layout


    public homePageAdapter() {

        super(R.layout.view_recipe);
    }

    @Override
    protected void convert(BaseViewHolder helper,FirstBean bean) {
        ImageView img=helper.getView(R.id.img);
        Glide.with(mContext).load(bean.img).into(img);
        helper.setText(R.id.tvTitle,bean.title);
    }

}