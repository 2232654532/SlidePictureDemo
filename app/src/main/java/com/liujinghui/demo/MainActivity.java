package com.liujinghui.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mcxtzhang.captchalib.SwipeCaptchaView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.swipecaptchaview)
    SwipeCaptchaView swipecaptchaview;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.change_code)
    Button changeCode;

    private int[] pictures = new int[]{R.drawable.liu_yi_fei_one, R.drawable.liu_yi_fei_two, R.drawable.yang_mi_one};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipecaptchaview.createCaptcha();
                //设置拖动条能点击
                seekbar.setEnabled(true);
                //设置当前的进度为0
                seekbar.setProgress(0);
            }
        });

        //设置回调监听
        swipecaptchaview.setOnCaptchaMatchCallback(new SwipeCaptchaView.OnCaptchaMatchCallback() {
            @Override
            public void matchSuccess(SwipeCaptchaView swipeCaptchaView) {
                Toast.makeText(MainActivity.this,"恭喜你，验证成功。可以进行尽情的玩耍了",Toast.LENGTH_SHORT).show();
                //设置拖动
                seekbar.setEnabled(false);
            }

            @Override
            public void matchFailed(SwipeCaptchaView swipeCaptchaView) {
                Toast.makeText(MainActivity.this,"完成了80%的比对，还差一点就成功了",Toast.LENGTH_SHORT).show();
                //设置拖动
                swipeCaptchaView.resetCaptcha();
                seekbar.setProgress(0);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                swipecaptchaview.setCurrentSwipeValue(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //设置进度条的最大值
                seekBar.setMax(swipecaptchaview.getMaxSwipeValue());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //进行比对截图是否一样
                swipecaptchaview.matchCaptcha();
            }
        });

        Glide.with(MainActivity.this).load(R.drawable.liu_yi_fei_one).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                //设置图片的资源
                swipecaptchaview.setImageBitmap(resource);
                //创建截图的阴影
                swipecaptchaview.createCaptcha();
            }
        });
    }
}
