package com.example.administrator.ceshigongju.main;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * @author James
 * @date 2018/7/11
 * @describe:
 */

public class ChooseActivity extends BaseActivity {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_choose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @OnClick({R.id.btn_switch, R.id.btn_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_switch:
                startActivity(ModifyLockActivity.class);
                break;
            case R.id.btn_note:
                startActivity(SearchActivity.class);
                break;
        }
    }
}
