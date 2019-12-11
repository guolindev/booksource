package com.example.sharedpreferencestest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 调用带权限检查的 取设备信息方法。
        MainActivityPermissionsDispatcher.getDeviceIdWithPermissionCheck(this);

        //读取手机设备ID并存储SharedPreferences。
        Button saveData = (Button) findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                editor.apply();
            }
        });
        Button restoreData = (Button) findViewById(R.id.restore_data);
        restoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("name", "");
                int age = pref.getInt("age", 0);
                boolean married = pref.getBoolean("married", false);
                Log.d("MainActivity", "name is " + name);
                Log.d("MainActivity", "age is " + age);
                Log.d("MainActivity", "married is " + married);
            }
        });
    }


    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    @SuppressLint("MissingPermission")
    void getDeviceId() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        //IMEI for GSM
        String imei_0 = tm.getImei(0);
        String imei_1 = tm.getImei(1);
        //MEID for CDMA.
        String meid = tm.getMeid();
        String te1 = tm.getLine1Number();
        String simSerialNumber = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();

        Log.d(TAG, "imei_0:" + imei_0);
        Log.d(TAG, "imei_1:" + imei_1);
        Log.d(TAG, "meid:" + meid);
        Log.d(TAG, "te1:" + te1);
        Log.d(TAG, "simSerialNumber:" + simSerialNumber);
        Log.d(TAG, "imsi:" + imsi);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
