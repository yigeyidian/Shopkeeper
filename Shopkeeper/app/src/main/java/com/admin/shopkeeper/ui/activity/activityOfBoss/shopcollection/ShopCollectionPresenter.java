package com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.ReturnBussinessBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returnbussiness.IReturnBussinessView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ShopCollectionPresenter extends BasePresenter<IShopCollectionView> {

    public ShopCollectionPresenter(Context context, IShopCollectionView iView) {
        super(context, iView);
    }

    public void getData(int type, String startDate, String endDate, String startTime, String endTime, int selectType) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShopCollection(type + "", "ASC", startDate, endDate, startTime, endTime, App.INSTANCE().getShopID(), selectType)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        ShopCollectionBean[] beens = new Gson().fromJson(stringModel.getResult(), ShopCollectionBean[].class);
                        iView.success(Arrays.asList(beens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getChain() {
        RetrofitHelper.getInstance()
                .getApi()
                .getChain("15", App.INSTANCE().getShopID())
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
