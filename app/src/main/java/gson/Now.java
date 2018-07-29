package gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wuyue on 2018/7/29.
 */

public class Now {
    @SerializedName("tmp")
    public String temprature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
