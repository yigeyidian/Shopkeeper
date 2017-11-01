package com.admin.shopkeeper.ui.activity.activityOfBoss.couponDetailTable;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CouponDetailTableAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.CouponDetailTableBean;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
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

public class CouponDetailTableActivity extends BaseActivity<CouponDetailTablePresenter> implements ICouponDetailTableView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coupon_default)
    TextView tVdefault;
    @BindView(R.id.coupon_give)
    TextView tVGiveCoupon;//券发放量
    @BindView(R.id.coupon_consume)
    TextView tVConsumeCoupon;//券使用量
    private PopupWindow laheiPop;
    List<CouponDetailTableBean> data;
    private PopupWindow popupWindow;
    private String titleStr;
    CouponDetailTableAdapter adapter;
    int pageIndex = 1;
    @Override
    protected void initPresenter() {
        presenter = new CouponDetailTablePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_detail_table;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("券明细表");

        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CouponDetailTableAdapter(R.layout.item_coupon_detail_table);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {

        });
        String startDate = Tools.formatLastMonthDate("yyyy-MM-dd");
        String endDate = Tools.formatNowDate("yyyy-MM-dd");

        presenter.getData(pageIndex ,startDate, endDate,"00:00:00", "23:59:59", 0);

//        tvDate.setText(startDate + "至" + endDate);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (resultCode == RESULT_OK) {
            presenter.getCouponInfo();
        }*/
        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
        }

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
    Date endDate;

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
            builder.setTitle("选择时间");
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
                endDate = date;
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
            if (endDate == null) {
                showToast("请选择结束时间");
                return;
            }

            if (Tools.comparaDate(startDate, endDate)) {
                showToast("筛选时间出错");
                return;
            }

            if (Tools.checkDate(startDate, endDate)) {
                showToast("筛选时间不能大于一个月");
                return;
            }
            presenter.getData(pageIndex ,Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", endDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", endDate), typestr.equals("营业时间") ? 0 : 1);
//            tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "至" + Tools.formatNowDate("yyyy-MM-dd", endDate));
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


    int defaultType = 0;

    @OnClick(R.id.coupon_default)
    public void setDefaultClick() {
        defaultType++;

        if (data == null) {
            return;
        }
        if (defaultType % 3 == 1) {
            UIUtils.setDrawableRight(tVdefault, R.mipmap.sort_a_z);
            List<CouponDetailTableBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getCouponPerson()) > Integer.parseInt(o2.getCouponPerson())) {
                    return 1;
                } else if (Integer.parseInt(o1.getCouponPerson()) == Integer.parseInt(o2.getCouponPerson())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setNewData(newData);
        } else if (defaultType % 3 == 2) {
            UIUtils.setDrawableRight(tVdefault, R.mipmap.sort_z_a);
            List<CouponDetailTableBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getCouponPerson()) > Integer.parseInt(o2.getCouponPerson())) {
                    return -1;
                } else if (Integer.parseInt(o1.getCouponPerson()) > Integer.parseInt(o2.getCouponPerson())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tVdefault, R.mipmap.sort_default);
            adapter.setNewData(data);
        }
        UIUtils.setDrawableRight(tVGiveCoupon, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tVConsumeCoupon, R.mipmap.sort_default);
    }

    int nameType = 0;

    @OnClick(R.id.coupon_give)
    public void setNameClick() {
        nameType++;
        if (data == null) {
            return;
        }
        if (nameType % 3 == 1) {
            UIUtils.setDrawableRight(tVGiveCoupon, R.mipmap.sort_a_z);
            List<CouponDetailTableBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getGiveConponVolume()) > Integer.parseInt(o2.getGiveConponVolume())) {
                    return 1;
                } else if (Integer.parseInt(o1.getGiveConponVolume()) == Integer.parseInt(o2.getGiveConponVolume())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setNewData(newData);
        } else if (nameType % 3 == 2) {
            UIUtils.setDrawableRight(tVGiveCoupon, R.mipmap.sort_z_a);
            List<CouponDetailTableBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getGiveConponVolume()) > Integer.parseInt(o2.getGiveConponVolume())) {
                    return -1;
                } else if (Integer.parseInt(o1.getGiveConponVolume()) == Integer.parseInt(o2.getGiveConponVolume())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tVGiveCoupon, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tVdefault, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tVConsumeCoupon, R.mipmap.sort_default);
    }

    int stateType = 0;

    @OnClick(R.id.coupon_consume)
    public void setStateClick() {
        stateType++;
        if (data == null) {
            return;
        }
        if (stateType % 3 == 1) {
            UIUtils.setDrawableRight(tVConsumeCoupon, R.mipmap.sort_a_z);
            List<CouponDetailTableBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getUseCoupon()) > Integer.parseInt(o2.getUseCoupon())) {
                    return 1;
                } else if (Integer.parseInt(o1.getUseCoupon()) == Integer.parseInt(o2.getUseCoupon())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setNewData(newData);
        } else if (stateType % 3 == 2) {
            UIUtils.setDrawableRight(tVConsumeCoupon, R.mipmap.sort_z_a);
            List<CouponDetailTableBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getUseCoupon()) > Integer.parseInt(o2.getUseCoupon())) {
                    return -1;
                } else if (Integer.parseInt(o1.getUseCoupon()) == Integer.parseInt(o2.getUseCoupon())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tVConsumeCoupon, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tVdefault, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tVGiveCoupon, R.mipmap.sort_default);
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
    public void success(List<CouponDetailTableBean> couponDetailTableBeenList) {
        this.data = couponDetailTableBeenList;
        adapter.setNewData(couponDetailTableBeenList);
    }

}
