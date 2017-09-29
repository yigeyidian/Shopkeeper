package com.admin.shopkeeper.ui.activity.splash;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;

import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.User;
import com.admin.shopkeeper.utils.SPUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by zeng on 2017/3/28.
 */

class SplashPresenter extends BasePresenter<ISplashView> {
    SplashPresenter(Context context, ISplashView iView) {
        super(context, iView);
    }

    void getConfig() {
        String id = SPUtils.getInstance().getString(SPUtils.PREFERENCE_SHOP_ID);
        if (!TextUtils.isEmpty(id)) {
            App.INSTANCE().setShopID(id);
        }
    }

     void getUser() {
        AppDbHelper.INSTANCE().getUser()
                .compose(getActivityLifecycleProvider().<User>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    App.INSTANCE().setUser(user);
                }, Throwable::printStackTrace);
    }
}
