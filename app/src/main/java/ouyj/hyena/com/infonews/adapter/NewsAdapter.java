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


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context content;
    private ArrayList<NewsItem> listItem;
    private LayoutInflater layoutInflater;

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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = layoutInflater.inflate(
                R.layout.item_text,
                parent,
                false
        );
        return new TextViewHolder(hold);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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




}
