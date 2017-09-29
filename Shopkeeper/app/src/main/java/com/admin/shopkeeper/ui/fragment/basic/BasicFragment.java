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
    //员工管理点击
    @OnClick(R.id.staff_manage_ll)
    public void setStaffManageClick() {
        startActivity(StaffManageActivity.class);
    }
    //会员管理点击
    @OnClick(R.id.member_manage_ll)
    public void setMemberManageClick() {
        startActivity(MemberManageActivity.class);
    }

    @OnClick(R.id.basic_retcause)
    public void retcauseClick() {
        startActivity(ReturnCauseActivity.class);
    }

    @OnClick(R.id.basic_commodity)
    public void commodityClick() {
        startActivity(FoodManagerActivity.class);
    }

    @OnClick(R.id.basic_print)
    public void printClick() {
        startActivity(PrintManagerActivity.class);
    }

    @OnClick(R.id.basic_sale)
    public void saleClick() {
        startActivity(SaleActivity.class);
    }

    @OnClick(R.id.basic_weight)
    public void weightClick() {
        startActivity(WeightActivity.class);
    }

    @OnClick(R.id.basic_house)
    public void houseClick() {
        startActivity(HouseActivity.class);
    }

    @OnClick(R.id.basic_desk)
    public void deskClick() {
        startActivity(DeskManagerActivity.class);
    }

    @OnClick(R.id.basic_desktype)
    public void desktypeClick() {
        startActivity(DeskTypeActivity.class);
    }

    @OnClick(R.id.basic_guazhang)
    public void guazhangClick() {
        startActivity(GuaZhangActivity.class);
    }
}
