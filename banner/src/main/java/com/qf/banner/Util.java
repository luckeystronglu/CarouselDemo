package com.qf.banner;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luckey on 2016/8/26.
 */
public interface Util {

    @GET("bejson/fetchJSON.php?u=http%3A%2F%2Fwww.hunliji.com%2Fp%2Fwedding%2Findex.php%2Fhome%2FAPIPosterBlock%2Fblock_info%3Fid%3D1001%26app_version%3D6.8.7%26city%3D0%0A&_=1471513881375")
    Call<BannerEntity> getDatas();
}
