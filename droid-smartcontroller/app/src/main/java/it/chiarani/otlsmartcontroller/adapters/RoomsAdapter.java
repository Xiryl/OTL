package it.chiarani.otlsmartcontroller.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
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
import it.chiarani.otlsmartcontroller.views.RoomDetailActivity;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private User mUser;
    private Context context;

    public RoomsAdapter(User mUser) {
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        this.context = parent.getContext();

        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout rl;
        TextView title, qtaDevice;
        ImageView img;

        ViewHolder(View v) {
            super(v);
            rl          = v.findViewById(R.id.item_room_rl);
            title       = v.findViewById(R.id.item_room_title);
            img         = v.findViewById(R.id.item_room_img);
            qtaDevice   = v.findViewById(R.id.item_room_qta_device);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), RoomDetailActivity.class);


            String transitionName = "prova";
            Pair<View, String> p1 =  Pair.create((View)img, "prova");
            Pair<View, String> p2 =  Pair.create((View)rl, "prova1");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,p1,p2);


            v.getContext().startActivity(i, options.toBundle());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
        holder.img.setImageDrawable(context.getResources().getDrawable(RoomHelper.getResource(RoomTypes.KITCHEN)));
        holder.title.setText(mUser.otlRoomsList.get(position).roomName);
        holder.qtaDevice.setText(mUser.otlRoomsList.get(position).devices.size() + "Device");
    }

    @Override
    public int getItemCount() {
        return mUser.otlRoomsList.size();
    }
}
