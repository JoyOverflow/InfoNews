package ouyj.hyena.com.infonews.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.NetworkImageView;

import ouyj.hyena.com.infonews.MainActivity;
import ouyj.hyena.com.infonews.MyApplication;
import ouyj.hyena.com.infonews.R;
import ouyj.hyena.com.infonews.model.PhotoSet;
import ouyj.hyena.com.infonews.utils.URLParse;
import ouyj.hyena.com.infonews.utils.VolleySingle;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnTouchListener{


    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    private ViewGroup parent;
    private RecyclerView recyclerView;
    private PhotoSet photoSet;
    private ViewConfiguration viewConfiguration;
    private int imageHeight;
    private int imageWeight;

    int defaultImage = R.drawable.load_fail_small;

    /**
     * 构造方法
     * @param context
     * @param photoSet
     * @param recyclerView
     */
    public HorizontalAdapter(Context context, PhotoSet photoSet, RecyclerView recyclerView) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        this.recyclerView = recyclerView;
        this.photoSet = photoSet;

        Resources r = context.getResources();
        float leftAndRightMargin = r.getDimension(R.dimen.list_margin_left_and_right);
        float imageMargin = r.getDimension(R.dimen.list_margin_top_and_bottom);

        viewConfiguration = ViewConfiguration.get(context);
        imageHeight = (int)r.getDimension(R.dimen.list_big_image_item_height);
        imageWeight = (int)(MyApplication.width - (leftAndRightMargin + imageMargin) * 2) / 3;
    }
    public void setPhotoSet(PhotoSet photo) {
        this.photoSet = photo;
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : position;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = mLayoutInflater.inflate(R.layout.item_image, parent, false);
        this.parent = parent;

        //设置项内图像视图的属性和事件
        ImageViewHolder holder = new ImageViewHolder(hold, imageWeight, imageHeight, viewType);
        holder.imgView.setTag(viewType);
        holder.imgView.setOnTouchListener(this);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (photoSet == null || photoSet.getPhotos() == null) {
            networkImage(((ImageViewHolder) holder).imgView, "");
        } else {
            String url=photoSet.getPhotos().get(position).getTimgurl();
            networkImage(
                    ((ImageViewHolder) holder).imgView,
                    URLParse.parseWebImage(url, imageWeight)
            );
        }
    }
    private void networkImage(NetworkImageView networkImageView, String url) {
        networkImageView.setDefaultImageResId(defaultImage);
        networkImageView.setErrorImageResId(defaultImage);
        networkImageView.setImageUrl(url,
                VolleySingle.getInstance(mContext.getApplicationContext()).getImageLoader());
    }



    @Override
    public int getItemCount() {
        return (photoSet == null || photoSet.getPhotos() == null)
                ? 3 : photoSet.getPhotos().size();
    }


    /**
     * 每项的图像触模事件
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(MainActivity.TAG, "onTouch！");
        return false;
    }
    /**
     * 每项的自定义布局
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView imgView;
        int index;
        ImageViewHolder(View view , int weight, int height, int i) {
            super(view);
            index = i;
            //第一张图不要边距
            if (index == 0) {
                //设置根布局
                RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                RelativeLayout rv = view.findViewById(R.id.container);
                rl.setMargins(0, 0, 0, 0);
                rv.setLayoutParams(rl);
            }
            imgView = view.findViewById(R.id.network_img);
            imgView.setLayoutParams(new RelativeLayout.LayoutParams(weight, height));
        }
    }
}
