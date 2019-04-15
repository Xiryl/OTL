package it.chiarani.otlsmartcontroller.db.persistence.Entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int idUser;

    public String userName;

    public String userPicture;

    public String userAccessToken;

    public List<OTLRoomsEntity> otlRoomsList;
}
