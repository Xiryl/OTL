package it.chiarani.otlsmartcontroller.db.persistence.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OTLRoomsEntity {
    @PrimaryKey
    public int idRoom;

    public String roomName;

    public String roomTags;

    public String roomLocation;

    public boolean isAvailable;

    public String roomToken;

}
