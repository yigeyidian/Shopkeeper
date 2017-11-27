package com.admin.shopkeeper.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by zeng on 2017/4/23.
 */
public class MenuListAdapter extends BaseQuickAdapter<OrderDetailFood, BaseViewHolder> {


    public MenuListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailFood item) {
        helper.setText(R.id.name, item.getProductNmae());
        if (item.getGiving() > 0) {
            helper.setText(R.id.number, String.valueOf(item.getAmmount()) + "(赠送" + item.getGiving() + "份)");
        } else {
            helper.setText(R.id.number, String.valueOf(item.getCount()));
        }
        helper.setText(R.id.price, item.getPrice() + "");
        helper.setText(R.id.vipPrice, item.getChargeMoney());
        helper.setText(R.id.etSale , 90+"");
        helper.getView(R.id.etSale).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        AppCompatTextView textView = helper.getView(R.id.describe);
        textView.setText("");
        if (!TextUtils.isEmpty(item.getSeasonName())) {
            textView.append(item.getSeasonName() + "(￥" + item.getSeasonPrice() + ")、");
        }
        if (!TextUtils.isEmpty(item.getProductshuxin())) {
            textView.append(item.getProductshuxin() + "、");
        }
        if (!TextUtils.isEmpty(item.getRemark())) {
            textView.append(item.getRemark() + "、");
        }
    }
}
