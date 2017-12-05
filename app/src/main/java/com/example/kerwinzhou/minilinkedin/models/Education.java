
package com.example.kerwinzhou.minilinkedin.models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by kerwinzhou on 11/21/17.
 */

public class Education implements Parcelable {
    public String id;

    public String school;

    public String major;

    public Date startDate;

    public Date endDate;

    public List<String> courses;

    public Education() {
        this.id = UUID.randomUUID().toString();
    }

    protected Education(Parcel in) {
        id = in.readString();
        school = in.readString();
        major = in.readString();
        long tmpDate = in.readLong();
        startDate = (tmpDate == -1 ? null : new Date(tmpDate));
        long sDate = in.readLong();
        endDate = (sDate == -1 ? null : new Date(sDate));
        courses = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(school);
        parcel.writeString(major);
        parcel.writeLong(startDate != null ? startDate.getTime() : -1);
        parcel.writeLong(endDate != null ? endDate.getTime() : -1);
        parcel.writeStringList(courses);
    }
}
