package com.admin.shopkeeper.ui.fragment.statement;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.BussinessBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.bussiness.BussinessActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returnanalysis.ReturnAnalysisActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.salerank.SaleRankActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by Administrator on 2017/8/24.
 */

public class StatementFragment extends BaseFragment<StatementPresenter> implements IStatementView {

    @BindView(R.id.state_totle)
    TextView tvTotle;
    @BindView(R.id.state_count)
    TextView tvCount;
    @BindView(R.id.state_price)
    TextView tvPrice;
    @BindView(R.id.state_rate)
    TextView tvRate;
    @BindView(R.id.state_refresh)
    PtrFrameLayout frameLayout;

    @Override
    public void initView() {

        MaterialHeader header = new MaterialHeader(getActivity());
        int c[] = {ContextCompat.getColor(getActivity(), R.color.bosscolorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        frameLayout.setDurationToCloseHeader(1000);
        frameLayout.setHeaderView(header);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_statement;
    }

    @Override
    protected void initPresenter() {
        presenter = new StatementPresenter(getActivity(), this);
        presenter.init();
    }

    @OnClick(R.id.state_bussiness)
    public void bussinessClick() {
        startActivity(BussinessActivity.class);
    }

    @OnClick(R.id.state_sale)
    public void saleClick() {
        startActivity(SaleRankActivity.class);
    }

    @OnClick(R.id.state_return)
    public void returnClick() {
        startActivity(ReturnAnalysisActivity.class);
    }

    @Override
    public void error(String msg) {
        frameLayout.refreshComplete();
        showFailToast(msg);
    }

    @Override
    public void success(List<BussinessBean> bussinessBeen) {
        frameLayout.refreshComplete();
        if (bussinessBeen.size() > 0) {
            BussinessBean bean = bussinessBeen.get(0);
            tvTotle.setText(""+bean.getChargeMoney());
            tvCount.setText("来客人数\n" + bean.getPersonCount());
            tvPrice.setText("客单价(元)\n" + bean.getCount());
            tvRate.setText("优惠金额\n" + (bean.getFreeMoney()));
        }
    }
}
