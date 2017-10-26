package com.admin.shopkeeper.ui.activity.activityOfBoss.freedetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.entity.HandoverBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.IFreeView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.IJionDetailView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.JionDetailPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class FreeDetailActivity extends BaseActivity<FreeDetailPresenter> implements IFreeDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_money)
    TextView tvMoney;
    @BindView(R.id.item_info)
    TextView tvInfo;
    @BindView(R.id.item_shop)
    TextView tvShop;

    private FreeBean bean;

    @Override
    protected void initPresenter() {
        presenter = new FreeDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_free_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("交接班报表详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (FreeBean) getIntent().getSerializableExtra("bean");

        tvShop.setText(bean.getNames());
        tvMoney.setText("￥" + String.valueOf(bean.getFreeMoney()));
        tvInfo.setText(bean.getOprate());
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
