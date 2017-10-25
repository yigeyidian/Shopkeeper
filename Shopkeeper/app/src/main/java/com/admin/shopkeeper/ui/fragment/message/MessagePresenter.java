package com.admin.shopkeeper.ui.fragment.message;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

public class MessagePresenter extends BasePresenter<IMessageView> {
    public MessagePresenter(Context context, IMessageView iView) {
        super(context, iView);
    }

    public void getMessage(int index, int size) {
        RetrofitHelper.getInstance().getApi().getMessage("8", App.INSTANCE().getShopID(), "all", "all", "all", index, size)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            Order[] orders = new Gson().fromJson(stringModel.getResult(), Order[].class);
                            iView.success(Arrays.asList(orders));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(stringModel.getMessage());
                            break;
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    iView.error("获取消息列表失败");
                });
    }

    public void getOrderDetail(Order item, int position) {
        RetrofitHelper.getInstance().getApi().getOrderDetail(App.INSTANCE().getShopID(), "9", item.getBillid())
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            OrderDetailFood[] detailFoods = new Gson().fromJson(stringModel.getResult(), OrderDetailFood[].class);
                            iView.toDetail(item, Arrays.asList(detailFoods), position);
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(stringModel.getMessage());
                            break;
                    }

                }, throwable -> {
                    iView.warning("获取订单详情失败");
                });
    }
}
