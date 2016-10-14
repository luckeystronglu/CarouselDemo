package com.qf.picnotext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //顶部轮播图
    private ConvenientBanner convenientBanner;
    private List<String> imglist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCarousel();
    }

    private void initCarousel() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner_showGoods);
//        convenientBanner.setPadding(0,0,0,0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contact.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONUtil util = retrofit.create(JSONUtil.class);
        Call<CarouselEntity> call = util.getCarousel();
        call.enqueue(new Callback<CarouselEntity>() {
            @Override
            public void onResponse(Call<CarouselEntity> call, Response<CarouselEntity> response) {
                List<CarouselEntity.DataBean> data = response.body().getData();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getPhoto() != null){
//                        Log.d("pring", "onResponse: "+data.get(i).getPhoto().getPhoto_url());
                        imglist.add(data.get(i).getPhoto().getPhoto_url());
                    }

                }


                convenientBanner
                        .setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                            @Override
                            public NetworkImageHolderView createHolder() {
                                return new NetworkImageHolderView();
                            }
                        }, imglist)
                        .setPageIndicator(new int[]{R.mipmap.abc_btn_radio_to_on_mtrl_000,
                                R.mipmap.abc_btn_radio_to_on_mtrl_015})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                        .startTurning(2000);
            }

            @Override
            public void onFailure(Call<CarouselEntity> call, Throwable t) {

            }
        });
    }
}
