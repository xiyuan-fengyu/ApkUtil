package com.xiyuan.apk;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiyuan_fengyu on 2016/9/13.
 */
public class ApkInfo {

    public final String versionName;

    public final int versionCode;

    public final String packageName;

    public final String minSdk;

    public final Set<String> permissions;

    private ApkInfo(String minSdk, String versionName, int versionCode, String packageName, Set<String> permissions) {
        this.minSdk = minSdk;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.packageName = packageName;
        this.permissions = permissions;
    }

    public static class Builder {
        private String versionName;

        private int versionCode;

        private String packageName;

        private String minSdk;

        private Set<String> permissions;

        public Builder() {
            this.permissions = new HashSet<String>();
        }

        public ApkInfo build() {
            return new ApkInfo(minSdk, versionName, versionCode, packageName, permissions);
        }

        public void setMinSdk(String minSdk) {
            this.minSdk = minSdk;
        }


        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }


        public void setPermissions(Set<String> permissions) {
            this.permissions = permissions;
        }

        public void addPermissions(String permissions) {
            this.permissions.add(permissions);
        }


        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }


        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }
    }

}
