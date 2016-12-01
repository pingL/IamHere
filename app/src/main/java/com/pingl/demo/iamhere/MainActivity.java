package com.pingl.demo.iamhere;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity implements View.OnClickListener {


    @BindView(R.id.clear_Button)    Button              mClear;
    @BindView(R.id.request_Button)  Button              mRequestButton;
    @BindView(R.id.mText)           TextView            mStartLoad;
    private                         LocationClient      mClient         = null;
    private                         BDLocationListener  myListener      = new MyLocationListener();
    private                         StringBuffer        sb              = new StringBuffer(256);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mClient = new LocationClient(getApplicationContext());
        mClient.registerLocationListener(myListener);
        setLocationOption();

    }

    @OnClick({R.id.request_Button,R.id.clear_Button})

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_Button:
                mClient.requestLocation();
                mClient.start();
                break;
            case R.id.clear_Button:
                if (mStartLoad != null){
                mClient.stop();
                mStartLoad.setText("");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.stop();
    }

    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
        option.disableCache(true);// 禁止启用缓存定位
        mClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null)
                return;
            sb.append("time: ");
            sb.append(bdLocation.getTime());
            sb.append("\nerror code: ");
            sb.append(bdLocation.getLocType());
            sb.append("\nnotional: ");
            sb.append(bdLocation.getCountry());
            sb.append("\nprovince: ");
            sb.append(bdLocation.getProvince());
            sb.append("\ncity: ");
            sb.append(bdLocation.getCity());
            mStartLoad.setText(sb.toString());
            mClient.stop();
            Log.d("TAG", "onReceiveLocation " + sb.toString());
        }
    }
}
