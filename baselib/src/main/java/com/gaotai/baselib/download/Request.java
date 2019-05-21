package  com.gaotai.baselib.download;

import java.util.ArrayList;
import java.util.List;

import android.util.Pair;
/**
 * 请求信息
 */
public class Request {

	public static final int            NETWORK_TYPE_MOBILE = 1;
    public static final int            NETWORK_TYPE_WIFI   = 1 << 1;

    public String                      title;
    public String                      description;
    public String                      requestUrl;
    public String                      dstFilePath;
    public int                         mAllowedNetworkTypes; 
    public String                      MD5;
    public String                      SHA1;
    
    private List<Pair<String, String>> requestHeaders      = new ArrayList<Pair<String, String>>();
    
    public Request addRequestHeader(String key, String value) {
        if (key == null) {
            throw new NullPointerException("header key cannot be null");
        }
        if (key.contains(":")) {
            throw new IllegalArgumentException("header key may not contain ':'");
        }
        this.requestHeaders.add(Pair.create(key, value));
        return this;
    }
}
