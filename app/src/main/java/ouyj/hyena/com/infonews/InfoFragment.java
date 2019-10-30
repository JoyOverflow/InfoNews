package ouyj.hyena.com.infonews;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import ouyj.hyena.com.infonews.adapter.NewsAdapter;
import ouyj.hyena.com.infonews.model.NewsItem;
import ouyj.hyena.com.infonews.utils.NewsGlobal;
import ouyj.hyena.com.infonews.utils.RequestSingleton;
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

    //存储新闻ID（防止重复）
    private HashSet<String> postsList = new HashSet<>();
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

        //查找下拉刷新视图引用（设置下拉刷新事件）
        swiper = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swiper.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_green_dark
        );
        swiper.setOnRefreshListener(this);


        //查找回收视图引用并设置布局管理器（确定项摆放规则）
        recycler = (RecyclerView)findViewById(R.id.recycler_view);
        linear = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(linear);
        //为回收视图设置适配器
        adapter = new NewsAdapter(
                getActivity(),
                newsList
        );
        recycler.setAdapter(adapter);
        //设置回收视图的滚动事件
        setRecyclerScroll();


        //获取新闻数据
        pageModel = new PageModel();
        getNews();
    }
    private void setRecyclerScroll() {
        //回收视图的滚动事件
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判断是否滚到回收视图的底部（是则继续加载数据）
                if (linear.findLastVisibleItemPosition() == newsList.size() - 1
                        && newsList.size() != 0) {
                    getNews();
                }
            }
        });
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
        postsList.clear();
        //重新加载数据
        getNews();
    }
    /**
     * 获取新闻数据
     */
    private void getNews() {
        //下次调用的页范围
        String url2= URLs.concatNewsListURL(tabName, pageModel.getRangePage());
        Log.d(MainActivity.TAG, "当前请求：" + url2);
        pageModel.moveRangePage();


        //解析网络数据
        //String url="http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
        String url="http://c.m.163.com/nc/article/headline/T1348654204705/0-10.html";
        RequestSingleton request = RequestSingleton.getInstance();
        StringRequest stringRequest=request.getGETStringRequest(
                getActivity(),
                url,
                new Response.Listener() {
                    //请求成功时的处理
                    @Override
                    public void onResponse(Object response) {
                        try {
                            String tmp=response.toString();
                            JSONObject obj = new JSONObject(tmp);

                            //得到新闻数组
                            //JSONArray itemArray = obj.getJSONArray("T1348647909107");
                            JSONArray itemArray = obj.getJSONArray("T1348654204705");
                            ArrayList<NewsItem> items = new Gson().fromJson(
                                    itemArray.toString(),
                                    NewsGlobal.ItemType
                            );

                            //遍历新闻列表（去除重复）
                            for (int i = 0; i < items.size(); i++) {
                                NewsItem news = items.get(i);

                                if (news.getTemplate() != null && news.getTemplate().length() > 0) {
                                    //如果是带Template的（新闻列表内只允许一个）
                                    if (newsList.size() > 0) {
                                        continue;
                                    }
                                }
                                //得到新闻ID
                                String postId=news.getPostid();
                                //填充与适配器关联的数据源（适配器负责项视图的数据绑定）
                                if (!postsList.contains(postId)) {
                                    postsList.add(postId);
                                    newsList.add(news);
                                }
                            }
                            //通知适配器更改数据（数据源内已有数据）
                            adapter.notifyDataSetChanged();
                            //取消下拉视图刷新
                            swiper.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        //创建Volley对象
        Context context=getActivity().getApplicationContext();
        VolleySingle volley=VolleySingle.getInstance(context);
        volley.getRequestQueue().add(stringRequest);

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
