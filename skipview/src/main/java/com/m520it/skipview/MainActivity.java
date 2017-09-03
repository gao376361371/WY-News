package com.m520it.skipview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SkipView mSkipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSkipView = (SkipView) findViewById(R.id.skipview);
        mSkipView.setOnSkipListener(new SkipView.OnSkipListener() {
            @Override
            public void onSkip() {
                //跳转页面
                startActivity(new Intent(getApplicationContext(),TwoActivity.class));
            }
        });
    }
    public void click(View view){
        Toa.show(getApplicationContext(),"我成功的依赖了这个module");
    }
}
