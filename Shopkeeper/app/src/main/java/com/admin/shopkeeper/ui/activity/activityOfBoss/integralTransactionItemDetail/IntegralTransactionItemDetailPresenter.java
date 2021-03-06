package com.admin.shopkeeper.ui.activity.activityOfBoss.integralTransactionItemDetail;

import android.content.Context;

import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.IntegralTranscationItemDetailBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class IntegralTransactionItemDetailPresenter extends BasePresenter<IIntegralTransactionItemDetailView> {

    public IntegralTransactionItemDetailPresenter(Context context, IIntegralTransactionItemDetailView iView) {
        super(context, iView);
    }


    public void getDetail(String date, String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getCollectionDetail("16", date, shopId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
//                        CollectionBean[] beans = new Gson().fromJson(stringModel.getResult(), CollectionBean[].class);
//                        iView.success(Arrays.asList(beans));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getDetail(int pageIndex , String startTime, String endTime,String uid ,String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getDetail("20", 20, pageIndex,"ASC" ,startTime,endTime,uid, shopId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        IntegralTranscationItemDetailBean[] beans = new Gson().fromJson(stringModel.getResult(), IntegralTranscationItemDetailBean[].class);
                        iView.success(Arrays.asList(beans));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
