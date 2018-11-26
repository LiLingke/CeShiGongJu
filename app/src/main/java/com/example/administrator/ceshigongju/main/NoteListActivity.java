package com.example.administrator.ceshigongju.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.adapter.NoteListAdapter;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;
import com.example.administrator.ceshigongju.bean.NoteBean;
import com.example.administrator.ceshigongju.bean.NoteEntity;
import com.example.administrator.ceshigongju.bean.NoteEntityDao;
import com.example.administrator.ceshigongju.util.GreenDaoHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import cn.dlc.commonlibrary.utils.glide.transform.BlurTransform;
import cn.dlc.commonlibrary.utils.rv_tool.RecyclerViewUtil;

public class NoteListActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.tv_fair)
    TextView tvFair;
    @BindView(R.id.rv)
    RecyclerView rv;

    private NoteListAdapter mAdapter;
    private NoteEntityDao mNoteDao;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_note_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebar.leftExit(this);
        mNoteDao = GreenDaoHelper.getDaoSession().getNoteEntityDao();
        initRecycle();
        initData();
    }

    public void initRecycle() {
        mAdapter = new NoteListAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        RecyclerViewUtil.setDefaultDivider(rv,layoutManager);
        rv.setAdapter(mAdapter);
    }


    private void initData(){

        List<NoteEntity> dataList = new ArrayList<>();
        dataList = mNoteDao.loadAll();
        mAdapter.setNewData(dataList);
        int sCount = 0;
        int fCount = 0;
        for (NoteEntity bean : dataList){
            sCount = sCount+bean.getSCount();
            fCount = fCount+bean.getFCount();
        }

        tvSuccess.setText(sCount+"");
        tvFair.setText(fCount+"");

    }
}
