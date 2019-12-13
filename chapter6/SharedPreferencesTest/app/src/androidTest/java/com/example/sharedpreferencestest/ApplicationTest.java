package com.example.sharedpreferencestest;

import android.content.Context;
import android.text.TextUtils;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();
        assertEquals("com.example.sharedpreferencestest", appContext.getPackageName());
    }

    @Test
    public void deviceInfoReflect() throws Exception {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAndroidId("androidId");
        deviceInfo.setSerial("serial");

        Class cls = deviceInfo.getClass();
        Field[] fields = cls.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            System.out.println("属性名:" + field.getName() + " 属性值:" + field.get(deviceInfo));

            // meid字段没有赋值，应该是null。
            if (field.getName().equalsIgnoreCase("meid")) {
                assertTrue(TextUtils.isEmpty((String) field.get(deviceInfo)));
            }

            // serial字段赋值，应该不为空。
            if (field.getName().equalsIgnoreCase("serial")) {
                assertFalse(TextUtils.isEmpty((String) field.get(deviceInfo)));
            }
        }
    }



}