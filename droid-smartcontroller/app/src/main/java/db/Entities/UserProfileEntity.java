package db.Entities;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserProfileEntity {
    @PrimaryKey
    public int idUser;

    public String userName;

    public String userPicture;

    public String userAccessToken;

    public List<OTLRoomsEntity> otlRoomsList;

}
