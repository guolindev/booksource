package com.example.sharedpreferencestest;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 将android设备信息以json格式保存在SharedPreference中。
 *
 * @author scott
 */
public class DevInfoGsonUtil {

    static DateTimeFormatter appInstallDateFomatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String TAG = "DevInfoGsonUtil";

    private static final String GSON_KEY = "gson";

    private Gson gson = new Gson();

    /**
     * 以gson为关键字保存在sharedPreference中。
     * @param deviceInfo
     * @param pref
     */
    void save(DeviceInfo deviceInfo, SharedPreferences pref) {

        SharedPreferences.Editor editor = pref.edit();
        //检查appInstallGuid是否存在
        boolean isContains = pref.contains("appInstallGuid");
        if (!isContains) {
            String uniqueID = UUID.randomUUID().toString();
            deviceInfo.setAppInstallGuid(uniqueID);
            LocalDateTime date = LocalDateTime.now();
            deviceInfo.setAppInstallDate(DevInfoGsonUtil.appInstallDateFomatter.format(date));
            Log.i(TAG, "create new  appInstallGuid!");
        }

        editor.putString(GSON_KEY, gson.toJson(deviceInfo));
        editor.apply();
    }


    /**
     * 用Gson转换为对象。
     *
     * @param pref
     * @return
     */
    DeviceInfo load(SharedPreferences pref) {
        String gsonInSharedP = pref.getString(GSON_KEY, " ");
        return gson.fromJson(gsonInSharedP, DeviceInfo.class);
    }


}
