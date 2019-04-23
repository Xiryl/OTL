package it.chiarani.otlsmartcontroller.db.persistence.Entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import it.chiarani.otlsmartcontroller.helpers.RoomTypes;

@Entity
public class OTLRoomsEntity {
    @PrimaryKey
    public int idRoom;

    public String roomName;

    public String roomType;

    public String roomTags;

    public String roomLocation;

    public boolean isAvailable;

    public String roomToken;

    public List<OTLDeviceEntity> devices;

}
