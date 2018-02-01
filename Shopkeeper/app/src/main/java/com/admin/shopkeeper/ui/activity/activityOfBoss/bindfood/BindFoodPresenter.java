package com.admin.shopkeeper.ui.activity.activityOfBoss.bindfood;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.ui.activity.activityOfBoss.boss.IBossMainView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BindFoodPresenter extends BasePresenter<IBindFoodView> {

    public BindFoodPresenter(Context context, IBindFoodView iView) {
        super(context, iView);
    }


    public void getFood(SaleBean bean, String name) {
        RetrofitHelper.getInstance()
                .getApi()
                .getSaleBindFood("5", 20, 1, name, bean.getGuiId(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {

                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    iView.error("加载失败");
                });
    }


}
