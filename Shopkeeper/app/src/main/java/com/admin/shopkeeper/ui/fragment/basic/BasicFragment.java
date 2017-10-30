package com.admin.shopkeeper.ui.fragment.basic;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.ui.activity.activityOfBoss.deskmanager.DeskManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.desktype.DeskTypeActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodmanager.FoodManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhang.GuaZhangActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.house.HouseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager.MemberManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.print.PrintManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returncause.ReturnCauseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.sale.SaleActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.staffManager.StaffManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weight.WeightActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BasicFragment extends BaseFragment<BasicPresenter> implements IBasicView {
    @Override
    public void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_basic;
    }

    @Override
    protected void initPresenter() {
        presenter = new BasicPresenter(getActivity(), this);
        presenter.init();
    }
    @OnClick(R.id.basic_guazhang)
    public void guazhangClick() {
        startActivity(GuaZhangActivity.class);
    }

}
