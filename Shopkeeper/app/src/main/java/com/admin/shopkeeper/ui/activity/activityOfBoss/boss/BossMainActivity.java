package com.admin.shopkeeper.ui.activity.activityOfBoss.boss;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.ui.fragment.basic.BasicFragment;
import com.admin.shopkeeper.ui.fragment.market.MarketFragment;
import com.admin.shopkeeper.ui.fragment.setting.SettingFragment;
import com.admin.shopkeeper.ui.fragment.home.HomeFragment;
import com.admin.shopkeeper.ui.fragment.statement.StatementFragment;
import com.admin.shopkeeper.utils.UIUtils;
import com.admin.shopkeeper.weight.HomeViewPager;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BossMainActivity extends BaseActivity<BossMainPresenter> implements IBossMainView {

    public static final String[] tabs = {"首页", "报表", "订单", "营销", "设置"};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    HomeViewPager viewPager;
    @BindView(R.id.tab_home)
    TextView tvHome;
    @BindView(R.id.tab_statement)
    TextView tvStatistics;
    @BindView(R.id.tab_order_list)
    TextView tvOrderList;
    @BindView(R.id.tab_market)
    TextView tvMarket;
    @BindView(R.id.tab_setting)
    TextView tvSetting;

    Map<String, Fragment> fragments = new HashMap<>();


    @Override
    protected void initPresenter() {
        presenter = new BossMainPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_boss_main;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("首页");
        toolbar.setNavigationIcon(R.mipmap.person_advar_man);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabs.length;
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragments.get(tabs[position]);
                if (fragment != null) {
                    return fragment;
                }
                switch (position) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new StatementFragment();
                        break;
                    case 2:
                        fragment = new BasicFragment();
                        break;
                    case 3:
                        fragment = new MarketFragment();
                        break;
                    case 4:
                        fragment = new SettingFragment();
                        break;
                }
                return fragment;
            }
        });
        viewPager.setNoScroll(true);

        presenter.queryUserInfo(App.INSTANCE().getShopID());
    }

    @OnClick(R.id.tab_home)
    public void homeClick() {
        changePage(0);
    }

    @OnClick(R.id.tab_statement)
    public void statementClick() {
        changePage(1);
    }

    @OnClick(R.id.tab_order_list)
    public void orderListClick() {
        changePage(2);
    }

    @OnClick(R.id.tab_market)
    public void marketClick() {
        changePage(3);
    }

    @OnClick(R.id.tab_setting)
    public void settingClick() {
        changePage(4);
    }

    private void changePage(int index) {
        if (index == viewPager.getCurrentItem()) {
            return;
        }
        viewPager.setCurrentItem(index, false);
//        "首页", "报表", "订单", "营销", "设置"
        switch (index) {
            case 0:
                setImmersionBar(true, "首页");
                toolbar.setVisibility(View.VISIBLE);
                tvHome.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home_fill);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 1:
                setImmersionBar(true, "报表");
                toolbar.setVisibility(View.VISIBLE);
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics_fill);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 2:
                setImmersionBar(true, "订单");
                toolbar.setVisibility(View.VISIBLE);
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist_fill);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;
            case 3:
                setImmersionBar(true, "营销");
                toolbar.setVisibility(View.VISIBLE);
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing_fill);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;
            case 4:
                setImmersionBar(false, "设置");
                toolbar.setVisibility(View.VISIBLE);
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_selected_two));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.menu_bar_icon_settings_fill);
                break;
        }
    }

    /**
     * @param isNavigationIcon 是否有头像
     * @param title            toolbar标题
     */
    private void setImmersionBar(boolean isNavigationIcon, String title) {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle(title);
        if (isNavigationIcon) {
            toolbar.setNavigationIcon(R.mipmap.person_advar_man);
        } else {
            toolbar.setNavigationIcon(null);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public void getInfoSuccess(List<BossUserInfo> bossUserInfos) {
        BossUserInfo bossUserInfo = bossUserInfos.get(0);
        if (!TextUtils.isEmpty(bossUserInfos.get(0).getAddress())) {
            App.INSTANCE().setShopAddress(bossUserInfos.get(0).getAddress());
        }
        if (!TextUtils.isEmpty(bossUserInfos.get(0).getShopNames())) {
            App.INSTANCE().setShopName(bossUserInfos.get(0).getShopNames());
        }
        if (!TextUtils.isEmpty(bossUserInfos.get(0).getName())) {
            App.INSTANCE().setUserNameOfBoss(bossUserInfos.get(0).getName());
        }
    }
}
