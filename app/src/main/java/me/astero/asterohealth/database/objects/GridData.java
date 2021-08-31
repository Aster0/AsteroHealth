package me.astero.asterohealth.database.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GridData {


    @PrimaryKey
    @NonNull
    public String name;

    @ColumnInfo(name = "metrics")
    public String metrics;

    @ColumnInfo(name = "value")
    public int value;

    @ColumnInfo(name = "duration")
    public int duration;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "image")
    public String image;


    @ColumnInfo(name = "icon")
    public String icon;

    public GridData(String name, String metrics, int value, int duration, String type, String image, String icon)
    {
        this.name = name;
        this.metrics = metrics;
        this.value = value;
        this.duration = duration;
        this.type = type;
        this.image = image;
        this.icon = icon;
    }

}
