package com.xuhong.smartlightview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private SmartLightView mSmartLightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSmartLightView = (SmartLightView) findViewById(R.id.SmartLightView);
        mSmartLightView.setSmartLightViewOnClickListener(new SmartLightView.SmartLightViewOnClickListener() {
            @Override
            public void lightStatusCallBack(boolean isOpen) {
                Log.e("xuhong", "点击事件回调，当前开灯状态：" + isOpen);
            }
        });
    }
}
