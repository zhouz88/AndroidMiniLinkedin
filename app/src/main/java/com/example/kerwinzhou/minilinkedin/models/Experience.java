package com.example.kerwinzhou.minilinkedin.models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by kerwinzhou on 11/21/17.
 */

public class Experience implements Parcelable{

    public String name;

    public String content;

    public String id;

    protected Experience(Parcel in) {
        name = in.readString();
        content = in.readString();
        id = in.readString();
    }

    public Experience() {
        this.id = UUID.randomUUID().toString();
    }

    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
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