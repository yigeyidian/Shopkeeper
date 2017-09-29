package com.admin.shopkeeper.ui.activity.activityOfBoss.boss;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.fragment.basic.BasicFragment;
import com.admin.shopkeeper.ui.fragment.market.MarketFragment;
import com.admin.shopkeeper.ui.fragment.setting.SettingFragment;
import com.admin.shopkeeper.ui.fragment.statement.StatementFragment;
import com.admin.shopkeeper.utils.UIUtils;
import com.admin.shopkeeper.weight.HomeViewPager;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BossMainActivity extends BaseActivity<BossMainPresenter> implements IBossMainView {

    public static final String[] tabs = {"营销", "报表", "基础配置", "设置"};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    HomeViewPager viewPager;
    @BindView(R.id.tab_market)
    TextView tvMarket;
    @BindView(R.id.tab_statement)
    TextView tvStatistics;
    @BindView(R.id.tab_basic)
    TextView tvConfig;
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

        toolbar.setTitle("营销");
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
                        fragment = new MarketFragment();
                        break;
                    case 1:
                        fragment = new StatementFragment();
                        break;
                    case 2:
                        fragment = new BasicFragment();
                        break;
                    case 3:
                        fragment = new SettingFragment();
                        break;
                }
                return fragment;
            }
        });
        viewPager.setNoScroll(true);
    }

    @OnClick(R.id.tab_market)
    public void marketClick() {
        changePage(0);
    }

    @OnClick(R.id.tab_statement)
    public void statementClick() {
        changePage(1);
    }

    @OnClick(R.id.tab_basic)
    public void basicClick() {
        changePage(2);
    }

    @OnClick(R.id.tab_setting)
    public void settingClick() {
        changePage(3);
    }

    private void changePage(int index) {
        if (index == viewPager.getCurrentItem()) {
            return;
        }
        viewPager.setCurrentItem(index, false);
        switch (index) {
            case 0:
                setImmersionBar(true, "营销");
                toolbar.setVisibility(View.VISIBLE);
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_selected));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvConfig.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvMarket, R.mipmap.navbar_marketing_hover);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.navbar_statistics);
                UIUtils.setDrawableTop(tvConfig, R.mipmap.navbar_configuration);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 1:
                toolbar.setVisibility(View.GONE);
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_selected));
                tvConfig.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvMarket, R.mipmap.navbar_marketing);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.navbar_statistics_hover);
                UIUtils.setDrawableTop(tvConfig, R.mipmap.navbar_configuration);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 2:
                setImmersionBar(false, "基础设置");
                toolbar.setVisibility(View.VISIBLE);
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvConfig.setTextColor(getResources().getColor(R.color.navbar_selected));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvMarket, R.mipmap.navbar_marketing);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.navbar_statistics);
                UIUtils.setDrawableTop(tvConfig, R.mipmap.navbar_configuration_hover);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 3:
                setImmersionBar(false, "设置");
                toolbar.setVisibility(View.VISIBLE);
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvConfig.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_selected));

                UIUtils.setDrawableTop(tvMarket, R.mipmap.navbar_marketing);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.navbar_statistics);
                UIUtils.setDrawableTop(tvConfig, R.mipmap.navbar_configuration);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings_hover);
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
}
