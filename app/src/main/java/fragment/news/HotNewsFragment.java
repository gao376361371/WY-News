package fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.m520it.newreader09.NewsDetailActivity;
import com.m520it.newreader09.R;

import java.io.IOException;
import java.util.ArrayList;

import adapter.HotNewsListAdapter;
import bean.BannerBean;
import bean.NewsBean;
import bean.NewsListBean;
import fragment.LogFragment;
import http.HttpHelper;
import http.StringCallback;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import util.Constant;
import util.JsonUtil;
import widget.banner.BannerView;

import static com.m520it.newreader09.NewsDetailActivity.NEWS_ID;

/**
 * Created by 17612 on 2017/7/21.
 */

public class HotNewsFragment extends LogFragment{
    //如果是在xml中使用fragment标签展示碎片Fragment的话,需要设置id或tag属性
    String text = getClass().getSimpleName();
    private NewsBean banner;
    private HotNewsListAdapter mHotNewsListAdapter;
    private ListView mLvNews;
    private PtrClassicFrameLayout mPtrFrame;
    private BannerView mBannerView;

    @Override
    public View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hot_news,container,false);
        mLvNews = (ListView) view.findViewById(R.id.lv_news);
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame);
        setListView();
        setRefresh();
        return view;
    }

    private void setRefresh() {
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                //请求最新的数据
                requestData(false);
            }
        });
    }

    private void setListView() {
        View footView = View.inflate(getContext(),R.layout.view_foot,null);
        mLvNews.addFooterView(footView);
        //点击监听
        mLvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),NewsDetailActivity.class);
                NewsBean item = (NewsBean) parent.getAdapter().getItem(position);
                intent.putExtra(NEWS_ID,item.getId());
                startActivity(intent);
            }
        });
        //设置监听
        mLvNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //判断滚动的状态 如果状态是停下来了,判断是否要加载更多数据
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //整个列表的最后一个条目的位置
                    int lastItemPosition = view.getAdapter().getCount() - 1;
                    //当前可见的最后一个条目
                    int lastVisiblePosition = view.getLastVisiblePosition();
                    if (lastItemPosition == lastVisiblePosition){
                        //加载更多,请求数据
                        Log.e(getClass().getSimpleName()+" xmg", "onScrollStateChanged: "+"请求数据");
                        requestData(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    @Override
    protected void initData() {
        requestData(false);
    }

    private int loadMoreCount = 0;//加载更多的次数

    private void requestData(final boolean isLoadMore) {
        String url = Constant.getHotNewsUrl(0,9);
        //加载更多,每次多加十条
        if (isLoadMore){
            loadMoreCount++;
            url = Constant.getHotNewsUrl(0+loadMoreCount*10,9+loadMoreCount*10);
        }
        HttpHelper.getInstence().requestForAsyncGET(url, new StringCallback() {
            @Override
            public void onFail(IOException e) {
                //将下拉刷新的头部收起来
                if (mPtrFrame.isRefreshing()){
                    mPtrFrame.refreshComplete();
                }
                String name = Thread.currentThread().getName();
                Log.e(getClass().getSimpleName() + " xmg", "onFail: " + name);
            }
            @Override
            public void onSuccess(String result) {
                //将下拉刷新的头部收起来
                if(mPtrFrame.isRefreshing()){
                    mPtrFrame.refreshComplete();
                }
                String name = Thread.currentThread().getName();
                Log.e(getClass().getSimpleName() + " xmg", "onSuccess: " + name);

                NewsListBean newsListBean = JsonUtil.parseJson(result,NewsListBean.class);
                ArrayList<NewsBean> t1348647909107 = newsListBean.getT1348647909107();

                Log.e(getClass().getSimpleName() + " xmg", "onSuccess: 在post中执行的线程名字: " + name);
                if (!isLoadMore){
                    //先拿走第一条轮播图的数据
                    NewsBean bannerData = t1348647909107.remove(0);
                    setBanner(bannerData);
                }
                if (mHotNewsListAdapter == null){
                    mHotNewsListAdapter = new HotNewsListAdapter(t1348647909107);
                    mLvNews.setAdapter(mHotNewsListAdapter);
                }else {
                    //不是第一次进来了,直接添加数据进来,并刷新
                    if (isLoadMore) {
                        //添加数据
                        mHotNewsListAdapter.addData(t1348647909107);
                    } else {
                        //下拉刷新时,不要直接add,应该将先前的数据清除掉,再add
                        mHotNewsListAdapter.updateData(t1348647909107);
                    }
                }
            }
        });
    }




        //设置轮播图
    public void setBanner(NewsBean bannerData) {
        ArrayList<BannerBean> ads= bannerData.getAds();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> picUrls = new ArrayList<>();

        for (int i = 0; i < ads.size();i++){
            BannerBean bannerBean = ads.get(i);
            String imgsrc = bannerBean.getImgsrc();
            String title = bannerBean.getTitle();
            titles.add(title);
            picUrls.add(imgsrc);
        }
        if (mBannerView == null){
            mBannerView = new BannerView(getContext());
            //设置数据到BannerView中
            mBannerView.setData(titles,picUrls);
            mLvNews.addHeaderView(mBannerView);
        }else {
            //不是第一次进来,直接对bannerView中数据来进行刷新
            mBannerView.updateData(titles,picUrls);
        }
    }
}
