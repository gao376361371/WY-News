package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 17612 on 2017/7/20.
 */

public abstract class LogFragment extends Fragment {
    //如果是在xml中使用fragment标签展示碎片Fragment的话,需要设置id或tag属性
    String text = getClass().getSimpleName();


    //    //初始化时想传值过来时,不要直接使用构造器来传,会报错
    //    public MyFragment(String text) {
    //        this.text = text;
    //    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName()+" xmg", " onCreateView: ");
        /*Bundle arguments = getArguments();
        if(arguments!=null){
            text = arguments.getString("title");
        }
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(30);*/
        return onChildCreateView(inflater,container,savedInstanceState);
    }

    public abstract View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    //关联到宿主Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(getClass().getSimpleName()+" xmg", "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName()+" xmg", "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName()+" xmg", "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName()+" xmg", "onResume: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(getClass().getSimpleName()+" xmg", "onActivityCreated: ");
        initData();
    }

    protected abstract void initData();

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName()+" xmg", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName()+" xmg", "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName()+" xmg", "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getClass().getSimpleName()+" xmg", "onDestroyView: ");
    }

    //取消关联
    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getClass().getSimpleName()+" xmg", "onDetach: ");
    }
}
