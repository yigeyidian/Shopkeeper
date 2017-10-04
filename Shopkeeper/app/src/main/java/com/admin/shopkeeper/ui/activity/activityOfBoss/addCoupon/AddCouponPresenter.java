package com.admin.shopkeeper.ui.activity.activityOfBoss.addCoupon;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.ProductBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class AddCouponPresenter extends BasePresenter<IAddCouponView> {

    public AddCouponPresenter(Context context, IAddCouponView iView) {
        super(context, iView);
    }

    //presenter.submit(nameStr,needStr,giveMoney,startDate,endDate,detail,typeWaimai,typeSpinner,limitStr,maxStr,dayStr,jiFenStr,nameStr);
    public void submit(String nameStr, String needStr, String giveMoney, String startDate, String endDate, String detail, int couponType,
                       int typeWaimai, String useType, String limitStr, String maxStr, String dayStr, String jiFenStr, String num) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addCoupon("2", App.INSTANCE().getShopID(), nameStr, needStr, giveMoney, startDate, endDate,
                        detail, jiFenStr, typeWaimai, couponType, useType, maxStr, dayStr, num, limitStr)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("添加成功");
                    } else {
                        iView.error("添加失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("添加失败");
                });
    }

    public void submitGroup(String nameStr, String needStr, String giveMoney, String startDate, String endDate, String detail, int couponType,
                            int typeWaimai, String useType, String limitStr, String maxStr, String dayStr, String jiFenStr, String num,
                            String productName, String productId) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addCouponGroup("3", App.INSTANCE().getShopID(), nameStr, needStr, giveMoney, startDate, endDate,
                        detail, jiFenStr, typeWaimai, couponType, useType, maxStr, dayStr, num, limitStr, productName, productId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("添加成功");
                    } else {
                        iView.error("添加失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("添加失败");
                });
    }

    public void submitCoupon(String nameStr, String needStr, String giveMoney, String startDate, String endDate, String detail, int couponType,
                             int typeWaimai, String useType, String limitStr, String maxStr, String dayStr, String jiFenStr, String num,
                             String productName, String productId) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addCommodityCoupon("3", App.INSTANCE().getShopID(), nameStr, needStr, giveMoney, startDate, endDate,
                        detail, jiFenStr, typeWaimai, couponType, useType, maxStr, dayStr, num, limitStr,productName, productId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("添加成功");
                    } else {
                        iView.error("添加失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("添加失败");
                });
    }

    public void getProductData() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getCommodityCouponInfo("6", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("1")) {
                            iView.error("暂无数据");
                        } else {
                            ProductBean[] productBeen = new Gson().fromJson(stringModel.getResult(), ProductBean[].class);
                            iView.productsuccess(Arrays.asList(productBeen));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getMealData() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getProductInfo("5", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("1")) {
                            iView.error("暂无数据");
                        } else {
                            ProductBean[] productBeen = new Gson().fromJson(stringModel.getResult(), ProductBean[].class);
                            iView.productsuccess(Arrays.asList(productBeen));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
