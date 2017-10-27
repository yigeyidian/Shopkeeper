package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class RechargeTranscationAdapter extends BaseQuickAdapter<MemberTranscationBean, BaseViewHolder> {
    public RechargeTranscationAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberTranscationBean item) {
        helper.setText(R.id.item_name, App.INSTANCE().getShopName());
        helper.setText(R.id.item_username, String.valueOf(item.getName()));
        helper.setText(R.id.item_phone, String.valueOf(item.getPhone()));
        helper.setText(R.id.item_cumulative_recharge, String.valueOf(item.getRechargeMoney()));
        helper.setText(R.id.item_consume_money, String.valueOf(item.getUsedMoney()));
        helper.setText(R.id.item_recharge_money, String.valueOf(item.getZonAdd()));
        helper.setText(R.id.item_yue, String.valueOf(item.getYue()));
    }
}
