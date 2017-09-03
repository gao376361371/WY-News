package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.newreader09.R;

import java.util.ArrayList;

import bean.NewsBean;
import util.ImageUtil;

/**
 * Created by 17612 on 2017/7/23.
 */

public class HotNewsListAdapter extends MyBaseAdapter<NewsBean>{
    public HotNewsListAdapter(ArrayList<NewsBean> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if (convertView == null){
            //viewHolder 创建一个
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_news,null);
            viewHolder = new HotNewsViewHolder();
            viewHolder.mIvHot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.mTvReply = (TextView) convertView.findViewById(R.id.tv_reply);
            viewHolder.mTvSource = (TextView) convertView.findViewById(R.id.tv_source);
            viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mTvTop = (TextView) convertView.findViewById(R.id.tv_top);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HotNewsViewHolder) convertView.getTag();
        }
        //给ViewHolder的控件设置数据展示
        changeUI(viewHolder,mData.get(position));
        return convertView;
    }

    private void changeUI(HotNewsViewHolder viewHolder, NewsBean newsBean) {
        viewHolder.mTvTitle.setText(newsBean.getTitle());
        viewHolder.mTvSource.setText(newsBean.getSource());

        if ("S".equals(newsBean.getInterest())){
            viewHolder.mTvTitle.setVisibility(View.VISIBLE);
            viewHolder.mTvReply.setVisibility(View.GONE);
        } else {
            viewHolder.mTvTop.setVisibility(View.GONE);
            int replyCount = Integer.valueOf(newsBean.getReplyCount());
            if (replyCount == 0){
                viewHolder.mTvReply.setVisibility(View.GONE);
            }else {
                viewHolder.mTvReply.setVisibility(View.VISIBLE);
                viewHolder.mTvReply.setText(replyCount+"跟贴");
            }
        }
        //展示
        ImageUtil.getSingleton().showImage(newsBean.getImg(),viewHolder.mIvHot);
    }
    //添加数据并刷新
    public void addData(ArrayList<NewsBean> t1348647909107){
        mData.addAll(t1348647909107);
        notifyDataSetChanged();
    }
    //下拉刷新时,不要直接add,应该将先前的数据清除掉,再add
    public void updateData(ArrayList<NewsBean> t1348647909107){
        mData.clear();
        mData.addAll(t1348647909107);
        notifyDataSetChanged();
    }

    public class HotNewsViewHolder{
        //控件
        ImageView mIvHot;
        TextView mTvTitle;
        TextView mTvSource;
        TextView mTvReply;
        TextView mTvTop;
    }
}
