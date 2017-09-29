package com.admin.shopkeeper.ui.activity.activityOfBoss.addCoupon;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.EditFoodTypeAdapter;
import com.admin.shopkeeper.adapter.ProductAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.ListDialog;
import com.admin.shopkeeper.dialog.MutiSelectDialog;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.ProductBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCouponActivity extends BaseActivity<AddCouponPresenter> implements IAddCouponView,
        DatePicker.OnDateChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coupon_type)
    RadioGroup rgCouponType;
    @BindView(R.id.coupon_name)
    EditText etCouponName;
    @BindView(R.id.coupon_need)
    EditText etCouponNeedMoney;
    @BindView(R.id.coupon_give)
    EditText etCouponGiveMoney;
    @BindView(R.id.coupon_start_date)
    TextView tvCouponStartDate;
    @BindView(R.id.coupon_end_date)
    TextView tvCouponEndDate;
    @BindView(R.id.coupon_detail)
    EditText etCouponDetail;
    @BindView(R.id.radioGroup_waiMai)
    RadioGroup rgWaiMai;
    @BindView(R.id.use_type_text)
    TextView spinnerType;
    @BindView(R.id.coupon_money_limit)
    EditText etLimit;
    //最大购买量
    @BindView(R.id.coupon_max)
    EditText etMax;
    //退货提前天数
    @BindView(R.id.coupon_days)
    EditText etDays;
    //积分
    @BindView(R.id.coupon_jiFen)
    EditText etJifen;
    //数量
    @BindView(R.id.coupon_nums)
    EditText etNums;
    @BindView(R.id.edit_product)
    Spinner spinner;
    @BindView(R.id.ll_prduct)
    LinearLayout llProduct;

    private String titleStr;

    private int year, month, day;
    //在TextView上显示的字符
    private StringBuffer date;
    //private final String[] useTypes = {"预定", "扫码点餐", "外卖", "快餐"};

    private List<MutiBean> useTypes = new ArrayList<>();
    private CouponManageBean bean;
    private ProductAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new AddCouponPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_coupon;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        date = new StringBuffer();
        titleStr = getIntent().getStringExtra(Config.PARAM1);
        bean = (CouponManageBean) getIntent().getSerializableExtra(Config.PARAM2);

        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        if (bean == null) {
            toolbar.setTitle("新增券");

            ((RadioButton) rgCouponType.getChildAt(0)).setChecked(true);
            ((RadioButton) rgWaiMai.getChildAt(0)).setChecked(true);
        } else {
            toolbar.setTitle("编辑券");

            etCouponName.setText(bean.getName());
            etCouponNeedMoney.setText(bean.getSumPrice() + "");
            etCouponGiveMoney.setText(bean.getPrice() + "");
            tvCouponStartDate.setText(bean.getBeginTime());
            tvCouponEndDate.setText(bean.getEndTime());
            etNums.setText(bean.getCounts());
            etDays.setText(bean.getCode());

            if (bean.getTypeId().equals("1")) {
                ((RadioButton) rgCouponType.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgCouponType.getChildAt(1)).setChecked(true);
            }
        }
        setSupportActionBar(toolbar);

        useTypes.add(new MutiBean("预定", false, 1));
        useTypes.add(new MutiBean("外卖", false, 3));
        useTypes.add(new MutiBean("快餐", false, 4));
        useTypes.add(new MutiBean("扫码点餐", false, 5));

