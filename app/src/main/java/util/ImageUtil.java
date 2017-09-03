package util;

import android.widget.ImageView;

import com.m520it.newreader09.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by 17612 on 2017/7/23.
 */

public class ImageUtil {
    private DisplayImageOptions mOptions;
    private ImageUtil(){
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_default)
                .delayBeforeLoading(500)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private static volatile ImageUtil sInstance;

    public static ImageUtil getSingleton(){
        if (sInstance == null){
            synchronized (ImageUtil.class){
            if (sInstance == null){
                sInstance = new ImageUtil();
            }
            }
        }
        return sInstance;
    }
    public void showImage(String url, ImageView imageView){
        showImage(url,imageView,R.drawable.icon_default);
    }

    private int mLastResId = R.drawable.icon_default;

    private void showImage(String url, ImageView imageView, int resId) {
        if (mLastResId!=resId){
            mLastResId = resId;
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_default)
                    .delayBeforeLoading(500)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
        }
        ImageLoader.getInstance().displayImage(url,imageView,mOptions);
    }

}
