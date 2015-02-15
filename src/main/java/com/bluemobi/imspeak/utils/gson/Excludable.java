package com.bluemobi.imspeak.utils.gson;

/**
 * Created by wangbin on 2014/12/1.
 */
public class Excludable {
    /**
     * 不需要序列化的域.
     */
    private transient String[] excludeFields;

    /**
     * 不需要序列化的类.
     */
    private transient Class[] excludeClasses;

    public void setExcludeFields(String[] excludeFields) {
        this.excludeFields = excludeFields;
    }

    public String[] getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeClasses(Class[] excludeClasses) {
        this.excludeClasses = excludeClasses;
    }

    public Class[] getExcludeClasses() {
        return excludeClasses;
    }
}