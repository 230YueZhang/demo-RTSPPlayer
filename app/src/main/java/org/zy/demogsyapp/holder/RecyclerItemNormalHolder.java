package org.zy.demogsyapp.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.zy.demogsyapp.R;
import org.zy.demogsyapp.model.VideoModel;
import org.zy.demogsyapp.video.MultiSampleVideo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerItemNormalHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerViewNormalHolder";

    protected Context context = null;

    @BindView(R.id.video_item_player)
    MultiSampleVideo gsyVideoPlayer;

    private String fullKey = "null";

    public RecyclerItemNormalHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
    }

    public void onBind(final int position, VideoModel videoModel) {
        String url;
        String title;
        if (position % 2 == 0) {
            url = "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov";//"https://res.exexm.com/cw_145225549855002";
            title = "这是title2";
        } else {
            url = "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov";//"http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
            title = "这是title1";
        }
        //多个播放时必须在setUpLazy、setUp和getGSYVideoManager()等前面设置
        gsyVideoPlayer.setPlayTag(TAG);
        gsyVideoPlayer.setPlayPosition(position);

        boolean isPlaying = gsyVideoPlayer.getCurrentPlayer().isInPlayingState();

        if (!isPlaying) {
            gsyVideoPlayer.setUpLazy(url, false, null, null, "这是title");
        }

        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        gsyVideoPlayer.setRotateViewAuto(true);
        gsyVideoPlayer.setLockLand(true);
        gsyVideoPlayer.setReleaseWhenLossAudio(false);
        gsyVideoPlayer.setShowFullAnimation(true);
        gsyVideoPlayer.setIsTouchWiget(false);

        gsyVideoPlayer.setNeedLockFull(true);

        if (position % 2 == 0) {
            gsyVideoPlayer.loadCoverImage(url, R.mipmap.defaultimg);
        } else {
            gsyVideoPlayer.loadCoverImage(url, R.mipmap.defaultimge);
        }

        gsyVideoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {


            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                fullKey = "null";
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                fullKey = gsyVideoPlayer.getKey();
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    public String getFullKey() {
        return fullKey;
    }

}