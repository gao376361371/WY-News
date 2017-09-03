package widget.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by 17612 on 2017/7/24.
 */

public class BannerAdapter extends PagerAdapter {

    ArrayList<ImageView> mImageView;

    public BannerAdapter (ArrayList<ImageView> imageViews){
        mImageView = imageViews;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //通过余数,就不会越界
        position = position % mImageView.size();
        ImageView imageView = mImageView.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
