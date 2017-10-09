package com.admin.shopkeeper.ui.activity.activityOfBoss.wechat;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.ListDialog;
import com.admin.shopkeeper.dialog.MutiSelectDialog;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.WechatBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WechatActivity extends BaseActivity<WechatPresenter> implements IWechatView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_center)
    EditText etCenter;
    @BindView(R.id.edit_order)
    EditText etOrder;
    @BindView(R.id.text_yuding)
    TextView tvYuding;
    @BindView(R.id.text_kuaican)
    TextView tvKuaican;
    @BindView(R.id.text_waimai)
    TextView tvWaimai;
    @BindView(R.id.text_saoma)
    TextView tvTandian;
    @BindView(R.id.jifen_adding)
    RadioGroup rgJifenAdding;
    @BindView(R.id.jifen_exchange)
    RadioGroup rgJifenExchange;

    String yuding = "";
    String kuaican = "";
    String waimai = "";
    String tandian = "";

    List<MutiBean> selectTypeStr = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new WechatPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechat;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("微信规则设置");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        selectTypeStr.add(new MutiBean("积分兑换", false, 1));
        selectTypeStr.add(new MutiBean("优惠券", false, 2));
        selectTypeStr.add(new MutiBean("团购(商品)券", false, 4));
        selectTypeStr.add(new MutiBean("无", false, 5));

        presenter.getData();
    }


    @OnClick(R.id.btn_save)
    public void saveClick() {
        String centerStr = etCenter.getText().toString().trim();
        if (TextUtils.isEmpty(centerStr)) {
            showToast("请输入个人中心");
            return;
        }

        String orderStr = etOrder.getText().toString().trim();
        if (TextUtils.isEmpty(centerStr)) {
            showToast("请输入微信下单");
            return;
        }

        presenter.save(centerStr, orderStr, ((RadioButton) rgJifenAdding.getChildAt(0)).isChecked() ? 1 : 0, ((RadioButton) rgJifenExchange.getChildAt(0)).isChecked() ? 1 : 0, yuding, waimai, kuaican, tandian);
    }

    @OnClick(R.id.ll_yuding)
    public void yudingClick() {
        showSelectDialog("微信预定", 1, yuding);
    }

    @OnClick(R.id.ll_kuaican)
    public void kuaicanClick() {
        showSelectDialog("快餐", 2, kuaican);
    }

    @OnClick(R.id.ll_waimai)
    public void waimaiClick() {
        showSelectDialog("外卖", 3, waimai);
    }

    @OnClick(R.id.ll_saoma)
    public void tandianClick() {
        showSelectDialog("扫码点餐", 4, tandian);
    }

    private void showSelectDialog(String title, int type, String selected) {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle(title);
        builder.setReasons(selectTypeStr);
        builder.setSelect(selected);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, String value) {
                switch (type) {
                    case 1:
                        yuding = value;
                        tvYuding.setText(text);
                        break;
                    case 2:
                        kuaican = value;
                        tvKuaican.setText(text);
                        break;
                    case 3:
                        waimai = value;
                        tvWaimai.setText(text);
                        break;
                    case 4:
                        tandian = value;
                        tvTandian.setText(text);
                        break;
                }
            }
        });
        builder.creater().show();

//        ListDialog.Builder builder = new ListDialog.Builder(this, R.style.OrderDialogStyle);
//        builder.setTitle(title);
//        builder.setReasons(selectTypeStr);
//        builder.setButtonClick(new ListDialog.OnButtonClick() {
//
//            @Override
//            public void onItemClick(int i, String str) {
//                switch (str) {
//                    case "积分兑换":
//                        if (type == 1) {
//                            yuding = 1;
//                            tvYuding.setText(str);
//                        } else if (type == 2) {
//                            kuaican = 1;
//                            tvKuaican.setText(str);
//                        } else if (type == 3) {
//                            waimai = 1;
//                            tvWaimai.setText(str);
//                        } else if (type == 4) {
//                            tandian = 1;
//                            tvTandian.setText(str);
//                        }
//                        break;
//                    case "优惠券":
//                        if (type == 1) {
//                            yuding = 2;
//                            tvYuding.setText(str);
//                        } else if (type == 2) {
//                            kuaican = 2;
//                            tvKuaican.setText(str);
//                        } else if (type == 3) {
//                            waimai = 2;
//                            tvWaimai.setText(str);
//                        } else if (type == 4) {
//                            tandian = 2;
//                            tvTandian.setText(str);
//                        }
//                        break;
//                    case "团购(商品)券":
//                        if (type == 1) {
//                            yuding = 4;
//                            tvYuding.setText(str);
//                        } else if (type == 2) {
//                            kuaican = 4;
//                            tvKuaican.setText(str);
//                        } else if (type == 3) {
//                            waimai = 4;
//                            tvWaimai.setText(str);
//                        } else if (type == 4) {
//                            tandian = 4;
//                            tvTandian.setText(str);
//                        }
//                        break;
//                    case "无":
//                        if (type == 1) {
//                            yuding = 5;
//                            tvYuding.setText(str);
//                        } else if (type == 2) {
//                            kuaican = 5;
//                            tvKuaican.setText(str);
//                        } else if (type == 3) {
//                            waimai = 5;
//                            tvWaimai.setText(str);
//                        } else if (type == 4) {
//                            tandian = 5;
//                            tvTandian.setText(str);
//                        }
//                        break;
//                }
//            }
//        });
//        builder.creater().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void success(WechatBean bean) {
        etCenter.setText(bean.getPersonCenter() + "");
        etOrder.setText(bean.getWeixinOrder() + "");

        etCenter.setSelection(etCenter.length());
        etOrder.setSelection(etOrder.length());

        yuding = bean.getWeixinYuding();
        kuaican = bean.getWeixinKuaican();
        waimai = bean.getWeixinWaimai();
        tandian = bean.getTandian();

        String yudingStr = "";
        String kuaicanStr = "";
        String waimaiStr = "";
        String tandianStr = "";
        for (MutiBean mutiBean : selectTypeStr) {
            if (yuding.contains(mutiBean.getValue() + "")) {
                yudingStr += mutiBean.getText() + ",";
            }
            if (kuaican.contains(mutiBean.getValue() + "")) {
                kuaicanStr += mutiBean.getText() + ",";
            }
            if (waimai.contains(mutiBean.getValue() + "")) {
                waimaiStr += mutiBean.getText() + ",";
            }
            if (tandian.contains(mutiBean.getValue() + "")) {
                tandianStr += mutiBean.getText() + ",";
            }
        }
        if (!TextUtils.isEmpty(yudingStr)) {
            tvYuding.setText(yudingStr.substring(0, yudingStr.length() - 1));
        }
        if (!TextUtils.isEmpty(kuaicanStr)) {
            tvKuaican.setText(kuaicanStr.substring(0, kuaicanStr.length() - 1));
        }
        if (!TextUtils.isEmpty(waimaiStr)) {
            tvWaimai.setText(waimaiStr.substring(0, waimaiStr.length() - 1));
        }
        if (!TextUtils.isEmpty(tandianStr)) {
            tvTandian.setText(tandianStr.substring(0, tandianStr.length() - 1));
        }

        if (bean.getJifenAdding().equals("1")) {
            ((RadioButton) rgJifenAdding.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgJifenAdding.getChildAt(1)).setChecked(true);
        }
        if (bean.getJifenExchange().equals("1")) {
            ((RadioButton) rgJifenExchange.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgJifenExchange.getChildAt(1)).setChecked(true);
        }
    }
}
