package org.zy.demogsyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author ZhangYue
 * @project customDemo
 * @createdTime 2018/6/1  15:49
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRTSPDetailActivity(View view) {
        startActivity(new Intent(this, VideoWithContentActivity.class));
    }

    public void openRTSPMutiListActivity(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void openHTTPDetailActivity(View view) {
        startActivity(new Intent(this, HttpVideoPlayActivity.class));
    }
}
