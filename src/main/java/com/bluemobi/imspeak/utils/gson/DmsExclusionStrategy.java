package com.bluemobi.imspeak.utils.gson;

import com.bluemobi.yunyuhome.annotation.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by wangbin on 2014/12/1.
 */
public class DmsExclusionStrategy implements ExclusionStrategy {


    private String[] excludeFields;
    private Class[] excludeClasses;


    public DmsExclusionStrategy() {

    }



    public DmsExclusionStrategy(String[] excludeFields) {
        this.excludeFields = excludeFields;
    }


    public DmsExclusionStrategy(String[] excludeFields,
                                Class<?>[] excludeClasses) {
        this.excludeFields = excludeFields;
        this.excludeClasses = excludeClasses;
    }





    public boolean shouldSkipClass(Class<?> clazz) {
        if (this.excludeClasses == null) {
            return false;
        }

        for (Class<?> excludeClass : excludeClasses) {
            if (excludeClass.getName().equals(clazz.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        if (this.excludeFields == null) {
            return false;
        }

        if(f.getAnnotation(Exclude.class)!=null){
            return true;
        }

        for (String field : this.excludeFields) {
            if (field.equals(f.getName())) {
                return true;
            }
        }

        return false;
    }

    public final String[] getExcludeFields() {
        return excludeFields;
    }

    public final Class<?>[] getExcludeClasses() {
        return excludeClasses;
    }
}