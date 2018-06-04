package org.zy.demogsyapp.video;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

import org.zy.demogsyapp.view.CustomRenderView;

/**
 * 自定义渲染控件
 * Created by guoshuyu on 2018/1/30.
 */

public class CustomRenderVideoPlayer extends NormalGSYVideoPlayer {
    public CustomRenderVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public CustomRenderVideoPlayer(Context context) {
        super(context);
    }

    public CustomRenderVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addTextureView() {
        mTextureView = new CustomRenderView();
        mTextureView.addView(getContext(), mTextureViewContainer, mRotate, this, this, mEffectFilter, mMatrixGL, mRenderer, mMode);
    }
}
