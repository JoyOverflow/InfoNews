package ouyj.hyena.com.infonews.model;

import java.util.List;

public class NewsItem {

    private String postid;
    private String template;
    private String title;
    private String source;
    private int replyCount;
    private String imgsrc;

    public String getPostid(){
        return postid;
    }
    public String getTemplate() {
        return template;
    }
    public String getTitle() {
        return title;
    }
    public String getSource() {
        return source;
    }
    public int getReplyCount() {
        return replyCount;
    }
    public String getImgsrc() {
        return imgsrc;
    }



    private String skipID;
    private String skipType;
    private int order;
    public String getSkipID() {
        return skipID;
    }
    public String getSkipType() {
        return skipType;
    }
    public int getOrder() {
        return order;
    }


    private List<Ads> ads;
    public List<Ads> getAdss() {
        return ads;
    }

}
