package com.admin.shopkeeper.ui.activity.activityOfBoss.basicSets;


import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.DaZheDialog;
import com.admin.shopkeeper.dialog.ListDialog;
import com.admin.shopkeeper.dialog.MutiSelectDialog;
import com.admin.shopkeeper.entity.BasicSetBean;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;
import com.bumptech.glide.Glide;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.gyf.barlibrary.ImmersionBar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;

public class BasicSetsActivity extends BaseActivity<BasicSetsPresenter> implements IBasicSetsView, OnPermissionCallback {

    private final String[] priceStrs = {"四舍五入", "原价", "抹零"};
    private final String[] sizeStrs = {"小", "中", "大"};
    private final String[] payStrs = {"现金", "银行卡", "微信支付", "会员卡", "线下支付宝", "线下微信"};
    private final String[] payTypeStrs = {"现金", "银行卡", "微信支付", "挂账", "会员卡", "被扫支付宝", "被扫微信", "美团券", "大众点评券", "主扫支付宝"};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.isPrint)
    AppCompatCheckBox cbPrint;
    @BindView(R.id.isSale)
    AppCompatCheckBox cbSale;
    @BindView(R.id.isShowOfGuest)
    AppCompatCheckBox cdShowOfGuest;
    @BindView(R.id.productSize)
    TextView productSize;
    @BindView(R.id.tv_price_text)
    TextView tvPrice;
    @BindView(R.id.tv_pay_text)
    TextView tvPay;
    @BindView(R.id.tv_pay_type_text)
    TextView tvPayTypeOfShop;
    @BindView(R.id.imageview)
    ImageView imageView;
    @BindView(R.id.password)
    EditText password;
    private PermissionHelper permissionHelper;
    private String imagePath;
    private List<MutiBean> payTypes = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new BasicSetsPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_sets;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("基本设置");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        permissionHelper = PermissionHelper.getInstance(this);
        presenter.getBasicSets();

        payTypes.add(new MutiBean("现金", false, 1));
        payTypes.add(new MutiBean("银行卡", false, 2));
        payTypes.add(new MutiBean("主扫微信", false, 3));
        payTypes.add(new MutiBean("挂账", false, 4));
        payTypes.add(new MutiBean("会员卡", false, 5));
        payTypes.add(new MutiBean("被扫支付宝", false, 6));
        payTypes.add(new MutiBean("被扫微信", false, 7));
        payTypes.add(new MutiBean("美团券", false, 8));
        payTypes.add(new MutiBean("大众点评券", false, 9));
        payTypes.add(new MutiBean("主扫支付宝", false, 10));
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
                if (TextUtils.isEmpty(imagePath)) {
                    submit("");
                } else {
                    presenter.uploadImage(imagePath);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.imageview)
    public void selectImageClick() {
        permissionHelper
                .setForceAccepting(false)// true if you had like force reshowing the permission dialog on Deny (not recommended)
                .request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @OnClick(R.id.ll_money)
    public void priceClick() {
        showDialog("应付金额", Arrays.asList(priceStrs));
    }

    @OnClick(R.id.ll_size)
    public void sizeClick() {
        showDialog("菜品大小", Arrays.asList(sizeStrs));
    }

    @OnClick(R.id.ll_pay)
    public void payClick() {
        showDialog("快速支付", Arrays.asList(payStrs));
    }

    String payTypeValues = "";

    @OnClick(R.id.ll_pay_type)
    public void payTypeClick() {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("券适用类型");
        builder.setReasons(payTypes);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {


            @Override
            public void onOk(String text, String value) {
                payTypeValues = value;
                tvPayTypeOfShop.setText(text);
            }
        });
        builder.creater().show();
    }

    int priceType;
    int payType;
    int sizeType;

    private void showDialog(String title, List<String> list) {
        ListDialog.Builder builder = new ListDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle(title);
        builder.setReasons(list);
        builder.setButtonClick(new ListDialog.OnButtonClick() {

            @Override
            public void onItemClick(int i, String str) {
                switch (str) {
                    case "现金":
                        payType = 1;
                        tvPay.setText(str);
                        break;
                    case "银行卡":
                        payType = 2;
                        tvPay.setText(str);
                        break;
                    case "微信支付":
                        payType = 3;
                        tvPay.setText(str);
                        break;
                    case "会员卡":
                        payType = 5;
                        tvPay.setText(str);
                        break;
                    case "线下支付宝":
                        payType = 6;
                        tvPay.setText(str);
                        break;
                    case "线下微信":
                        payType = 7;
                        tvPay.setText(str);
                        break;
                    case "大":
                        sizeType = 2;
                        productSize.setText(str);
                        break;
                    case "中":
                        sizeType = 1;
                        productSize.setText(str);
                        break;
                    case "小":
                        sizeType = 0;
                        productSize.setText(str);
                        break;
                    case "四舍五入":
                        priceType = 0;
                        tvPrice.setText(str);
                        break;
                    case "原价":
                        priceType = 1;
                        tvPrice.setText(str);
                        break;
                    case "抹零":
                        priceType = 2;
                        tvPrice.setText(str);
                        break;
                }
            }
        });
        builder.creater().show();
    }


    private void submit(String imageName) {
        presenter.commit(imageName, cbPrint.isChecked() ? "1" : "2", sizeType + "", payType + "", cbSale.isChecked() ? "1" : "0",
                priceType + "", cdShowOfGuest.isChecked() ? "1" : "0", password.getText().toString(), payTypeValues);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                imagePath = photos.get(0);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {
                imagePath = photos.get(0);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }

            @Override
            public void onPickFail(String error) {
                showFailToast(error);
                imageView.setImageResource(R.mipmap.list_add_image);
            }

            @Override
            public void onPickCancle() {
                showToast("取消选择");
                imageView.setImageResource(R.mipmap.list_add_image);
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void error(String failed) {
        showFailToast(failed);
    }


    @Override
    public void success(BasicSetBean bean) {
        if (bean.getPrintSet().equals("1")) {
            cbPrint.setChecked(true);
        }

        if (bean.getChenjinDazhe().equals("1")) {
            cbSale.setChecked(true);
        }

        if (bean.getGuestShow().equals("1")) {
            cdShowOfGuest.setChecked(true);
        }

        switch (bean.getPayType()) {
            case "1":
                tvPay.setText("现金");
                payType = 1;
                break;
            case "2":
                tvPay.setText("银行卡");
                payType = 2;
                break;
            case "3":
                tvPay.setText("微信支付");
                payType = 3;
                break;
            case "5":
                tvPay.setText("会员卡");
                payType = 5;
                break;
            case "6":
                tvPay.setText("线下支付宝");
                payType = 6;
                break;
            case "7":
                tvPay.setText("线下微信");
                payType = 7;
                break;
        }

        switch (bean.getJiezhangPay()) {
            case "0":
                tvPrice.setText("四舍五入");
                priceType = 0;
                break;
            case "1":
                tvPrice.setText("原价");
                priceType = 1;
                break;
            case "2":
                tvPrice.setText("抹零");
                priceType = 2;
                break;
        }

        switch (bean.getProductSize()) {
            case "0":
                productSize.setText("小");
                sizeType = 0;
                break;
            case "1":
                productSize.setText("中");
                sizeType = 1;
                break;
            case "2":
                productSize.setText("大");
                sizeType = 2;
                break;
        }

        payTypeValues = bean.getCashPayType();
        String payTypeStr;
        

        if (!TextUtils.isEmpty(bean.getPayImage())) {
            Glide.with(this).load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/pay/" + bean.getPayImage()).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.list_add_image);
        }
        password.setText(bean.getPayPassord());
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        finish();
    }

    @Override
    public void imagesuccess(String name) {
        submit(name);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        PhotoPickUtils.startPick(this, false, 1, new ArrayList<>());
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        showFailToast("权限拒绝");
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {

    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {

    }

    @Override
    public void onNoPermissionNeeded() {

    }

}
