package fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fragment.LogFragment;

/**
 * Created by 17612 on 2017/7/21.
 */

public class EmptyFragment extends LogFragment{

    //如果是在xml中使用fragment标签展示碎片Fragment的话,需要设置id或tag属性
    String text = getClass().getSimpleName();

    @Override
    public View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(30);
        textView.setText(text);
        return textView;
    }

    @Override
    protected void initData() {

    }
}
