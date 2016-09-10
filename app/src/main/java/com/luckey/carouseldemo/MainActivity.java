package com.luckey.carouseldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //头部广告栏
    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<>();
    private List<String> networkImages;
    private List<String> strList = new ArrayList<>();
    private String[] str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

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
                        strList.add(list.get(i).getPosters().getImage_path());
                    }

                }

                initImageLoader();

                convenientBanner = (ConvenientBanner)
                        findViewById(R.id.convenientBanner_showGoods);
                //本地图片例子
                convenientBanner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, localImages)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求
                        // 自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.mipmap.abc_btn_radio_to_on_mtrl_000,
                                R.mipmap.abc_btn_radio_to_on_mtrl_015})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_LEFT);
                //设置翻页的效果，不需要翻页效果可用不设
                //     .setPageTransformer(Transformer.DefaultTransformer);
                //        convenientBanner.setManualPageable(false);设置不能手动影响
                //  网络加载例子
                str = new String[strList.size()];
                for (int i = 0; i < strList.size(); i++) {
                    str[i] = strList.get(i);
                }
                networkImages = Arrays.asList(str);
                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, networkImages);
                convenientBanner.startTurning(2000);
            }

            @Override
            public void onFailure(Call<BannerEntity> call, Throwable t) {

            }
        });


    }

    //初始化网络图片缓存库(广告轮播)
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_launcher)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
