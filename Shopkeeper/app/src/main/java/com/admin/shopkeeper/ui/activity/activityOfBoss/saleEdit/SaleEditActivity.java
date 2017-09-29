package com.admin.shopkeeper.ui.activity.activityOfBoss.saleEdit;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class SaleEditActivity extends BaseActivity<SaleEditPresenter> implements ISaleEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_count)
    EditText etCount;

    private SaleBean bean;

    @Override
    protected void initPresenter() {
        presenter = new SaleEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (SaleBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改打折");

            etName.setText(bean.getName());
            etCount.setText(bean.getCount());
        } else {
            toolbar.setTitle("添加打折");
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        String nameStr = etName.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入打折名称");
            return;
        }

        String countStr = etCount.getText().toString().trim();
        if (TextUtils.isEmpty(countStr)) {
            showToast("请输入打折数量");
            return;
        }

        presenter.commit(bean == null ? "" : bean.getGuiId(), nameStr, countStr);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        setResult(RESULT_OK);
        finish();
    }
}
