package ouyj.hyena.com.infonews.utils;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ouyj.hyena.com.infonews.model.NewsItem;
import ouyj.hyena.com.infonews.model.PhotoSet;

public class NewsGlobal {

    //解析新闻元素
    public static Type ItemType =
            new TypeToken<ArrayList<NewsItem>>() {}.getType();

    //解析图片新闻内图片
    public static Type ItemImage =
            new TypeToken<PhotoSet>() {}.getType();

    public static ConcurrentHashMap<String, PhotoSet> extraImageHashMap = new ConcurrentHashMap<>();
}
