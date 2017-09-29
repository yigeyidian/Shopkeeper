package com.admin.shopkeeper.ui.fragment.statement;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.BussinessBean;
import com.admin.shopkeeper.entity.DeskBussinessBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.fragment.market.IMarketView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

class StatementPresenter extends BasePresenter<IStatementView> {


    StatementPresenter(Context context, IStatementView iView) {
        super(context, iView);
    }

    public void getData() {
        RetrofitHelper.getInstance()
                .getApi()
                .getBussinee("10", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("加载失败");
                        } else {
                            BussinessBean[] bussinessBeen = new Gson().fromJson(stringModel.getResult(), BussinessBean[].class);
                            iView.success(Arrays.asList(bussinessBeen));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    iView.error("加载失败");
                });
    }
}
