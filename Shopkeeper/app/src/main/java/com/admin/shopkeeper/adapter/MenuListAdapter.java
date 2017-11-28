package com.admin.shopkeeper.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import es.dmoral.toasty.Toasty;

/**
 * Created by zeng on 2017/4/23.
 */
public class MenuListAdapter extends BaseQuickAdapter<OrderDetailFood, BaseViewHolder> {

    Context mContext;
    boolean mIsVisible;

    public MenuListAdapter(int layoutResId, Context context ,boolean isVisible) {
        super(layoutResId);
        mContext = context;
        mIsVisible = isVisible;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailFood item) {
        TextView tvSale = helper.getView(R.id.etSale) ;
        if(mIsVisible){
           tvSale.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.name, item.getProductNmae());
        if (item.getGiving() > 0) {
            helper.setText(R.id.number, String.valueOf(item.getAmmount()) + "(赠送" + item.getGiving() + "份)");
        } else {
            helper.setText(R.id.number, String.valueOf(item.getCount()));
        }
        helper.setText(R.id.price, item.getPrice() + "");
        helper.setText(R.id.vipPrice, item.getChargeMoney());
        if(item.getSale() >0 ){
            helper.setText(R.id.etSale , String.valueOf(item.getSale()));
        }

        tvSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!App.INSTANCE().getUser().getPermissionValue().contains("quanxiandazhe")) {
                    Toasty.warning(mContext, "没有打折权限", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("设置折扣");
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_bill_da_zhe, null);
                AppCompatImageView imageView = (AppCompatImageView) view1.findViewById(R.id.imageView);
                AppCompatEditText editText = (AppCompatEditText) view1.findViewById(R.id.editText);
                builder.setView(view1);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvSale.setText("");
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int saleNum = 0;
                        if(!TextUtils.isEmpty(editText.getText().toString())){
                            saleNum = Integer.parseInt(editText.getText().toString());
                        }
                        if (saleNum>0 && saleNum < 100) {
                            tvSale.setText(saleNum+"");
                            item.setSale(saleNum);
                            MsgEvent event = new MsgEvent(MsgEvent.oneSale);
                            EventBus.getDefault().post(event);
                        }else{
                            Toasty.warning(mContext, "请输入正确的打折数", Toast.LENGTH_SHORT, true).show();
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
