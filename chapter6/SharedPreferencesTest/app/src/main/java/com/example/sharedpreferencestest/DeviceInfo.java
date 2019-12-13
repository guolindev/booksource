package com.example.sharedpreferencestest;

public class DeviceInfo {

    //无需权限，一定存在
    private String appInstallGuid;
    private String appInstallDate;

    //无需权限，基本保证存在
    private String androidId;
    private String serial;

    //需要权限，
    private String imei0;
    private String imei1;
    private String meid;
    private String te1;
    private String simSerialNumber;
    private String imsi;

    //已过时
    private String deviceId;

    public String getAppInstallDate() {
        return appInstallDate;
    }

    public void setAppInstallDate(String appInstallDate) {
        this.appInstallDate = appInstallDate;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getAppInstallGuid() {
        return appInstallGuid;
    }

    public void setAppInstallGuid(String appInstallGuid) {
        this.appInstallGuid = appInstallGuid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getImei0() {
        return imei0;
    }

    public void setImei0(String imei0) {
        this.imei0 = imei0;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getTe1() {
        return te1;
    }

    public void setTe1(String te1) {
        this.te1 = te1;
    }

    public String getSimSerialNumber() {
        return simSerialNumber;
    }

    public void setSimSerialNumber(String simSerialNumber) {
        this.simSerialNumber = simSerialNumber;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return
     * @deprecated
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     * @deprecated
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "appInstallGuid='" + appInstallGuid + '\'' +
                ", appInstallDate='" + appInstallDate + '\'' +
                ", androidId='" + androidId + '\'' +
                ", serial='" + serial + '\'' +
                ", imei0='" + imei0 + '\'' +
                ", imei1='" + imei1 + '\'' +
                ", meid='" + meid + '\'' +
                ", te1='" + te1 + '\'' +
                ", simSerialNumber='" + simSerialNumber + '\'' +
                ", imsi='" + imsi + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

}
