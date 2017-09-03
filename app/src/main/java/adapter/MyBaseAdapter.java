package adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by 17612 on 2017/7/23.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    public ArrayList<T> mData;

    public MyBaseAdapter(ArrayList<T> data) {
        mData = data;
    }

    public ArrayList<T> getmData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
