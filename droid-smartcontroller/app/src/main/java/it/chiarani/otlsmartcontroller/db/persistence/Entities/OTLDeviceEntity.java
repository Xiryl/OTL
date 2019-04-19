package it.chiarani.otlsmartcontroller.db.persistence.Entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Dao;

@Entity
public class OTLDeviceEntity {
    @PrimaryKey
    public int idDevice;

    public String deviceName;

    public String deviceDescription;

    public String deviceLocation;

    public int deviceStatus;

    public boolean isAvailable;

    public String deviceToken;
}
