package org.zy.demogsyapp;


import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.Window;

import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.zy.demogsyapp.adapter.RecyclerBaseAdapter;
import org.zy.demogsyapp.adapter.RecyclerNormalAdapter;
import org.zy.demogsyapp.holder.RecyclerItemNormalHolder;
import org.zy.demogsyapp.model.VideoModel;
import org.zy.demogsyapp.video.manager.CustomManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {


    @BindView(R.id.list_item_recycler)
    RecyclerView videoList;

//    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    RecyclerBaseAdapter recyclerBaseAdapter;

    List<VideoModel> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        resolveData();

        final RecyclerNormalAdapter recyclerNormalAdapter = new RecyclerNormalAdapter(this, dataList);
//        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        videoList.setLayoutManager(gridLayoutManager);
        videoList.setAdapter(recyclerNormalAdapter);

        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (CustomManager.instance().size() >= 0) {
                    Map<String, CustomManager> map = CustomManager.instance();
                    List<String> removeKey = new ArrayList<>();
                    for (Map.Entry<String, CustomManager> customManagerEntry : map.entrySet()) {
                        CustomManager customManager = customManagerEntry.getValue();
                        //当前播放的位置
                        int position = customManager.getPlayPosition();
                        //对应的播放列表TAG
                        if (customManager.getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                                && (position < firstVisibleItem || position > lastVisibleItem)) {
                            CustomManager.releaseAllVideos(customManagerEntry.getKey());
                            removeKey.add(customManagerEntry.getKey());
                        }
                    }
                    if (removeKey.size() > 0) {
                        for (String key : removeKey) {
                            map.remove(key);
                        }
//                        recyclerNormalAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
//        if (CustomManager.backFromWindowFull(this, listMultiNormalAdapter.getFullKey())) {
//            return;
//        }
        super.onBackPressed();
    }
    private boolean isPause;
    @Override
    protected void onPause() {
        super.onPause();
        CustomManager.onPauseAll();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomManager.onResumeAll();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomManager.clearAllVideo();
    }

    private void resolveData() {
        for (int i = 0; i < 19; i++) {
            VideoModel videoModel = new VideoModel();
            dataList.add(videoModel);
        }
        if (recyclerBaseAdapter != null)
            recyclerBaseAdapter.notifyDataSetChanged();
    }

}
