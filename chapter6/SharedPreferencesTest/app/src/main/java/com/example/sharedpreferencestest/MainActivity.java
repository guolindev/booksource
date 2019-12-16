package com.example.sharedpreferencestest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.otaserver.android.dao.DeviceInfo;
import com.otaserver.android.util.DeviceInfoTextUtil;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static final String sharedPrefsName = "DeviceInfo";

//    DeviceInfoGsonUtil devInfoUtil = new DeviceInfoGsonUtil();
    DeviceInfoTextUtil devInfoUtil = new DeviceInfoTextUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //无权限记录设备id
        updateDeviceInfoNoNeedPermission();

        // 调用带权限检查的 取设备信息方法。
        //MainActivityPermissionsDispatcher.updateDeviceinfoWithPermissionCheck(this);

        Button saveData = (Button) findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //在按钮中执行才进行权限提示,这里是一个内部类调用。
                MainActivityPermissionsDispatcher.updateDeviceinfoWithPermissionCheck(MainActivity.this);
            }
        });

        Button printData = (Button) findViewById(R.id.restore_data);
        printData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences(sharedPrefsName, MODE_PRIVATE);
                DeviceInfo devInfo = devInfoUtil.load(pref);
                Log.d(TAG, "devInfo="+devInfo);
            }
        });
    }

    /**
     * 不需要运行时权限获取android属性的例子
     */
    void updateDeviceInfoNoNeedPermission() {

        /**
         * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
         * ANDROID_ID可以作为设备标识，但需要注意：
         * 1. 厂商定制系统的Bug：不同的设备可能会产生相同的ANDROID_ID：9774d56d682e549c。
         * 2. 厂商定制系统的Bug：有些设备返回的值为null。
         * 3. 设备差异：对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId() 返回相同的值。
         */
        String androidId = Settings.System.getString(this.getContentResolver(), Settings.System.ANDROID_ID);
        Log.d(TAG, "ANDROID_ID:" + androidId);

        String serial = android.os.Build.SERIAL;
        Log.d(TAG, "serial:" + serial);

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAndroidId(androidId);
        deviceInfo.setSerial(serial);

        SharedPreferences pref = getSharedPreferences(sharedPrefsName, MODE_PRIVATE);

        devInfoUtil.save(deviceInfo, pref);

        Log.d(TAG, "do updateDeviceInfoNoNeedPermission()");
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    @SuppressLint("MissingPermission")
    void updateDeviceinfo() {

        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        //已过时方法。
        //在api26已经放弃。官方建议使用getImei()和getMeid()这两个方法得到相应的值。
        String deviceId = tm.getDeviceId();

        //IMEI for GSM.
        // 提示：androidQ 是无法取得imei的。
        String imei_0 = tm.getImei(0);
        String imei_1 = tm.getImei(1);

        //MEID for CDMA.
        String meid = tm.getMeid();
        String te1 = tm.getLine1Number();
        String simSerialNumber = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setImei0(imei_0);
        deviceInfo.setImei1(imei_1);
        deviceInfo.setMeid(meid);
        deviceInfo.setTe1(te1);
        deviceInfo.setSimSerialNumber(simSerialNumber);
        deviceInfo.setImsi(imsi);
        deviceInfo.setDeviceId(deviceId);

        SharedPreferences pref = getSharedPreferences(sharedPrefsName, MODE_PRIVATE);
        devInfoUtil.save(deviceInfo, pref);
        Log.d(TAG, "do updateDeviceinfo()");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


}
