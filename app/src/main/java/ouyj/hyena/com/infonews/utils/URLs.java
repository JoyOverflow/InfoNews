package ouyj.hyena.com.infonews.utils;

public class URLs {
    //滚动选项卡
    public static String tabName[] = {
            "头条",
            "科技",
            "体育",
            "广州",
            "财经",
            "足球",
            "娱乐",
            "电影",
            "汽车",
            "博客",
            "社会",
            "旅游"
    };
    //要读取的Url链接
    public static final String host = "http://c.m.163.com/";
    public static final String PRE_URL = host + "news/";

    public static String concatNewsListURL(String name, String startEndIndex) {
        String keyword= getUrlKey(name);
        return PRE_URL + keyword + "/" + startEndIndex +  ".html";
    }
    /**
     * 将汉字转换为拼音（例：头条=toutiao）
     * @param name
     * @return
     */
    public static String getUrlKey(String name) {
        String pinyin = PinYin.convertAll(name);
        return pinyin;
    }
}
