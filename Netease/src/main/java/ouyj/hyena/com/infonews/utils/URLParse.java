package ouyj.hyena.com.infonews.utils;

import ouyj.hyena.com.infonews.model.NewsItem;

public class URLParse {
    /**
     * 拼接图片链接（从服务端获取指定尺寸的图片）
     * @param imageSrc
     * @param width
     * @return
     */
    public static String parseWebImage(String imageSrc, int width) {
        if (imageSrc.contains("http://")) {
            return URLs.WEBP_PRE_URL2 + imageSrc + "&thumbnail=" +  width + URLs.WEBP_POS_URL2;
        } else {
            return imageSrc;
        }
    }



    //"54GI0096|76110" transform to http://c.3g.163.com/photo/api/set/0096/76110.json
    public static String parsePhotoSet(NewsItem newsItem) {
        if (newsItem == null) return "";
        String photoset = newsItem.getSkipID();
        if (photoset == null) return "";

        int index = photoset.lastIndexOf("|");
        String realUrl = "";
        if (index - 4 >= 0  && index + 1 <= photoset.length()) {
            realUrl = photoset.substring(index - 4, photoset.length()).replace("|", "/");
            realUrl = URLs.IMAGE_JSON_URL +  realUrl + ".json";
        }
        return realUrl;
    }
}
