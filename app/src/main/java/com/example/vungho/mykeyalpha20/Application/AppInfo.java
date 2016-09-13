package com.example.vungho.mykeyalpha20.Application;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by vungho on 24/03/2016.
 */
public class AppInfo {
    private String name;
    private String packageName;
    private boolean statut;

    public AppInfo(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.statut = false;
    }

    public AppInfo(String packageName) {
        this(null, packageName);
    }

    public AppInfo() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfo)) return false;

        AppInfo appInfo = (AppInfo) o;

        return packageName.equals(appInfo.packageName);

    }

    @Override
    public int hashCode() {
        return packageName.hashCode();
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
