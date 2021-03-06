package com.saboor.aros.app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saboor.aros.R;
import com.saboor.aros.app.listener.OnSwipeTouchListener;
import com.saboor.aros.app.models.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class RecyclerViewAdapterOrdersOfCook extends RecyclerView.Adapter<OrderViewHolder>
{
    Context mContext;
    ArrayList<Order> mData;
    Dialog myDialog;
    int mCookNo;

    public RecyclerViewAdapterOrdersOfCook(Context context, ArrayList<Order> data, int cookNo)
    {
        mContext = context;
        mData = data;
        mCookNo = cookNo;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.layout_orders_of_cook,parent,false);
        final OrderViewHolder vHolder = new OrderViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position)
    {

        holder.order_name.setText(mData.get(position).getItemName());
        holder.order_status.setText(mData.get(position).getStatus());
        String status=mData.get(position).getStatus();
        if(status.equals("Cooking"))
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffeee0"));
            holder.button.setVisibility(View.INVISIBLE);
        }
        else if(status.equals("Waiting"))
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ff6961"));
            holder.button.setVisibility(View.VISIBLE);
        }
        else if(status.equals("Ready"))
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#98fb98"));
            holder.button.setVisibility(View.INVISIBLE);
        }

        holder.order_row.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                removeItemFromList(position);
                return false;
            }
        });

        // Head Chef presses ">" button (waiting order)
        holder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseChef();
            }
        });

        // new ImageDownload(holder.project_image,mContext).execute(mData.get(position).getProjectImage());
    }

    @Override
    public int getItemCount()
    {
        if(mData!=null)
        {
            return mData.size();
        }
        else
        {
            return 0;
        }
    }

    public void chooseChef()
    {
        Intent intent = new Intent(mContext, CooksListActivity.class);
        mContext.startActivity(intent);
    }

    private void removeItemFromList(int position)
    {
        if(mData.get(position).getStatus().equals("Ready"))
        {
            Toast.makeText(mContext, "Order has been served", Toast.LENGTH_SHORT).show();
            mData.remove(position);
            RecyclerViewAdapterCook.adapters.get(mCookNo).notifyDataSetChanged();
        }

        else if(mData.get(position).getStatus().equals("Waiting"))
        {
            Toast.makeText(mContext, "Order Cancelled", Toast.LENGTH_SHORT).show();
            mData.remove(position);
            RecyclerViewAdapterCook.adapters.get(mCookNo).notifyDataSetChanged();
        }

        else if(mData.get(position).getStatus().equals("Cooking"))
        {
            Toast.makeText(mContext, "Order has been wasted", Toast.LENGTH_SHORT).show();
            mData.remove(position);
            RecyclerViewAdapterCook.adapters.get(mCookNo).notifyDataSetChanged();
        }
    }
}
