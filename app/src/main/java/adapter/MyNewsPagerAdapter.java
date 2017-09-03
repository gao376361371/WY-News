package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 17612 on 2017/7/21.
 */

public class MyNewsPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragments;
    String[] mStrings;

    public MyNewsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] stringArray) {
        super(fm);
        mFragments = fragments;
        mStrings = stringArray;
    }

    @Override
    public int getCount() {
        //判断是否为null
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings[position];
    }
}
