package ouyj.hyena.com.infonews.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestSingleton {

    private volatile static RequestSingleton request;
    private static final HashMap<String, String> defaultPairs_baishuku;

    public static final String CONTENT_TYPE = "Content-Type";
    protected static final String TYPE_UTF8_CHARSET = "charset=UTF-8";

    static {
        defaultPairs_baishuku = new HashMap<>();
        defaultPairs_baishuku.put("User-Agent", "Mozilla/5.0");
        defaultPairs_baishuku.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        defaultPairs_baishuku.put("Accept-Encoding", "");
        defaultPairs_baishuku.put("Accept-Language", "zh-CN,zh;q=0.8");
        defaultPairs_baishuku.put("Host", "c.m.163.com");
        defaultPairs_baishuku.put("Upgrade-Insecure-Requests", "1");
    }


    /**
     * 获取单例对象
     * @return
     */
    public static RequestSingleton getInstance() {
        if (request == null) {
            synchronized (RequestSingleton.class) {
                if (request == null)
                    request = new RequestSingleton();
            }
        }
        return request;
    }

    public StringRequest getGETStringRequest(Context context, final String url, Response.Listener responseListener) {
        return new StringRequest(
                Request.Method.GET,
                url,
                responseListener,
                new DefaultErrorListener(context))
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                addEncodeing2Request(response);
                return super.parseNetworkResponse(response);
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return defaultPairs_baishuku;
            }
            @Override
            public int getDefaultTtl() {
                return 1 * 30 * 1000;
            }
            @Override
            public int getDefaultSoftTtl() {
                return 1 * 30 * 1000;
            }
            @Override
            public boolean shouldLocalCacheControl() {
                return true;
            }
        };
    }

    private void addEncodeing2Request(NetworkResponse response) {
        try {
            String type = response.headers.get(CONTENT_TYPE);
            if (type == null) {
                //Content-Type:
                Log.d("RVA", "content type was null");
                type = TYPE_UTF8_CHARSET;
                response.headers.put(CONTENT_TYPE, type);
            } else if (!type.contains("charset")) {
                //Content-Type: text/plain;
                Log.d("RVA", "charset was null, added encode utf-8");
                type += ";" + TYPE_UTF8_CHARSET;
                response.headers.put(CONTENT_TYPE, type);
            } else {
                //Nice! Content-Type: text/plain; charset=utf-8'
                Log.d("RVA", "charset is " + type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DefaultErrorListener implements Response.ErrorListener {
        private Context contextHold;
        public DefaultErrorListener(Context context) {
            contextHold = context;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Log.d("RVA", "error:" + error);

            int errorCode = 0;
            if (error instanceof TimeoutError) {
                errorCode = -7;
            } else if (error instanceof NoConnectionError) {
                errorCode = -1;
            } else if (error instanceof AuthFailureError) {
                errorCode = -6;
            } else if (error instanceof ServerError) {
                errorCode = 0;
            } else if (error instanceof NetworkError) {
                errorCode = -1;
            } else if (error instanceof ParseError) {
                errorCode = -8;
            }
            Toast.makeText(contextHold, ErrorCode.errorCodeMap.get(errorCode), Toast.LENGTH_SHORT).show();
        }
    }
}
