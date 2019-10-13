package ouyj.hyena.com.infonews;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

import ouyj.hyena.com.infonews.adapter.NewsAdapter;
import ouyj.hyena.com.infonews.model.NewsItem;
import ouyj.hyena.com.infonews.utils.URLs;
import ouyj.hyena.com.infonews.utils.VolleySingle;

/**
 * 具体的新闻列表片段
 */
public class InfoFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String STRING_TABNAME = "intent_String_tabName";
    public static final String INT_TABPOSITION = "intent_int_position";

    private String tabName;
    private int position;

    private RecyclerView recycler;
    private LinearLayoutManager linear;
    private SwipeRefreshLayout swiper;

    private NewsAdapter adapter;
    private ArrayList<NewsItem> newsList = new ArrayList<>();
    private PageModel pageModel;

    /**
     * 构造方法
     */
    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * 延迟加载视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_info);

        //得到传递数据（选项卡名称和索引如“头条”）
        tabName = getArguments().getString(STRING_TABNAME);
        position = getArguments().getInt(INT_TABPOSITION);

        //查找下拉刷新视图引用
        swiper = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swiper.setOnRefreshListener(this);
        swiper.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_green_dark
        );

        //查找回收视图引用并设置（布局管理器用来确定项摆放规则）
        recycler = (RecyclerView)findViewById(R.id.recycler_view);
        linear = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(linear);
        //为回收视图设置适配器
        adapter = new NewsAdapter(
                getActivity(),
                newsList
        );
        recycler.setAdapter(adapter);


        pageModel = new PageModel();
        //获取新闻数据
        getNews();
        //设置回收视图的滚动事件
        setRecyclerScroll();
    }
    private void setRecyclerScroll() {
    }
    /**
     * 获取新闻数据
     */
    private void getNews() {
        String url2= URLs.concatNewsListURL(tabName, pageModel.getRangePage());
        Log.d(MainActivity.TAG, "当前请求：" + url2);
        //下次调用的页范围
        pageModel.moveRangePage();


        //解析网络数据
        String url="http://c.m.163.com/nc/article/headline/T1348647909107/0-10.html";
        //创建Volley对象
        Context context=getActivity().getApplicationContext();
        VolleySingle volley=VolleySingle.getInstance(context);

        //volley.getRequestQueue().add();




        //通知适配器更改数据
        adapter.notifyDataSetChanged();
        //取消下拉视图刷新
        swiper.setRefreshing(false);
    }
    /**
     * 下拉刷新时实现数据加载
     */
    @Override
    public void onRefresh() {
        Log.d(MainActivity.TAG, "正在下拉刷新！");

        //清空页范围
        pageModel.reSet();

        //清空从服务端获取的新闻列表
        newsList.clear();
        //重新加载数据
        getNews();
    }



    static class PageModel {
        int start;
        int end;
        public PageModel() {
            this.start = 0;
            this.end = 10;
        }
        public void reSet() {
            this.start = 0;
            this.end = 10;
        }
        /**
         * 返回要请求的记录范围
         * @return
         */
        public String getRangePage() {
            return start + "-" + end;
        }
        /**
         * 驱动索引向前走
         * @return
         */
        public boolean moveRangePage() {
            if(end<=200)
                end+=10;
            else
                end=200;

            return true;
        }
    }


}
