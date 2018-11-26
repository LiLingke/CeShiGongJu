package cn.dlc.commonlibrary.utils.rv_tool;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by John on 2017/11/12.
 */

public class EmptyRecyclerView extends RecyclerView.AdapterDataObserver {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private View mEmptyView;

    private EmptyRecyclerView(RecyclerView recyclerView, View emptyView) {
        mRecyclerView = recyclerView;
        mEmptyView = emptyView;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {

        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(this);
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(this);
        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        checkIfEmpty();
    }

    /**
     * @param recyclerView
     * @param emptyView
     * @return
     */
    public static EmptyRecyclerView bind(@NonNull RecyclerView recyclerView,
        @Nullable View emptyView) {
        EmptyRecyclerView emptyRecyclerView = new EmptyRecyclerView(recyclerView, emptyView);
        emptyRecyclerView.setAdapter(recyclerView.getAdapter());
        return emptyRecyclerView;
    }

    @Override
    public void onChanged() {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (mEmptyView != null && mAdapter != null) {

            boolean hideEmpty = mAdapter.getItemCount() > 0;
            mEmptyView.setVisibility(hideEmpty ? View.GONE : View.VISIBLE);
            //mRecyclerView.setVisibility(hideEmpty ? View.VISIBLE : View.GONE);
        }
    }
}
