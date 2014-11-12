package com.jch.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jch.lib.R;

/**
 * 上拉快速加载.
 * Created by ACER on 2014/11/3.
 */
public class RefreshMoreListView extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private View mFootView = null;

    private LoadMoreListener mLoadMoreListener;

    private RefreshListner refreshListner;

    private boolean loadingMore = false;

    public int getmPageSize() {
        return mPageSize;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    /**
     * 每次加载的数量.
     */
    private int mPageSize;

    public void setmCanLoadMoreAble(boolean canLoadMoreAble) {
        this.mCanLoadMoreAble = canLoadMoreAble;
        if (canLoadMoreAble)
            mFootView.setVisibility(View.VISIBLE);
        else
            mFootView.setVisibility(View.GONE);
    }

    /**
     * 是否能加载更多. *
     */
    private boolean mCanLoadMoreAble = true;


    /**
     * 加载更多。
     */
    public interface LoadMoreListener {

        public void onLoadmore();
    }

    public interface XOnItemClickListener {

        public void xonItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    private XOnItemClickListener itemClickListener;


    public void setXOnItemClickListener(XOnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCanLoadMoreAble)
            this.itemClickListener.xonItemClick(parent, view, position - 1, id);
        else
            this.onItemClick(parent, view, position, id);

    }

    /**
     * 数据刷新。
     */
    public interface RefreshListner {

        public void onrefresh();
    }

    public RefreshMoreListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        mFootView = LayoutInflater.from(context).inflate(R.layout.refreshlist_footview, null);
        setOnScrollListener(this);
        addFooterView(mFootView);
        mFootView.setVisibility(View.GONE);
        setOnItemClickListener(this);
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public void setRefreshListner(RefreshListner listener) {
        this.refreshListner = listener;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (getLastVisiblePosition() == totalItemCount - 1 && mCanLoadMoreAble && !loadingMore) {       //上拉加载
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadmore();
                loadingMore = true;
            }
        }
    }


    /**
     * 数据加载完成。
     * 根据加载的数据是否大于pageSize判断是否换需要加载数据.
     * 也可以用于首次加载后调用，用于判断是否显示上拉加载。
     *
     * @param loadAcount
     */
    public void loadMoreOver(int loadAcount) {
        loadingMore = false;
        if (loadAcount < mPageSize) {
            setmCanLoadMoreAble(false);
            loadMoreCompleteMsg();
        } else {
            setmCanLoadMoreAble(true);
        }

        invalidate();
    }

    public void loadMoreCompleteMsg() {
        Toast.makeText(getContext(), getResources().getString(R.string.load_more_complete), Toast.LENGTH_SHORT).show();
    }


}
