package ouyj.hyena.com.infonews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ouyj.hyena.com.infonews.DetailActivity;
import ouyj.hyena.com.infonews.ImageActivity;
import ouyj.hyena.com.infonews.MainActivity;
import ouyj.hyena.com.infonews.R;
import ouyj.hyena.com.infonews.model.Ads;
import ouyj.hyena.com.infonews.model.NewsItem;
import ouyj.hyena.com.infonews.model.PhotoSet;
import ouyj.hyena.com.infonews.utils.NewsGlobal;
import ouyj.hyena.com.infonews.utils.RequestSingleton;
import ouyj.hyena.com.infonews.utils.URLParse;
import ouyj.hyena.com.infonews.utils.VolleySingle;
import ouyj.hyena.com.infonews.widget.RecyclerImgView;
import ouyj.hyena.com.infonews.widget.SwitchImage;

/**
 * 自定义新闻适配器类（Adapter是RecyclerView的一个内部抽象类）
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context content;
    private ArrayList<NewsItem> listItem;
    private LayoutInflater layoutInflater;

    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;

    //ImageViewClickListener imageViewListener = new ImageViewClickListener();

    /**
     *  构造方法（传入上下文和数据源）
     * @param context
     * @param listItem
     */
    public NewsAdapter(Context context, ArrayList<NewsItem> listItem) {
        this.content = context;
        this.listItem = listItem;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 新闻类型的枚举（0,1,2）
    */
    public enum NewsType {
        ITEM_TYPE_BANNER,
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }
    /**
     * 判断新闻项的类型（创建项布局时调用）
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(listItem.get(position).getOrder() == 1)
            return NewsType.ITEM_TYPE_BANNER.ordinal();
        else if(listItem.get(position).getImgextra() != null &&
                listItem.get(position).getImgextra().size() > 1 &&
                listItem.get(position).getSkipType() != null &&
                listItem.get(position).getSkipType().equals("photoset"))
            return NewsType.ITEM_TYPE_IMAGE.ordinal();
        else
            return NewsType.ITEM_TYPE_TEXT.ordinal();
    }



    /**
     * 创建每项的自定义布局资源（ViewHolder由回收视图所管理可在列表中重复使用）
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(MainActivity.TAG, "NewsAdapter.onCreateViewHolder！" + viewType);

        if (viewType == NewsType.ITEM_TYPE_TEXT.ordinal()) {
            //普通新闻
            View hold = layoutInflater.inflate(R.layout.news_text, parent, false);
            return new TextViewHolder(hold);
        }
        else if(viewType == NewsType.ITEM_TYPE_IMAGE.ordinal()){
            //图片新闻
            View hold = layoutInflater.inflate(R.layout.news_image, parent, false);
            return new ImageViewHolder(hold);
        }
        else {
            //横幅新闻
            View hold = layoutInflater.inflate(R.layout.news_banner, parent, false);
            return new BannerViewHold(hold);
        }
    }
    public static class TextViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imgView;
        TextView title,subTitle,vote;
        View view;
        TextViewHolder(View view) {
            super(view);
            this.view=view;
            imgView = view.findViewById(R.id.iv_left_image);
            title = view.findViewById(R.id.list_item_news_title);
            subTitle = view.findViewById(R.id.list_item_news_subtitle);
            vote = view.findViewById(R.id.list_item_vote);
        }
    }
    class TextViewClickListener implements View.OnClickListener {
        int position;
        TextViewClickListener(int i) {
            position = i;
        }
        @Override
        public void onClick(View v) {
            Intent i = new Intent(content, DetailActivity.class);
            i.putExtra("NEWS_LINK", "");
            content.startActivity(i);
        }
    }
    /**
     * 图片新闻项
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        TextView vote;
        RecyclerImgView recyclerView;
        View view;
        ImageViewHolder(View view) {
            super(view);
            this.view=view;
            txtView = view.findViewById(R.id.tv_title);
            vote = view.findViewById(R.id.tv_vote);
            //显示新闻图片列表
            recyclerView = view.findViewById(R.id.rv_subrecycleview);
        }
    }
    class ImageViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(content, ImageActivity.class);
            content.startActivity(i);
        }
    }
    /**
     * 横幅新闻项
     */
    public static class BannerViewHold extends RecyclerView.ViewHolder {
        SwitchImage mSwitchImage;
        public BannerViewHold(View itemView) {
            super(itemView);
            mSwitchImage = itemView.findViewById(R.id.si_banner_image);
        }
    }






    /**
     * 负责每视图项的数据绑定和事件侦听
     * 调用时机是项将要出现在屏幕上时，这时需要向viewHolder中填充数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Log.d(MainActivity.TAG, "NewsAdapter.onBindViewHolder！");
        if (holder instanceof TextViewHolder) {
            //设置视图文本
            ((TextViewHolder) holder).title.setText(listItem.get(position).getTitle());
            ((TextViewHolder) holder).subTitle.setText(listItem.get(position).getSource());
            ((TextViewHolder) holder).vote.setText(listItem.get(position).getReplyCount() + "跟帖");

            NetworkImageView imgView = ((TextViewHolder) holder).imgView;

            //从服务端获取指定尺寸的图片链接
            String picUrl= URLParse.parseWebImage(
                    listItem.get(position).getImgsrc(),
                    imgView.getWidth()
            );

            //清单文件内需设置因特网访问权限，否则无法出现图片
            //设置默认图片直到网络图片加载完为止
            //设置错误图片在网络加载出错时显示
            ImageLoader loader= VolleySingle.getInstance(content.getApplicationContext()).getImageLoader();
            imgView.setDefaultImageResId(defaultImage);
            imgView.setErrorImageResId(defaultImage);
            imgView.setImageUrl(picUrl,loader);

            //设置事件侦听
            ((TextViewHolder) holder).view.setOnClickListener(
                    new TextViewClickListener(position)
            );
        }
        else if (holder instanceof ImageViewHolder) {
            //新闻标题和跟贴数
            ((ImageViewHolder) holder).txtView.setText(listItem.get(position).getTitle());
            ((ImageViewHolder) holder).vote.setText(listItem.get(position).getReplyCount() + "跟帖");
            //含Url信息的Json数据
            String jsonUrl = URLParse.parsePhotoSet(listItem.get(position));

            //查找回收视图引用
            RecyclerImgView hold = ((ImageViewHolder) holder).recyclerView;
            if (hold.getAdapter() != null && hold.getAdapter() instanceof HorizontalAdapter) {
                //加载数据
                getPhotoSet((HorizontalAdapter) hold.getAdapter(), jsonUrl);
            } else {
                //为回收视图设置适配器
                hold.setLayoutManager(new LinearLayoutManager(
                        content,
                        LinearLayoutManager.HORIZONTAL,
                        false)
                );
                HorizontalAdapter adapter = new HorizontalAdapter(content, null, hold);
                hold.setAdapter(adapter);
                //加载数据（数据变化则通知适配器）
                getPhotoSet(adapter,jsonUrl);
            }
            ((ImageViewHolder) holder).view.setOnClickListener(
                    new ImageViewClickListener()
            );
        }
        else if (holder instanceof BannerViewHold) {

            //获取横幅新闻项
            NewsItem item = listItem.get(position);

            //轮播的图片数
            int size;
            List<Ads> ads = item.getAdss();
            if (ads != null)
                size = ads.size() + 1;
            else
                size = 1;

            //为轮播设置缺省图片
            int[] defaultImages2 = new int[size];
            for (int i = 0; i < defaultImages2.length; i++) {
                defaultImages2[i] = defaultImage;
            }

            //轮播新闻图片所在的Url地址
            String[] picList = new String[size];
            picList[0] = URLParse.parseWebImage(
                    item.getImgsrc(), ((BannerViewHold)holder).mSwitchImage.getWidth()
            );
            for (int i = 1; i < picList.length; i++) {
                picList[i] = item.getAdss().get(i - 1).getImgsrc();
            }

            //轮播新闻的标题（第1条和其后新闻分别设置）
            String[] titleList = new String[size];
            titleList[0] = item.getTitle();
            for (int i = 1; i < titleList.length; i++) {
                titleList[i] = item.getAdss().get(i - 1).getTitle();
            }

            //初始化并是否滚动（设置轮播的文本和图片）
            ((BannerViewHold) holder).mSwitchImage.initPager(defaultImages2, picList, titleList);
            ((BannerViewHold) holder).mSwitchImage.setMove(true, 8000);
            //从网络加载图片
            ((BannerViewHold) holder).mSwitchImage.setAndLoadImage(new SwitchImage.DisplayImageView() {
                @Override
                public void displayImageFromURL(ImageView view, String url) {
                    ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                            view,
                            defaultImage,
                            failImage
                    );
                    ImageLoader loader=VolleySingle.getInstance(content.getApplicationContext()).getImageLoader();
                    loader.get(url, listener, 1000, 500);
                }
            });

        }
    }
    private void getPhotoSet(final HorizontalAdapter adapter, final String url) {
        VolleySingle.getInstance(content.getApplicationContext()).getRequestQueue().add(
                RequestSingleton.getInstance().getGETStringRequest(content, url,
                        new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                JSONObject data;
                                try {
                                    //得到服务端返回数据
                                    data = new JSONObject(response.toString());
                                    PhotoSet photoSet = new Gson().fromJson(data.toString(), NewsGlobal.ItemImage);
                                    NewsGlobal.extraImageHashMap.put(url, photoSet);

                                    //通知适配器数据已改变
                                    adapter.setPhotoSet(photoSet);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }







    @Override
    public int getItemCount() {
        int result=listItem == null ? 0 : listItem.size();
        Log.d(MainActivity.TAG, "NewsAdapter.getItemCount！" + result);
        return result;
    }
    @Override
    public long getItemId(int position) {
        Log.d(MainActivity.TAG, "NewsAdapter.getItemId！");
        return super.getItemId(position);
    }
}
