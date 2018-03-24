package com.example.administrator.pptrecording.index;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/12/20 0020.
 */
//extends RecyclerView.Adapter<RecyclerView.ViewHolder>
public class SimpleAdapter {
////    recycleView的适配器，要有ViewHolder
////    直接將從服務器得到的数据传送到这里，在ShowFragment另开一个线程保存得到的数据到SharePreferences中
//    private Context mContext;
////    将每条正文数据包装成Content，
//    private ArrayList<Content> mDatas;
////    private List<JSONObject> objects;
////    传给ViewHolder的整个布局
//    private View mView;
////    区分是原创还是转发//    帮助、约、we are one 也要区分，现在只考虑这个，先把转发这个功能取消
//    private static final int HELP = 0;
//    private static final int TOGETHER = 1;
//    private static final int ONE = 2;
////    SharedPreferences contentPre=null;
//    private User user;
//    public SimpleAdapter(Context context, ArrayList<Content> datas) {
////        Log.e("SimAdapter","正在执行SimpleAdapter---------------------------");
//        mContext=context;
//        this.mDatas=datas;
//        user= CacUtil.cacheLoad("selfMessage",mContext);
//    }
//    public SimpleAdapter(Context context, SharedPreferences contentPre) {
////        Log.e("SimAdapter","正在执行SimpleAdapter---------------------------");
//        mContext=context;
//        user= CacUtil.cacheLoad("selfMessage",mContext);
//    }
//    /**
//     *创建ViewHolder，viewType不一样，创建的ViewHolder也不一样
//     * @param parent
//     * @param viewType
//     * @return
//     */
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        如果是帮助
//        if (viewType == HELP) {
//            mView = LayoutInflater.from(mContext).inflate(R.layout.help_text, parent, false);
//            OriginViewHolder originViewHolder = new OriginViewHolder(mView);
////            originViewHolder.imageList.addOnScrollListener(new NewPauseOnScrollListener(com.nostra13.universalimageloader.core.ImageLoader.getInstance().getInstance(), true, true));
//            originViewHolder.imageList.addOnScrollListener(new NewPauseOnScrollListener(ImageLoader.getInstance(), true, true));
//            return originViewHolder;
//        } else if (viewType == TOGETHER) {
////            约
//            mView = LayoutInflater.from(mContext).inflate(R.layout.together_text, parent, false);
//            RetweetViewHolder retweetViewHolder = new RetweetViewHolder(mView);
//            retweetViewHolder.imageList.addOnScrollListener(new NewPauseOnScrollListener(ImageLoader.getInstance(),true,true));
//            return retweetViewHolder;
//        }else{
//            if (viewType==ONE){
////                we are one
//                mView = LayoutInflater.from(mContext).inflate(R.layout.home_original_pictext, parent, false);
//                OneViewHolder oneViewHolder = new OneViewHolder(mView);
//                oneViewHolder.imageList.addOnScrollListener(new NewPauseOnScrollListener(ImageLoader.getInstance(), true, true));
//                return oneViewHolder;
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * @param holder
//     * @param position
//     */
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if (holder instanceof OriginViewHolder) {
////帮助
//            ((OriginViewHolder) holder).titlebar_layout.setVisibility(View.VISIBLE);
//            ((OriginViewHolder) holder).splitLine.setVisibility(View.GONE);
//            ((OriginViewHolder) holder).favoritedelete.setVisibility(View.GONE);
//            FillContent.fillTitleBar(mContext, mDatas.get(mDatas.size()-position-1), ((OriginViewHolder) holder).profile_img, ((OriginViewHolder) holder).profile_name, ((OriginViewHolder) holder).profile_time, ((OriginViewHolder) holder).weibo_comefrom);
//            FillContent.fillWeiBoContent(mDatas.get(mDatas.size()-position-1).getContent(), mContext, ((OriginViewHolder) holder).weibo_content);
//            FillContent.fillWeiBoImgList(mDatas.get(mDatas.size()-position-1), mContext, ((OriginViewHolder) holder).imageList);
//            ((OriginViewHolder) holder).popover_arrow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(mContext,"点击向下箭头",Toast.LENGTH_SHORT).show();
//                }
//            });
////            帮助按钮的响应
//            ((OriginViewHolder) holder).provideButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    先和发布者沟通再点击该按钮，不要乱点
//                    //                    先和发布者沟通再点击该按钮，不要乱点
//                    if(!mDatas.get(position).getId().equals(user.getId())){
//                        ToastUtil.show(mContext,"已通知发布者，请等待对方同意", Toast.LENGTH_SHORT);
//                        Message message=new Message();
//                        message.setType(MessageType.HELP_MESSAGE);
//                        message.setSendTime(System.currentTimeMillis());
//                        message.setReceiver_id(mDatas.get(position).getId());
//                        message.setSender_id(user.getId());
//                        message.setContent(mDatas.get(position).getTag()+"@"+mDatas.get(position).getType());
//                        Intent intent2=new Intent("add.friend.message");
//                        intent2.putExtra("message",message);
//                        mContext.sendBroadcast(intent2);
//                        ((OriginViewHolder) holder).provideButton.setEnabled(false);
//                    }else{
//                        ToastUtil.show(mContext,"乖，别闹，自己不能帮自己！", Toast.LENGTH_SHORT);
//                    }
//                }
//            });
//            ((OriginViewHolder) holder).contactButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!mDatas.get(position).getId().equals(user.getId())){
//                        Intent intent=new Intent(mContext, ChatActivity.class);
//                        User user= UserUtil.getUser(mDatas.get(position).getId(),mContext);
//                        intent.putExtra("friends",user);
//                        mContext.startActivity(intent);
//                    }else{
//                        ToastUtil.show(mContext,"乖，别闹，这是你自己发布的", Toast.LENGTH_SHORT);
//                    }
//                }
//            });
//            ((OriginViewHolder) holder).origin_weibo_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, DescriActivity.class);
//                    mContext.startActivity(intent);
//                }
//            });
//
//        } else if (holder instanceof RetweetViewHolder) {
////约
//            ((RetweetViewHolder) holder).titlebar_layout.setVisibility(View.VISIBLE);
//            ((RetweetViewHolder) holder).splitLine.setVisibility(View.GONE);
//            ((RetweetViewHolder) holder).favoritedelete.setVisibility(View.GONE);
//            ((RetweetViewHolder) holder).stateView.setText("当前状态：让你先约。");
//            ((RetweetViewHolder) holder).provideButton.setText("我要约！！！");
//            FillContent.fillTitleBar(mContext, mDatas.get(mDatas.size()-position-1), ((RetweetViewHolder) holder).profile_img, ((RetweetViewHolder) holder).profile_name, ((RetweetViewHolder) holder).profile_time, ((RetweetViewHolder) holder).weibo_comefrom);
//            FillContent.fillWeiBoContent(mDatas.get(mDatas.size()-position-1).getContent(), mContext, ((RetweetViewHolder) holder).weibo_content);
//            FillContent.fillWeiBoImgList(mDatas.get(mDatas.size()-position-1), mContext, ((RetweetViewHolder) holder).imageList);
//            ((RetweetViewHolder) holder).popover_arrow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(mContext,"点击向下箭头",Toast.LENGTH_SHORT).show();
//                }
//            });
//            ((RetweetViewHolder) holder).origin_weibo_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, DescriActivity.class);
//                    mContext.startActivity(intent);
//                }
//            });
//
//            ((RetweetViewHolder) holder).provideButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    先和发布者沟通再点击该按钮，不要乱点
//                    if(!mDatas.get(position).getId().equals(user.getId())){
//                        ToastUtil.show(mContext,"已通知发布者，请等待对方同意", Toast.LENGTH_SHORT);
//                        Message message=new Message();
//                        message.setType(MessageType.TOGETHER_MESSAGE);
//                        message.setSendTime(System.currentTimeMillis());
//                        message.setReceiver_id(mDatas.get(position).getId());
//                        message.setSender_id(user.getId());
//                        message.setContent(mDatas.get(position).getTag()+"@"+mDatas.get(position).getType());
//                        Intent intent2=new Intent("add.friend.message");
//                        intent2.putExtra("message",message);
//                        mContext.sendBroadcast(intent2);
//                        ((OriginViewHolder) holder).provideButton.setEnabled(false);
//                    }else{
//                        ToastUtil.show(mContext,"乖，别闹，自己不能约自己", Toast.LENGTH_SHORT);
//                    }
//                }
//            });
//
//            ((RetweetViewHolder) holder).contactButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!mDatas.get(position).getId().equals(user.getId())){
//                        Intent intent=new Intent(mContext, ChatActivity.class);
//                        User user= UserUtil.getUser(mDatas.get(position).getId(),mContext);
//                        intent.putExtra("friends",user);
//                        mContext.startActivity(intent);
//                    }else{
//                        ToastUtil.show(mContext,"乖，别闹，这是你自己发布的", Toast.LENGTH_SHORT);
//                    }
//                }
//            });
//
//        }else {
//            ((OneViewHolder) holder).titlebar_layout.setVisibility(View.VISIBLE);
//            ((OneViewHolder) holder).bottombar_layout.setVisibility(View.VISIBLE);
//            ((OneViewHolder) holder).splitLine.setVisibility(View.GONE);
//            ((OneViewHolder) holder).favoritedelete.setVisibility(View.GONE);
//            FillContent.fillTitleBar(mContext, mDatas.get(mDatas.size()-position-1), ((OneViewHolder) holder).profile_img, ((OneViewHolder) holder).profile_name, ((OneViewHolder) holder).profile_time, ((OneViewHolder) holder).weibo_comefrom);
//            FillContent.fillWeiBoContent(mDatas.get(mDatas.size()-position-1).getContent(), mContext, ((OneViewHolder) holder).weibo_content);
//            FillContent.fillButtonBar(mContext, mDatas.get(mDatas.size()-position-1), ((OneViewHolder) holder).bottombar_retweet, ((OneViewHolder) holder).bottombar_comment, ((OneViewHolder) holder).bottombar_attitude, ((OneViewHolder) holder).redirect, ((OneViewHolder) holder).comment, ((OneViewHolder) holder).feedlike);
//            FillContent.fillWeiBoImgList(mDatas.get(mDatas.size()-position-1), mContext, ((OneViewHolder) holder).imageList);
//            ((OneViewHolder) holder).popover_arrow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext,"点击向下箭头", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            //微博背景的点击事件
//            ((OneViewHolder) holder).origin_weibo_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(mContext, OriginPicTextCommentDetailActivity.class);
//////                传递正文对象
////                    intent.putExtra("weiboitem", mDatas.get(mDatas.size()-position-1));
////                    mContext.startActivity(intent);
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mDatas != null) {
//            return mDatas.size();
//        } else {
//            return 0;
//        }
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return mDatas.get(position).getType();
//    }
//
//    public void setData(ArrayList<Content> objects) {
//        this.mDatas = objects;
//    }
//
//    /**
//     * 帮助
//     */
//    public static class OriginViewHolder extends RecyclerView.ViewHolder {
////        点赞图片
//        public ImageView praise;
//        public LinearLayout origin_weibo_layout;
//        public LinearLayout titlebar_layout;
//        public ImageView profile_img;
//        public ImageView popover_arrow;
//        public TextView profile_name;
//        public TextView profile_time;
//        public TextView weibo_comefrom;
//        public EmojiTextView weibo_content;
//        public RecyclerView imageList;
//        public TextView favoritedelete;
//        public ImageView splitLine;
//        private TextView stateView;
//        private Button provideButton;
//        private Button contactButton;
//
//        public OriginViewHolder(View v) {
//            super(v);
//            praise=(ImageView)v.findViewById(R.id.praise);
//            origin_weibo_layout = (LinearLayout) v.findViewById(R.id.origin_weibo_layout);
//            titlebar_layout = (LinearLayout) v.findViewById(R.id.titlebar_layout);
////            头像
//            profile_img = (ImageView) v.findViewById(R.id.profile_img);
////            向下箭头
//            popover_arrow = (ImageView) v.findViewById(R.id.popover_arrow);
//            profile_name = (TextView) v.findViewById(R.id.profile_name);
//            profile_time = (TextView) v.findViewById(R.id.profile_time);
//            weibo_content = (EmojiTextView) v.findViewById(R.id.weibo_Content);
//            weibo_comefrom = (TextView) v.findViewById(R.id.weiboComeFrom);
////            分割顶部用户信息和正文内容
//            splitLine = (ImageView) v.findViewById(R.id.splitLine);
//            imageList = (RecyclerView) v.findViewById(R.id.weibo_image);
//            favoritedelete = (TextView) v.findViewById(R.id.favorities_delete);
//            stateView=(TextView)v.findViewById(R.id.state);
//            provideButton=(Button)v.findViewById(R.id.provide);
//            contactButton=(Button)v.findViewById(R.id.contact);
//        }
//    }
//
//    /**
//     * 约
//     */
//    public static class RetweetViewHolder extends RecyclerView.ViewHolder {
////        点赞
//        public ImageView praise;
//        public LinearLayout origin_weibo_layout;
//        public LinearLayout titlebar_layout;
//        public ImageView profile_img;
//        public ImageView popover_arrow;
//        public TextView profile_name;
//        public TextView profile_time;
//        public TextView weibo_comefrom;
//        public EmojiTextView weibo_content;
//        public RecyclerView imageList;
//        public TextView favoritedelete;
//        public ImageView splitLine;
//        private TextView stateView;
//        private Button provideButton;
//        private Button contactButton;
//
//        public RetweetViewHolder(View v) {
//            super(v);
//            praise=(ImageView)v.findViewById(R.id.praise);
//            origin_weibo_layout = (LinearLayout) v.findViewById(R.id.origin_weibo_layout);
//            titlebar_layout = (LinearLayout) v.findViewById(R.id.titlebar_layout);
////            头像
//            profile_img = (ImageView) v.findViewById(R.id.profile_img);
////            向下箭头
//            popover_arrow = (ImageView) v.findViewById(R.id.popover_arrow);
//            profile_name = (TextView) v.findViewById(R.id.profile_name);
//            profile_time = (TextView) v.findViewById(R.id.profile_time);
//            weibo_content = (EmojiTextView) v.findViewById(R.id.weibo_Content);
//            weibo_comefrom = (TextView) v.findViewById(R.id.weiboComeFrom);
////            分割顶部用户信息和正文内容
//            splitLine = (ImageView) v.findViewById(R.id.splitLine);
//            imageList = (RecyclerView) v.findViewById(R.id.weibo_image);
//            favoritedelete = (TextView) v.findViewById(R.id.favorities_delete);
//            stateView=(TextView)v.findViewById(R.id.state);
//            provideButton=(Button)v.findViewById(R.id.provide);
//            contactButton=(Button)v.findViewById(R.id.contact);
//        }
//    }
//
//    /**
//     * we are one
//     */
//    public static class OneViewHolder extends RecyclerView.ViewHolder {
//        //        点赞图片
//        public ImageView praise;
//        public LinearLayout origin_weibo_layout;
//        public LinearLayout titlebar_layout;
//        public ImageView profile_img;
//        public ImageView popover_arrow;
//        public TextView profile_name;
//        public TextView profile_time;
//        public TextView weibo_comefrom;
//        public EmojiTextView weibo_content;
//        public TextView redirect;
//        public TextView comment;
//        public TextView feedlike;
//        public RecyclerView imageList;
//        public TextView favoritedelete;
//        public ImageView splitLine;
//        public LinearLayout bottombar_layout;
//        public LinearLayout bottombar_retweet;
//        public LinearLayout bottombar_comment;
//        public LinearLayout bottombar_attitude;
//
//        public OneViewHolder(View v) {
//            super(v);
//            praise=(ImageView)v.findViewById(R.id.praise);
//            origin_weibo_layout = (LinearLayout) v.findViewById(R.id.origin_weibo_layout);
//            titlebar_layout = (LinearLayout) v.findViewById(R.id.titlebar_layout);
////            头像
//            profile_img = (ImageView) v.findViewById(R.id.profile_img);
////            向下箭头
//            popover_arrow = (ImageView) v.findViewById(R.id.popover_arrow);
//            profile_name = (TextView) v.findViewById(R.id.profile_name);
//            profile_time = (TextView) v.findViewById(R.id.profile_time);
//            weibo_content = (EmojiTextView) v.findViewById(R.id.weibo_Content);
//            weibo_comefrom = (TextView) v.findViewById(R.id.weiboComeFrom);
//            redirect = (TextView) v.findViewById(R.id.redirect);
//            comment = (TextView) v.findViewById(R.id.comment);
//            feedlike = (TextView) v.findViewById(R.id.feedlike);
////            分割顶部用户信息和正文内容
//            splitLine = (ImageView) v.findViewById(R.id.splitLine);
//            imageList = (RecyclerView) v.findViewById(R.id.weibo_image);
//            favoritedelete = (TextView) v.findViewById(R.id.favorities_delete);
////            底部的转发、评论、赞
//            bottombar_layout = (LinearLayout) v.findViewById(R.id.bottombar_layout);
//            bottombar_retweet = (LinearLayout) v.findViewById(R.id.bottombar_retweet);
//            bottombar_comment = (LinearLayout) v.findViewById(R.id.bottombar_comment);
//            bottombar_attitude = (LinearLayout) v.findViewById(R.id.bottombar_attitude);
//        }
//    }
}
