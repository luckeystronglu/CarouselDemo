package com.qf.localpiccarousel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //头部广告栏
    private ConvenientBanner convenientBanner;
    private List<Integer> localImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        localImages.add(R.mipmap.s1);
        localImages.add(R.mipmap.s2);
        localImages.add(R.mipmap.s3);
        localImages.add(R.mipmap.s4);
        localImages.add(R.mipmap.s5);

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
                .setPageIndicator(new int[]{R.mipmap.abc_btn_radio_to_on_mtrl_000,
                        R.mipmap.abc_btn_radio_to_on_mtrl_015})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_LEFT);

        convenientBanner.startTurning(2000);

    }

}





