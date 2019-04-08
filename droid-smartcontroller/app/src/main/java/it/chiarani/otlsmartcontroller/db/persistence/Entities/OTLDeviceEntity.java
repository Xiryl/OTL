package it.chiarani.otlsmartcontroller.db.persistence.Entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OTLDeviceEntity {
    @PrimaryKey
    public int idDevice;

    public String deviceName;

    public String deviceDescription;

    public String deviceLocation;

    public boolean deviceStatus;

    public boolean isAvailable;

    public String deviceToken;

    public List<OTLDeviceEntity> deviceList;

}
