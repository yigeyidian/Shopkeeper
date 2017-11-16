package com.admin.shopkeeper.ui.activity.activityOfBoss.memberconsumeDetails;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MemberConsumeDetailBean;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberConsumeDetailPresenter extends BasePresenter<IMemberConsumeDetailView> {

    public MemberConsumeDetailPresenter(Context context, IMemberConsumeDetailView iView) {
        super(context, iView);
    }

    public void getData(int pageIndex, String startDate, String endDate, String startTime, String endTime, int type,String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMemberTranscation("12", 20, pageIndex ,"ASC", startDate,
                        endDate, startTime, endTime,shopId, type)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if(TextUtils.isEmpty(stringModel.getResult())){
                            if(pageIndex>1){
                                iView.success("没有更多数据");
                            }else{
                                iView.success("暂无数据");
                            }
                        }else{
                            MemberConsumeDetailBean[] beens = new Gson().fromJson(stringModel.getResult(), MemberConsumeDetailBean[].class);
                            iView.success(Arrays.asList(beens));
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
