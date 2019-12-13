package com.example.sharedpreferencestest;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * 使用反射遍历javabean的示例：
     *
     * @throws Exception
     */
    @Test
    public void deviceInfoReflect() throws Exception {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAndroidId("androidId");
        deviceInfo.setSerial("serial");

        Class cls = deviceInfo.getClass();
        Field[] fields = cls.getDeclaredFields();

        //拿到函数成员
        Method[] methods=cls.getMethods();

        for(Method m : methods) {
            System.out.println("该类的方法有:"+m.getName());
        }

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            System.out.println("属性名:" + field.getName() + " 属性值:" + field.get(deviceInfo));
            if (field.getName().equalsIgnoreCase("androidId")) {
                assertNotNull(field.get(deviceInfo));
            }

        }




    }


}