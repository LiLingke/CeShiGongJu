package com.example.administrator.ceshigongju.adapter;

import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.bean.NoteBean;
import com.example.administrator.ceshigongju.bean.NoteEntity;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;

/**
 * Created by Administrator on 2018/3/19.
 */

public class NoteListAdapter extends BaseRecyclerAdapter<NoteEntity> {
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_note_list;
    }


    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        NoteEntity bean = getItem(position);
        holder.setText(R.id.item_time,bean.getTime());
        holder.setText(R.id.item_success,holder.getContext().getResources().getString(R.string.chenggongcishu,bean.getSCount()));
        holder.setText(R.id.item_fair,holder.getContext().getResources().getString(R.string.shibaicishu,bean.getFCount()));
    }
}
