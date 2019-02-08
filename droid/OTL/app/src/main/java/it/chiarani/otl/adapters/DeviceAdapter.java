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

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    // #region private fields
    private Context         mContext;
    private ClickListener   listener;
    private List<Device>    devices;
    private int             oldPos;
    private int             first_entry;
    // #endregion

    public DeviceAdapter(Context mContext, ClickListener listener, List<Device> devices) {
        this.mContext   = mContext;
        this.listener   = listener;
        this.devices    = devices;
    }

    public interface ClickListener{
        void onClick(String status);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       TextView title, subtitle;
       ImageView img;
       RelativeLayout rl;

        public ViewHolder(View v) {
            super(v);
            rl          = v.findViewById(R.id.item_device_rl);
            title       = v.findViewById(R.id.item_device_title);
            subtitle    = v.findViewById(R.id.item_device_subtitle);
            img         = v.findViewById(R.id.item_device_img);

            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            int pos = this.getAdapterPosition();
            oldPos= pos;

            if(devices.get(pos).isState()) {
                devices.get(pos).setState(false);
                listener.onClick("OFF");
            }
            else
            {
                devices.get(pos).setState(true);
                listener.onClick("ON");
            }

            notifyDataSetChanged();
        }
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new ViewHolder(singleItemLayout);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(!devices.get(position).isState())
        {
            /* gray state */
            holder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.item_device_gradient_inactive));
            holder.title.setTextColor(mContext.getResources().getColor(R.color.item_device_title_disactive));
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_light_dark));

            //devices.get(position).setState(false);
            //listener.onClick("OFF");
        }
        else
        {
            /* yellow state */
            holder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.item_device_gradient_active));
            holder.title.setTextColor(mContext.getResources().getColor(R.color.item_device_title_active));
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_light));

            //devices.get(position).setState(true);
            //listener.onClick("ON");
        }

        holder.title.setText(devices.get(position).getName());
        holder.subtitle.setText(devices.get(position).getTopic());

    }


    @Override
    public int getItemCount() {
        return devices.size();
    }
}