package bean;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.newreader09.R;

import java.util.ArrayList;

import adapter.MyBaseAdapter;
import util.ImageUtil;

/**
 * Created by 17612 on 2017/7/27.
 */

public class NewsCommentAdapter extends MyBaseAdapter<NewsCommentBean> {
    public NewsCommentAdapter(ArrayList<NewsCommentBean> data1) {
        super(data1);
        //往集合最前面添加一个空的数据,把以前的评论数据顶下去
        mData.add(0,new NewsCommentBean());
    }
    public static final int TYPE_HOT = 0;//热门跟帖类型
    public static final int TYPE_COMMENT = 1;//一般评论类型

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            //认为是热门跟帖类型
            return TYPE_HOT;
        }else {
            //一般评论类型
            return TYPE_COMMENT;
        }
    }
    //getViewTypeCount返回值代表这个列表有多少中类型


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加对应判断,如果position知道是什么类型的评论,那对应的item也知道是什么类型
        if (getItemViewType(position)==TYPE_HOT){
            //当前view应该是要展示成热门的跟帖效果
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_title,null);
        }else {
            NewsCommentViewHolder viewHolder;
            if(convertView==null){
                convertView = View.inflate(parent.getContext(), R.layout.item,null);
                viewHolder = new NewsCommentViewHolder();
                viewHolder.ivUserIcon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
                viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
                viewHolder.tvUserInfo = (TextView) convertView.findViewById(R.id.tv_user_info);
                viewHolder.ivSupport = (ImageView) convertView.findViewById(R.id.iv_support);
                viewHolder.tvVoteCount = (TextView) convertView.findViewById(R.id.tv_vote_count);
                viewHolder.flSubFloor = (FrameLayout) convertView.findViewById(R.id.fl_sub_floor);
                viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (NewsCommentViewHolder) convertView.getTag();
            }
            //修改控件的展示
            NewsCommentBean commentBean = mData.get(position);
            changeUI(viewHolder,commentBean);
        }
        return convertView;
    }

    private void changeUI(NewsCommentViewHolder viewHolder, NewsCommentBean commentBean) {
        viewHolder.tvComment.setText(commentBean.getContent());
        viewHolder.tvUserName.setText(commentBean.getUser().getNickname());
        viewHolder.tvVoteCount.setText(commentBean.getVote()+"");
        //北京市 手机型号 时间
        String info = commentBean.getUser().getLocation()+" "
                +commentBean.getDeviceInfo().getDeviceName()+" "
                +commentBean.getCreateTime();
        viewHolder.tvUserInfo.setText(info);

        String url = commentBean.getUser().getAvatar();
        if(TextUtils.isEmpty(url)){
            //设置为默认头像
            viewHolder.ivUserIcon.setImageResource(R.drawable.icon_user_default);
        }else{
            ImageUtil.getSingleton().showImage(url,viewHolder.ivUserIcon);
        }
    }

    public class NewsCommentViewHolder{
        ImageView ivUserIcon;
        TextView tvUserName;
        TextView tvUserInfo;
        ImageView ivSupport;
        TextView tvVoteCount;
        FrameLayout flSubFloor;
        TextView tvComment;
    }
}
