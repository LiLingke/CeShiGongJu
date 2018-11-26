package com.example.administrator.ceshigongju.adapter;

import android.bluetooth.BluetoothClass;
import android.view.View;
import android.widget.Button;

import com.clj.fastble.data.BleDevice;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.bean.NoteEntity;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;

/**
 * Created by Administrator on 2018/3/19.
 */

public class BleDevicesAdapter extends BaseRecyclerAdapter<BleDevice> {

    private int index = -1;
    private OnDeviceListener mListener;
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_devices_list;
    }


    @Override
    public void onBindViewHolder(CommonHolder holder, final int position) {
        final BleDevice bean = getItem(position);
        holder.setText(R.id.item_mac,bean.getMac());
        holder.setText(R.id.item_name,bean.getName());
        holder.setText(R.id.tv_distance,bean.getRssi()+"");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
    }

    public void setIndex(int position){
        this.index = position;
        notifyDataSetChanged();
    }
    public void setOnListener(OnDeviceListener listener){
        this.mListener = listener;
    }
    public interface OnDeviceListener{
        public void onClick(int position);

        public void onOpen(int position);

        public void onDiscon(int position);

    }
}
