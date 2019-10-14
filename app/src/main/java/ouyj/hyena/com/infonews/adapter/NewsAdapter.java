package ouyj.hyena.com.infonews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import ouyj.hyena.com.infonews.R;
import ouyj.hyena.com.infonews.model.NewsItem;
import ouyj.hyena.com.infonews.widget.RecyclerImgView;
import ouyj.hyena.com.infonews.widget.SwitchImage;

/**
 * 自定义新闻适配器类（Adapter是RecyclerView的一个内部抽象类）
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context content;
    private ArrayList<NewsItem> listItem;
    private LayoutInflater layoutInflater;

    //新闻类型的枚举
    public enum NewsType {
        ITEM_TYPE_BANNER,
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }


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
     * 返回创建的ViewHolder（负责每项的自定义布局资源）
     * 而viewHolder作为回收视图所管理的视图对象可以在列表中复用
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NewsType.ITEM_TYPE_TEXT.ordinal()) {
            //普通新闻
            View hold = layoutInflater.inflate(R.layout.news_text, parent, false);
            return new TextViewHolder(hold);
        }
        else if(viewType == NewsType.ITEM_TYPE_IMAGE.ordinal()){
            View hold = layoutInflater.inflate(
                    R.layout.news_image,
                    parent,
                    false
            );
            return new ImageViewHolder(hold);
        }
        else {
            View hold = layoutInflater.inflate(
                    R.layout.news_banner,
                    parent,
                    false
            );
            return new BannerViewHold(hold);
        }
    }
    /**
     * 负责每项的视图数据绑定和事件侦听
     * 调用时机是项将要出现在屏幕上时，这时需要向viewHolder中填充数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {

        }
        else if (holder instanceof ImageViewHolder) {

        }
        else if (holder instanceof BannerViewHold) {

        }
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    /**
     * 普通新闻项
     */
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
        }
    }


    /**
     * 图片新闻项
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        TextView vote;
        //显示新闻图片列表
        RecyclerImgView recyclerView;
        ImageViewHolder(View view) {
            super(view);
            txtView = view.findViewById(R.id.tv_title);
            vote = view.findViewById(R.id.tv_vote);
            recyclerView = view.findViewById(R.id.rv_subrecycleview);
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

}
