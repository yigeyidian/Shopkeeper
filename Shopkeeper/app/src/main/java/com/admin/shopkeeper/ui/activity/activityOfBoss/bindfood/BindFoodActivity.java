package com.admin.shopkeeper.ui.activity.activityOfBoss.bindfood;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.SaleBean;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class BindFoodActivity extends BaseActivity<BindFoodPresenter> implements IBindFoodView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SaleBean bean;


    @Override
    protected void initPresenter() {
        presenter = new BindFoodPresenter(this,this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("查看设置商品");
        toolbar.setNavigationIcon(R.mipmap.person_advar_man);
        setSupportActionBar(toolbar);

        bean = (SaleBean) getIntent().getSerializableExtra("bean");

        presenter.getFood(bean,"");

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

    @Override
    public void error(String msg) {

    }
}
