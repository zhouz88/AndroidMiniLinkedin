package com.example.kerwinzhou.minilinkedin.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by kerwinzhou on 11/21/17.
 */

public class BasicInfo implements Parcelable {
    public String name;

    public String email;

    public String id;

    public BasicInfo() {
        this.id = UUID.randomUUID().toString();
    }

    protected BasicInfo(Parcel in) {
        name = in.readString();
        email = in.readString();
        id = in.readString();
    }

    public static final Creator<BasicInfo> CREATOR = new Creator<BasicInfo>() {
        @Override
        public BasicInfo createFromParcel(Parcel in) {
            return new BasicInfo(in);
        }

        @Override
        public BasicInfo[] newArray(int size) {
            return new BasicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(id);
    }
}