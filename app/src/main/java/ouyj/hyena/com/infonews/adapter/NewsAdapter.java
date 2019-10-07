package ouyj.hyena.com.infonews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import ouyj.hyena.com.infonews.model.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public NewsAdapter(Context context, ArrayList<NewsItem> listItem, RecyclerView recyclerView) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
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
}
