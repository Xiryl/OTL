package it.chiarani.otl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.otl.R;
import it.chiarani.otl.databinding.ItemDeviceBinding;
import it.chiarani.otl.model.Device;
import it.chiarani.otl.model.HouseRoom;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    // #region private fields
    private Context mContext;
    private ClickListener   listener;
    private List<HouseRoom> rooms;
    private int             oldPos;
    private int             first_entry;
    // #endregion

    public RoomsAdapter(Context mContext, ClickListener listener, List<HouseRoom> rooms) {
        this.mContext   = mContext;
        this.listener   = listener;
        this.rooms    = rooms;
    }

    public interface ClickListener{
        void onClick(String status);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView img;
        RelativeLayout rl;

        public ViewHolder(View v) {
            super(v);
            rl          = v.findViewById(R.id.item_room_rl);
            title       = v.findViewById(R.id.item_room_title);
            img         = v.findViewById(R.id.item_room_img);

            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            int pos = this.getAdapterPosition();
            oldPos= pos;
            notifyDataSetChanged();
        }
    }

    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(singleItemLayout);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
/*
        if(devices.get(position).isState())
        {

            holder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.item_device_gradient_inactive));
            holder.title.setTextColor(mContext.getResources().getColor(R.color.item_device_title_disactive));
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_light_dark));

            devices.get(position).setState(false);
            listener.onClick("OFF");
        }
        else
        {

            holder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.item_device_gradient_active));
            holder.title.setTextColor(mContext.getResources().getColor(R.color.item_device_title_active));
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_light));

            devices.get(position).setState(true);
            listener.onClick("ON");
        }*/

if(position == oldPos) {
    holder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.item_room_gradient_inactive));
}
else
{
    holder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.item_room_gradient_active));
}
if(rooms.get(position).getName().equals("Cucina")) {
    holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_kitchen));

        }

        holder.title.setText(rooms.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }
}