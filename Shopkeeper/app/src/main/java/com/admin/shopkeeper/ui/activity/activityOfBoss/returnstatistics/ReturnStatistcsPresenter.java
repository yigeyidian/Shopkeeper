package com.admin.shopkeeper.ui.activity.activityOfBoss.returnstatistics;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.IShopCollectionView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ReturnStatistcsPresenter extends BasePresenter<IReturnStatisticsView> {

    public ReturnStatistcsPresenter(Context context, IReturnStatisticsView iView) {
        super(context, iView);
    }

    public void getData(String startDate, String endDate, String startTime, String endTime, int type) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShopCollection("1", "ASC", startDate, endDate, startTime, endTime, App.INSTANCE().getShopID(), type)
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
}