//        foodTypeAdapter = new EditFoodTypeAdapter(this);
//        foodSpinner.setAdapter(foodTypeAdapter);

        if (titleStr.equals("团购券管理") || titleStr.equals("商品券管理")) {
            llProduct.setVisibility(View.VISIBLE);

            adapter = new ProductAdapter(this);
            spinner.setAdapter(adapter);

            presenter.getProductData();
        } else {
            llProduct.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                doSubmit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    String useType;

    @OnClick(R.id.use_type_text)
    public void sizeClick() {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("券适用类型");
        builder.setReasons(useTypes);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {


            @Override
            public void onOk(String text, String value) {
                useType = value;
                spinnerType.setText(text);
            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.coupon_start_date)
    public void SetDateClick() {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tvCouponStartDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }

    @OnClick(R.id.coupon_end_date)
    public void SetEndDateClick() {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tvCouponEndDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }

    private void doSubmit() {
        String nameStr = etCouponName.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入券名称");
            return;
        }
        String needStr = etCouponNeedMoney.getText().toString().trim();
        if (TextUtils.isEmpty(needStr)) {
            showToast("请输入满足金额");
            return;
        }
        String giveMoney = etCouponGiveMoney.getText().toString().trim();
        if (TextUtils.isEmpty(giveMoney)) {
            showToast("请输入赠送金额");
            return;
        }
        String startDate = tvCouponStartDate.getText().toString().trim();
        if (TextUtils.isEmpty(startDate)) {
            showToast("请选择开始日期");
            return;
        }
        String endDate = tvCouponEndDate.getText().toString().trim();
        if (TextUtils.isEmpty(endDate)) {
            showToast("请选择结束日期");
            return;
        }
        String detail = etCouponDetail.getText().toString().trim();
        if (TextUtils.isEmpty(detail)) {
            showToast("请输入详细信息");
            return;
        }
        int typeCoupon = ((RadioButton) rgCouponType.getChildAt(0)).isChecked() ? 1 : 2;
        int typeWaimai = 0;
        if (((RadioButton) rgWaiMai.getChildAt(0)).isChecked()) {
            typeWaimai = 1;
        } else if (((RadioButton) rgWaiMai.getChildAt(1)).isChecked()) {
            typeWaimai = 2;
        } else if (((RadioButton) rgWaiMai.getChildAt(2)).isChecked()) {
            typeWaimai = 3;
        }else{
            typeWaimai = 4;
        }
        String limitStr = etLimit.getText().toString().trim();
        if (TextUtils.isEmpty(limitStr)) {
            showToast("请输入金额限制");
            return;
        }
        String maxStr = etMax.getText().toString().trim();
        if (TextUtils.isEmpty(maxStr)) {
            showToast("请输入最大购买数量");
            return;
        }
        String dayStr = etDays.getText().toString().trim();
        if (TextUtils.isEmpty(dayStr)) {
            showToast("请输入退货提前天数");
            return;
        }
        String jiFenStr = etDays.getText().toString().trim();
        if (TextUtils.isEmpty(jiFenStr)) {
            showToast("请输入积分");
            return;
        }
        String numStr = etNums.getText().toString().trim();
        if (TextUtils.isEmpty(numStr)) {
            showToast("请输入优惠券数量");
            return;
        }

        if (titleStr.equals("优惠券管理")) {
            presenter.submit(nameStr, needStr, giveMoney, startDate, endDate, detail, typeCoupon, typeWaimai, useType, limitStr, maxStr, dayStr, jiFenStr, numStr);
        } else if (titleStr.equals("团购券管理")) {


            ProductBean productBean = (ProductBean) spinner.getSelectedItem();
            if (productBean == null) {
                showFailToast("请绑定商品");
                return;
            }

            presenter.submitGroup(nameStr, needStr, giveMoney, startDate, endDate, detail, typeCoupon, typeWaimai, useType,
                    limitStr, maxStr, dayStr, jiFenStr, numStr, productBean.getName(), productBean.getId());
        } else {//商品券

            ProductBean productBean = (ProductBean) spinner.getSelectedItem();
            if (productBean == null) {
                showFailToast("请绑定商品");
                return;
            }

            presenter.submitCoupon(nameStr, needStr, giveMoney, startDate, endDate, detail, typeCoupon, typeWaimai, useType,
                    limitStr, maxStr, dayStr, jiFenStr, numStr, productBean.getName(), productBean.getId());
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void productsuccess(List<ProductBean> productBeen) {
        if (productBeen != null && productBeen.size() > 0) {
            adapter.setData(productBeen);
            spinner.setSelection(0);
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }
}
