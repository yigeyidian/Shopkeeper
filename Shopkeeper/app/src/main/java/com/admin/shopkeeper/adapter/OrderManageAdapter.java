package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderManage;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class OrderManageAdapter extends BaseQuickAdapter<OrderManage, BaseViewHolder> {
    public OrderManageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }
    //Type  1.预定菜品 2 预定桌位  3.外卖  4.快餐 5.扫码点餐 6.排队点餐 7.店内点餐
    @Override
    protected void convert(BaseViewHolder helper, OrderManage item) {
        helper.setText(R.id.item_order_id, "订单号："+item.getOrderNumber());
//        helper.setText(R.id.item_order_state, item.getOrderSate());
        switch (item.getType()){
            case "1":
                helper.setText(R.id.item_order_type, "预定菜品");
                break;
            case "2":
                helper.setText(R.id.item_order_type, "预定桌位");
                break;
            case "3":
                helper.setText(R.id.item_order_type, "外卖");
                break;
            case "4":
                helper.setText(R.id.item_order_type, "快餐");
                break;
            case "5":
                helper.setText(R.id.item_order_type, "扫码点餐");
                break;
            case "6":
                helper.setText(R.id.item_order_type, "排队点餐");
                break;
            case "7":
                helper.setText(R.id.item_order_type, "店内点餐");
                break;
            default:
                break;
        }

        helper.setText(R.id.item_order_total, "￥" +item.getTotalMoney());
        helper.setText(R.id.item_order_youhui, "￥" +item.getFreeMoney());
        helper.setText(R.id.item_order_jieZhang, "￥" + item.getChargeMoney());
        helper.setText(R.id.item_order_person,  item.getUsername());
        helper.setText(R.id.item_order_time, item.getJieZhangTime());
    }
}
