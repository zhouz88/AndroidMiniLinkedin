package com.example.kerwinzhou.minilinkedin.models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by kerwinzhou on 11/21/17.
 */

public class Project implements Parcelable{

    public String name;

    public String content;

    public String id;

    protected Project(Parcel in) {
        name = in.readString();
        content = in.readString();
        id = in.readString();
    }

    public Project() {
        this.id = UUID.randomUUID().toString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(content);
        parcel.writeString(id);
    }
}