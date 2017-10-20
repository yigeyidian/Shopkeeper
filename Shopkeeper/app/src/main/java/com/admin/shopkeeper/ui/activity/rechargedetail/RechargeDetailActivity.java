package com.admin.shopkeeper.ui.activity.rechargedetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.RechargeBean;
import com.admin.shopkeeper.entity.RechargeItemBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeDetailActivity extends BaseActivity<RechargeDetailPresenter> implements IRechargeDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_score)
    TextView tvSroce;
    @BindView(R.id.et_money)
    EditText editText;
    @BindView(R.id.tv_product)
    TextView tvProduct;

    private RechargeBean bean;
    private int type;

    @Override
    protected void initPresenter() {
        presenter = new RechargeDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        toolbar.setTitle("充值");
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        bean = (RechargeBean) intent.getSerializableExtra("bean");

        if (type == 0) {
            llMoney.setVisibility(View.VISIBLE);
            llProduct.setVisibility(View.GONE);
        } else {
            llMoney.setVisibility(View.GONE);
            llProduct.setVisibility(View.VISIBLE);
            presenter.getProduct();
        }

        presenter.searchMember(bean.getStaffTel());
    }

    @OnClick(R.id.button)
    public void sumitClick() {
        if (type == 0) {
            String moneyStr = editText.getText().toString();
            if (TextUtils.isEmpty(moneyStr)) {
                showToast("请输入充值金额");
                return;
            }

            presenter.moneyCommit(memberBean.getId(), moneyStr);
        } else {
            if (selectItem == null) {
                showToast("请选择充值产品");
                return;
            }

            presenter.productCommit(memberBean.getId(), selectItem.getGuid());
        }
    }

    @OnClick(R.id.ll_product)
    public void productClick() {

        if(datas == null){
            showToast("获取产品数据失败");
        }

        List<String> names = new ArrayList<>();
        for (RechargeItemBean bean : datas) {
            names.add(bean.getName());
        }

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("选择充值产品");
        builder.setReasons(names);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                tvProduct.setText(text);
                selectItem = datas.get(position);
            }

            @Override
            public void onCancel() {

            }
        });
        builder.creater().show();
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        finish();
    }

    MemberBean memberBean;

    @Override
    public void saleSuccess(MemberBean memberBean) {
        this.memberBean = memberBean;
        tvName.setText(memberBean.getName());
        tvCard.setText(memberBean.getNo());
        tvMoney.setText(memberBean.getMoney() + "");
        tvPhone.setText(memberBean.getPhone());
        tvSroce.setText(memberBean.getScore() + "");
    }

    List<RechargeItemBean> datas;

    RechargeItemBean selectItem;

    @Override
    public void productSuccess(List<RechargeItemBean> rechargeItemBeen) {
        this.datas = rechargeItemBeen;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
