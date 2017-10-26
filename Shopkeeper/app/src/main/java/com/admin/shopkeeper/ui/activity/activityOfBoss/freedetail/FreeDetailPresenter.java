package com.admin.shopkeeper.ui.activity.activityOfBoss.freedetail;

import android.content.Context;

import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.HandoverDetailBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.IJionDetailView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class FreeDetailPresenter extends BasePresenter<IFreeDetailView> {

    public FreeDetailPresenter(Context context, IFreeDetailView iView) {
        super(context, iView);
    }

}
