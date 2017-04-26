package com.example.abdullahhashmi.passwordmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdullahhashmi on 4/26/17.
 */

@SuppressWarnings("serial")
public class Records implements Serializable {

    private String title;
    private String id;
    private List<RecordData> record_data = new ArrayList<>();

    public Records(String title, String id) {
        this.title = title;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getId() {
        return id;
    }

    public List<RecordData> getRecordData() {
        return record_data;
    }

    public void setRecordData(List<RecordData> record_data) {
        this.record_data = record_data;
    }
}