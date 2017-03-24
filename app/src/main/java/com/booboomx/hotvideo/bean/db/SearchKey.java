package com.booboomx.hotvideo.bean.db;

import io.realm.RealmObject;

/**
 * Created by booboomx on 17/3/24.
 */

public class SearchKey extends RealmObject {
    public String searchKey;
    public long insertTime;//插入时间

    public SearchKey() {
    }

    public SearchKey(String suggestion, long insertTime) {
        this.searchKey = suggestion;
        this.insertTime = insertTime;
    }

    public String getSearchKey() {
        return searchKey;
    }
}

