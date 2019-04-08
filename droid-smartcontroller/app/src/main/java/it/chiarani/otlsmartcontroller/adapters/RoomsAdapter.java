package it.chiarani.otlsmartcontroller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.helpers.RoomHelper;
import it.chiarani.otlsmartcontroller.helpers.RoomTypes;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private User userProfileEntity;
    private Context context;

    public RoomsAdapter(User userProfileEntity) {
        this.userProfileEntity = userProfileEntity;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        this.context = parent.getContext();

        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl;
        TextView title;
        ImageView img;

        public ViewHolder(View v) {
            super(v);
            rl      = v.findViewById(R.id.item_room_rl);
            title   = v.findViewById(R.id.item_room_title);
            img     = v.findViewById(R.id.item_room_img);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
        holder.img.setImageDrawable(context.getResources().getDrawable(RoomHelper.getResource(RoomTypes.KITCHEN)));
        //holder.title
    }

    @Override
    public int getItemCount() {
        return userProfileEntity.otlRoomsList.size();
    }
}
