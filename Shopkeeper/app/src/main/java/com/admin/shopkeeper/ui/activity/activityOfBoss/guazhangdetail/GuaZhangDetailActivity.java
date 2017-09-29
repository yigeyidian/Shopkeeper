package com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangdetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodSeasonAdapter;
import com.admin.shopkeeper.adapter.GuazhangDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class GuaZhangDetailActivity extends BaseActivity<GuaZhangDetailPresenter> implements IGuaZhangDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private GuaZhangBean bean;
    private GuazhangDetailAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new GuaZhangDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gua_zhang_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("挂账详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (GuaZhangBean) getIntent().getSerializableExtra("bean");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new GuazhangDetailAdapter(R.layout.item_guazhang_detail);
        recyclerView.setAdapter(adapter);

        presenter.getData(bean);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    @Override
    public void success(List<GuazhangDetailBean> datas) {
        adapter.setNewData(datas);
    }
}
