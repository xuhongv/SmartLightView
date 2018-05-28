package com.xuhong.smartlightview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private SmartLightView mSmartLightView;

    private TextView mTvLumin;
    private SeekBar mSbLumin;
    private TextView mTvWarmLight;
    private SeekBar mSbWarmLight;

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

        mTvLumin = (TextView) findViewById(R.id.tvLumin);
        mSbLumin = (SeekBar) findViewById(R.id.sbLumin);
        mTvWarmLight = (TextView) findViewById(R.id.tvWarmLight);
        mSbWarmLight = (SeekBar) findViewById(R.id.sbWarmLight);


        //亮度
        mSbLumin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSmartLightView.setProgressLumin(seekBar.getProgress());

            }
        });


        mSbWarmLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSmartLightView.setProgressTemper(seekBar.getProgress());

            }
        });



    }
}
