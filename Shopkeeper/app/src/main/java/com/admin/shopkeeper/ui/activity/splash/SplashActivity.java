package com.admin.shopkeeper.ui.activity.splash;

import android.os.CountDownTimer;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.boss.BossMainActivity;
import com.admin.shopkeeper.ui.activity.home.HomeActivity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.utils.SPUtils;
import com.gyf.barlibrary.ImmersionBar;


public class SplashActivity extends BaseActivity<SplashPresenter> implements ISplashView {
    private CountDownTimer timer;


    @Override
    protected void initPresenter() {
        presenter = new SplashPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .init();

        presenter.getUser();
        presenter.getConfig();
        //两秒钟后关闭引导页
        timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (App.INSTANCE().getUser() != null && SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_LOGIN, false)) {
                    if (App.INSTANCE().getUser().getRoleID().equals("2")) {
                        startActivity(BossMainActivity.class);
                    } else {
                        startActivity(HomeActivity.class);
                    }
                } else {
                    startActivity(LoginActivity.class);
                }
                finish();
            }
        };
        timer.start();
    }

    @Override
    protected void initTheme() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        ImmersionBar.with(this).init();
        if (timer != null) {
            timer.cancel();
        }

    }
}
