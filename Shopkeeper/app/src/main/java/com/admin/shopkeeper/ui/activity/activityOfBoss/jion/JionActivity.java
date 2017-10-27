package com.admin.shopkeeper.ui.activity.activityOfBoss.jion;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.HandoverAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.HandoverBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.JionDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.IShopCollectionView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.ShopCollectionPresenter;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class JionActivity extends BaseActivity<JionPresenter> implements IJionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sort_1)
    TextView tvSort1;
    @BindView(R.id.sort_2)
    TextView tvSort2;
    @BindView(R.id.sort_3)
    TextView tvSort3;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    int page = 1;
    private PopupWindow popupWindow;
    private HandoverAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new JionPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jion;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("交接班报表");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HandoverAdapter(R.layout.item_handover);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(JionActivity.this, JionDetailActivity.class);
            intent.putExtra("bean", adapter.getItem(position));
            startActivity(intent);
        });
        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate),
                    0, App.INSTANCE().getShopID());
        }, recyclerView);

        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate),
                    0, App.INSTANCE().getShopID());
        });

        startDate = new Date(System.currentTimeMillis());
        entDate = new Date(System.currentTimeMillis());

        presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate),
                0, App.INSTANCE().getShopID());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                showSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Date startDate;
    Date entDate;

    private void showSearch() {
        List<String> types = new ArrayList<>();
        types.add("营业时间");
        types.add("自定义时间");

        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_select_1, null);
        popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView etStartTime = (TextView) laheiView.findViewById(R.id.pop_starttime);
        TextView etEndTime = (TextView) laheiView.findViewById(R.id.pop_endtime);
        TextView tvTimeType = (TextView) laheiView.findViewById(R.id.pop_time_typw);
        TextView tvShop = (TextView) laheiView.findViewById(R.id.tv_shop);

        tvShop.setText(App.INSTANCE().getShopName());


        tvTimeType.setOnClickListener(v -> {
            SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("应付金额");
            builder.setReasons(types);
            builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

                @Override
                public void onOk(String text, int position) {
                    tvTimeType.setText(text);
                }

                @Override
                public void onCancel() {

                }
            });
            builder.creater().show();
        });


        etStartTime.setOnClickListener(v -> {
            String typestr = tvTimeType.getText().toString();

            DatePickDialog dialog = new DatePickDialog(this);
            dialog.setYearLimt(10);
            dialog.setTitle("选择时间");
            dialog.setType(typestr.equals("营业时间") ? DateType.TYPE_YMD : DateType.TYPE_ALL);
            dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
            dialog.setOnChangeLisener(null);
            dialog.setOnSureLisener(date -> {
                startDate = date;
                etStartTime.setText(new SimpleDateFormat(typestr.equals("营业时间") ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss").format(date));
            });
            dialog.show();
        });

        etEndTime.setOnClickListener(v -> {
            String typestr = tvTimeType.getText().toString();
            DatePickDialog dialog = new DatePickDialog(this);
            dialog.setYearLimt(10);
            dialog.setTitle("选择时间");
            dialog.setType(typestr.equals("营业时间") ? DateType.TYPE_YMD : DateType.TYPE_ALL);
            dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
            dialog.setOnChangeLisener(null);
            dialog.setOnSureLisener(date -> {
                entDate = date;

                etEndTime.setText(new SimpleDateFormat(typestr.equals("营业时间") ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss").format(date));
            });
            dialog.show();
        });

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        laheiView.findViewById(R.id.pop_ok).setOnClickListener(v -> {

            String typestr = tvTimeType.getText().toString();

            if (startDate == null) {
                showToast("请选择开始时间");
                return;
            }
            if (entDate == null) {
                showToast("请选择结束时间");
                return;
            }

            if (Tools.comparaDate(startDate, entDate)) {
                showToast("筛选时间出错");
                return;
            }

            if (Tools.checkDate(startDate, entDate)) {
                showToast("筛选时间不能大于一个月");
                return;
            }


            page = 1;
            presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate),
                    typestr.equals("营业时间") ? 0 : 1, App.INSTANCE().getShopID());

            popupWindow.dismiss();
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        popupWindow.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    @OnClick(R.id.sort_1)
    public void sort1Click() {
        UIUtils.setDrawableRight(tvSort1, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_default);
        adapter.setNewData(datas);
    }

    int sort2 = 0;

    @OnClick(R.id.sort_2)
    public void sort2Click() {
        if (datas == null || datas.size() == 0) {
            return;
        }
        sort2++;
        if (sort2 % 3 == 1) {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_a_z);

            List<HandoverBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            });
            adapter.setNewData(newData);
        } else if (sort2 % 3 == 2) {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_z_a);

            List<HandoverBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getPrice() > o2.getPrice() ? -1 : 1;
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_default);
            adapter.setNewData(datas);
        }
        UIUtils.setDrawableRight(tvSort1, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_default);
    }

    int sort3 = 0;

    @OnClick(R.id.sort_3)
    public void sort3Click() {
        if (datas == null || datas.size() == 0) {
            return;
        }
        sort3++;
        if (sort3 % 3 == 1) {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_a_z);

            List<HandoverBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            });
            adapter.setNewData(newData);
        } else if (sort3 % 3 == 2) {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_z_a);

            List<HandoverBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getPrice() > o2.getPrice() ? -1 : 1;
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_default);
            adapter.setNewData(datas);
        }
        UIUtils.setDrawableRight(tvSort1, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_default);
    }

    @Override
    public void error(String msg) {
        showToast(msg);
        adapter.loadMoreEnd();
        refreshLayout.setRefreshing(false);
    }

    List<HandoverBean> datas = new ArrayList<>();

    @Override
    public void success(List<HandoverBean> data) {
        if (page == 1) {
            this.datas.clear();
        }
        this.datas.addAll(data);
        adapter.setNewData(datas);
        if (data.size() < 20) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
        refreshLayout.setRefreshing(false);
    }
}
