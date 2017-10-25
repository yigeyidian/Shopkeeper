package com.admin.shopkeeper.ui.activity.activityOfBoss.returnstatistics;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CollectionAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.IShopCollectionView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.ShopCollectionPresenter;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReturnStatisticsActivity extends BaseActivity<ReturnStatistcsPresenter> implements IReturnStatisticsView {

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
    private PopupWindow popupWindow;
    private CollectionAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new ReturnStatistcsPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return_statistics;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("退菜统计报表");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CollectionAdapter(R.layout.item_collection);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {

        });

        presenter.getData("1999-01-01", Tools.formatNowDate("yyyy-MM-dd"), "00:00:00", "23:59:59", 0);
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

    private void showSearch() {
        List<String> types = new ArrayList<>();
        types.add("营业时间");
        types.add("自定义时间");

        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_select_1, null);
        popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView etStartTime = (TextView) laheiView.findViewById(R.id.pop_starttime);
        TextView etEndTime = (TextView) laheiView.findViewById(R.id.pop_endtime);
        TextView tvTimeType = (TextView) laheiView.findViewById(R.id.pop_time_typw);

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
            selectDate(etStartTime);
        });

        etEndTime.setOnClickListener(v -> {
            selectDate(etEndTime);
        });

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        laheiView.findViewById(R.id.pop_ok).setOnClickListener(v -> {

            String startTime = etStartTime.getText().toString();
            String entTime = etEndTime.getText().toString();
            String typestr = tvTimeType.getText().toString();

            presenter.getData(startTime, entTime, "00:00:00", "23:59:59", typestr.equals(types.get(0)) ? 0 : 1);

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

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private int yy, mm, dd;

    public void selectDate(TextView tv) {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", (dialog, which) -> {
            tv.setText(yy + "-" + (mm + 1) + "-" + dd);
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(yy, mm, dd, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                yy = year;
                mm = monthOfYear;
                dd = dayOfMonth;
            }
        });
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

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getTotalMoney() > o2.getTotalMoney() ? 1 : -1;
            });
            adapter.setNewData(newData);
        } else if (sort2 % 3 == 2) {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_z_a);

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getTotalMoney() > o2.getTotalMoney() ? -1 : 1;
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

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getChargeMoney() > o2.getChargeMoney() ? 1 : -1;
            });
            adapter.setNewData(newData);
        } else if (sort3 % 3 == 2) {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_z_a);

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getChargeMoney() > o2.getChargeMoney() ? -1 : 1;
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
    }

    List<ShopCollectionBean> datas;

    @Override
    public void success(List<ShopCollectionBean> data) {
        this.datas = data;
        adapter.setNewData(data);
    }
}
