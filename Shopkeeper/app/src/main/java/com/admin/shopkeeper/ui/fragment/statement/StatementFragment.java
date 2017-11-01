package com.admin.shopkeeper.ui.fragment.statement;

import android.content.Intent;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.ui.activity.activityOfBoss.couponDetailTable.CouponDetailTableActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.deskmanager.DeskManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.deskopen.DeskOpenActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.desktype.DeskTypeActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodmanager.FoodManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.FreeActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.giftstatistics.GiftStatisticsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhang.GuaZhangActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.house.HouseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.integralTransactionDetail.IntegralTransactionDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jion.JionActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager.MemberManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberVolumeAnalysis.MemberVolumeAnalysisActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberconsumeDetails.MemberConsumeDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.print.PrintManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeDetail.RechargeDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTranscation.RechargeTranscationActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returncause.ReturnCauseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returnstatistics.ReturnStatisticsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.sale.SaleActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.salestatistics.SaleStatisticsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.ShopCollectionActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.staffManager.StaffManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weight.WeightActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/24.
 */

public class StatementFragment extends BaseFragment<StatementPresenter> implements IStatementView {

    @Override
    public void initView() {

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

    @OnClick(R.id.staff_manage_ll)
    public void shopCollectionClick() {
        Intent intent = new Intent(getActivity(), ShopCollectionActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @OnClick(R.id.member_manage_ll)
    public void chainCollectionClick() {
        Intent intent = new Intent(getActivity(), ShopCollectionActivity.class);
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    @OnClick(R.id.basic_print)
    public void thirdCollectionClick() {
        startActivity(ShopCollectionActivity.class);
    }

    @OnClick(R.id.basic_commodity)
    public void handoverClick() {
        startActivity(JionActivity.class);
    }

    @OnClick(R.id.basic_weight)
    public void freeClick() {
        startActivity(FreeActivity.class);
    }

    @OnClick(R.id.basic_sale)
    public void deskRateClick() {
        startActivity(DeskOpenActivity.class);
    }

    @OnClick(R.id.basic_guazhang)
    public void saleStatisticsClick() {
        startActivity(SaleStatisticsActivity.class);
    }

    @OnClick(R.id.basic_retcause)
    public void giftStatisticsClick() {
        startActivity(GiftStatisticsActivity.class);
    }


    @OnClick(R.id.statement_transaction_ll)
    public void statementTranscationClick() {
        startActivity(RechargeTranscationActivity.class);
    }
    @OnClick(R.id.statement_member_recharge_ll)
    public void statementDetailClick() {
        startActivity(RechargeDetailActivity.class);
    }
    @OnClick(R.id.statement_member_volume_ll)
    public void statementMemberVolumeClick() {
        startActivity(MemberVolumeAnalysisActivity.class);
    }
    @OnClick(R.id.statement_member_consume_detail_ll)
    public void statementMemberConsumeDetailClick() {
        startActivity(MemberConsumeDetailActivity.class);
    }
    @OnClick(R.id.statement_coupon_ll)
    public void couponClick() {
        startActivity(CouponDetailTableActivity.class);
    }
    @OnClick(R.id.statement_integral_ll)
    public void statementIntegralClick() {
        startActivity(IntegralTransactionDetailActivity.class);
    }
}
