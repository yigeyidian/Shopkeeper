package com.admin.shopkeeper.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.Order;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zeng on 2017/4/17.
 */

public class OrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    int type = 0;

    public OrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    public OrderAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, Order item) {

        helper.setText(R.id.tv_order_id, String.format(mContext.getString(R.string.string_order_id), item.getOrderNumber()));
        helper.setText(R.id.orderMoney, String.format(mContext.getString(R.string.string_order_money), item.getPayPrice()));
        helper.setText(R.id.orderTime, String.format(mContext.getString(R.string.string_order_time), item.getRecordDate()));
        helper.setText(R.id.table_id, String.format(mContext.getString(R.string.string_table_id), item.getTableName()));
        helper.setText(R.id.orderOperator, String.format(mContext.getString(R.string.string_order_operator), item.getUsername()));

//        Type  1.预定菜品 2 预定桌位  3.外卖  4.快餐 5.扫码点餐 6.排队点餐 7.店内点餐
//        OrderSate 1.待处理（未支付） 2.已确定（已支付） 3.已完成（已经开单）4.已取消 5.制作中（暂时未使用） 6.等待配送 （暂时未使用）

        switch (item.getType()) {
            case "1":
                helper.setText(R.id.orderType, "预定菜品");
                break;
            case "2":
                helper.setText(R.id.orderType, "预定桌位");
                break;
            case "3":
                helper.setText(R.id.orderType, "外卖");
                break;
            case "4":
                helper.setText(R.id.orderType, "快餐");
                break;
            case "5":
                helper.setText(R.id.orderType, "扫码点餐");
                break;
            case "6":
                helper.setText(R.id.orderType, "排队点餐");
                break;
            case "7":
                helper.setText(R.id.orderType, "店内点餐");
                break;
        }

        switch (item.getState()) {
            case 1:
                helper.setText(R.id.tv_order_status, "待处理");
                helper.setTextColor(R.id.tv_order_status, ContextCompat.getColor(mContext, R.color.orderStatusConfirm));
                helper.setImageResource(R.id.im_order_status, R.mipmap.bardge_green);
                break;
            case 2:
                helper.setText(R.id.tv_order_status, "已撤销");
                helper.setTextColor(R.id.tv_order_status, ContextCompat.getColor(mContext, R.color.orderStatusConfirm));
                helper.setImageResource(R.id.im_order_status, R.mipmap.bardge_green);
                break;
            case 3:
                helper.setText(R.id.tv_order_status, "已结账");
                helper.setTextColor(R.id.tv_order_status, ContextCompat.getColor(mContext, R.color.orderStatusWait));
                helper.setImageResource(R.id.im_order_status, R.mipmap.bardge_orange);
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(item.getPayType())) {
            //"PayType":"现金", "银行卡", "主扫微信", "挂账", "会员卡", "被扫支付宝", "被扫微信", "美团券", "大众点评券", "主扫支付宝"
            switch (item.getPayType()) {
                case "1":
                    helper.setText(R.id.payType, "支付方式：现金");
                    break;
                case "2":
                    helper.setText(R.id.payType, "支付状态：银行卡");
                    break;
                case "3":
                    helper.setText(R.id.payType, "支付状态：主扫微信");
                    break;
                case "4":
                    helper.setText(R.id.payType, "支付状态：挂账");
                    break;
                case "5":
                    helper.setText(R.id.payType, "支付状态：会员卡支付");
                    break;
                case "6":
                    helper.setText(R.id.payType, "支付状态：被扫支付宝支付");
                    break;
                case "7":
                    helper.setText(R.id.payType, "支付状态：被扫微信支付");
                    break;
                case "8":
                    helper.setText(R.id.payType, "支付状态：美团券支付");
                    break;
                case "9":
                    helper.setText(R.id.payType, "支付状态：大众点评券支付");
                    break;
                case "10":
                    helper.setText(R.id.payType, "支付状态：主扫支付宝支付");
                    break;
                default:
                    break;
            }
        } else {
            helper.setVisible(R.id.payType, false);
        }
        if (item.getPayIs().equals("1")) {
            if (item.getType().equals("1")) {
                helper.setText(R.id.payStatus, "支付状态：已预付" + "(￥" + item.getActuelPayPrice() + ")");
            } else {
                helper.setText(R.id.payStatus, "支付状态：已结账");
            }

        } else if (item.getPayIs().equals("2")) {
            helper.setText(R.id.payStatus, "支付状态：未支付");
        }
    }
}
