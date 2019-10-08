package ouyj.hyena.com.infonews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import ouyj.hyena.com.infonews.utils.LruImageCache;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int defaultImage = R.drawable.load_fail;
        NetworkImageView imgView = findViewById(R.id.img_network);


        String url="http://nimg.ws.126.net/?url=http://cms-bucket.ws.126.net/2019/10/06/b5555451a9f246e89eaf31cb14448981.png&thumbnail=92x2147483647&quality=75&type=webp";
        //ImageLoader loader=VolleySingle.getInstance(getApplicationContext()).getImageLoader();
        //imgView.setDefaultImageResId(defaultImage);
        //imgView.setErrorImageResId(defaultImage);
        //imgView.setImageUrl(url,loader);




        //创建请求队列对象
        RequestQueue queue = Volley.newRequestQueue(this);
        LruImageCache lruImageCache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(queue,lruImageCache);
        imgView.setDefaultImageResId(defaultImage);
        imgView.setErrorImageResId(defaultImage);
        imgView.setImageUrl(url,loader);
    }
}
