package com.qf.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //头部广告栏
    private List<String> titles = new ArrayList<>();
    private List<String> imgs = new ArrayList<>();
    private Banner banner;



//    private ArrayList<Integer> localImages = new ArrayList<>();

//    private List<String> networkImages;
//    private List<String> strList = new ArrayList<>();
//    private String[] str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        banner = (Banner) findViewById(R.id.banner);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://2.mshouban.applinzi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Util util = retrofit.create(Util.class);

        Call<BannerEntity> call = util.getDatas();
        call.enqueue(new Callback<BannerEntity>() {
            @Override
            public void onResponse(Call<BannerEntity> call, Response<BannerEntity> response) {
                List<BannerEntity.DataBean.FloorsBean.MAINTOPBANNERBean.HolesBean> list = response.body().getData().getFloors()
                        .getMAIN_TOP_BANNER().getHoles();


                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getPosters() != null){
                        Log.d("pring", "onResponse: "+list.get(i).getPosters());
//                        strList.add(list.get(i).getPosters().getImage_path());
                        imgs.add(list.get(i).getPosters().getImage_path());
                    }

                }

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getPosters() != null) {
                        titles.add(list.get(i).getPosters().getTitle());
                    }
                }


                banner.setImages(imgs)
                        .setImageLoader(new GlideImageLoader())
                        .setBannerTitles(titles)
                        //不设置setBannerStyle则只有图标指示器
//                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)//文字+指示图标
//                        .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE) //文字+数字指示
//                        .setBannerStyle(BIND_NOT_FOREGROUND) //文字在阴影区，图标指示在上
//                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)//只有图标指示器
                        .setBannerAnimation(Transformer.DepthPage)
                        .setDelayTime(3000)
                        .setIndicatorGravity(BannerConfig.CENTER)
                        .start();
                banner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Log.d("print", "OnBannerClick: " + position);
                    }
                });



            }

            @Override
            public void onFailure(Call<BannerEntity> call, Throwable t) {

            }
        });


    }

    public class GlideImageLoader implements ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：

             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */

            Glide.with(context).load(path).into(imageView);

        }
    }
}
