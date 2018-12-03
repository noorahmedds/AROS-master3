package com.saboor.aros.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.saboor.aros.R;
import com.saboor.aros.app.MainActivity;
import com.saboor.aros.app.models.Chef;

import java.util.ArrayList;

public class RecyclerViewAdapterAttendanceList extends RecyclerView.Adapter<RecyclerViewAdapterAttendanceList.ViewHolder>
{
    private ArrayList<Chef> mChefs = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterAttendanceList(Context context, ArrayList<Chef> chefs)
    {
        this.mChefs = chefs;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.presentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mChefs.get(position).setPresent(true);
                else
                    mChefs.get(position).setPresent(false);
            }
        });

        holder.cookName.setText(mChefs.get(position).getName());
        if(mChefs.get(position).isPresent())
            holder.presentSwitch.setChecked(true);
        else
            holder.presentSwitch.setChecked(false);
    }

    public ArrayList<Chef> getmChefs(){
        return new ArrayList<>(mChefs);
    }

    @Override
    public int getItemCount()
    {
        return mChefs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cookName;
        SwitchCompat presentSwitch;

        public ViewHolder(View itemView)
        {
            super(itemView);
            cookName = itemView.findViewById(R.id.cookName);
            presentSwitch = itemView.findViewById(R.id.present_switch);

        }
    }
}