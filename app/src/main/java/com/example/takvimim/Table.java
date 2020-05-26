package com.example.takvimim;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "reminder")
public class Table {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    String message;
    @ColumnInfo
    String  det;
    @ColumnInfo
    String  adr;
    @ColumnInfo
    String  remindDate;
    @ColumnInfo
    String  time;

    public String getMessage() {
        return message;
    }
    public String getTime() {
        return time;
    }

    public String getRemindDate() {
        return remindDate;
    }

    public String getDet() {
        return det;
    }

    public String getAdr() {
        return adr;
    }

    public int getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDet(String det) {
        this.det = det;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    //public String toString(){return "Etkinlik:"+message+"\n"+"Detay:"+det+"\n"+"Adres:"+adr+"\n"+"Saat:"+time;  }
    public String toString(){return "Etkinlik:"+message+"\n"+"Detay:"+det+"\n"+"Adres:"+adr+"\n"+"Tarih:"+remindDate+"\n"+"Saat:"+time;  }
}
