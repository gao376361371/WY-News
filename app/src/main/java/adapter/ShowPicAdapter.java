package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import java.util.ArrayList;

/**
 * Created by 17612 on 2017/7/27.
 */

public class ShowPicAdapter extends PagerAdapter{
    ArrayList<ImageView> mImageViews;

    public ShowPicAdapter(ArrayList<ImageView> imageViews) {
        mImageViews = imageViews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImageViews.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
