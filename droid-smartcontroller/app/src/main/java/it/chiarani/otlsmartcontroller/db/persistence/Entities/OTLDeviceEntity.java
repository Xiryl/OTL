package it.chiarani.otlsmartcontroller.db.persistence.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import it.chiarani.otlsmartcontroller.helpers.DeviceTypes;

@Entity
public class OTLDeviceEntity {
    @PrimaryKey
    public int idDevice;

    public String deviceName;

    public String deviceType;

    public String deviceDescription;

    public String deviceLocation;

    public int deviceStatus;

    public boolean isAvailable;

    public String deviceToken;
}
