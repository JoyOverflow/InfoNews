package ouyj.hyena.com.infonews.utils;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ouyj.hyena.com.infonews.model.NewsItem;

public class NewsGlobal {

    //解析新闻元素
    public static Type ItemType =
            new TypeToken<ArrayList<NewsItem>>() {}.getType();
}
