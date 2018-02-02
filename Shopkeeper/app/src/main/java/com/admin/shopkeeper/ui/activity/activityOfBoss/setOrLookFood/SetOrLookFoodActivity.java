package com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SetOrLookFoodAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SetFoodCouponDialog;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.saleEdit.SaleEditActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class SetOrLookFoodActivity extends BaseActivity<SetOrLookFoodPresenter> implements ISetOrLookFoodView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SetOrLookFoodAdapter setOrLookFoodAdapter;
    private PopupWindow laheiPop;
    private CouponLineDownBean bean;
    private String[] shopName;
    List shopNameList = new ArrayList();
    List<FoodBean> foods;
    List<MenuTypeEntity> types;
    List<FoodBean> selectFoods;
    List<MenuTypeEntity> selectFoodTypes;

    @Override
    protected void initPresenter() {
        presenter = new SetOrLookFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_or_look_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("设置或查看设置商品");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (CouponLineDownBean) getIntent().getSerializableExtra("bean");
        if (bean.getMerchantName() != null) {
            shopName = bean.getMerchantName().split(",");
        }

        for (int i = 0; i < shopName.length; i++) {
            ShopBean shopBean = new ShopBean();
            shopBean.setName(shopName[i]);
            shopNameList.add(shopBean);
        }
        bean.getShopId();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        setOrLookFoodAdapter = new SetOrLookFoodAdapter(R.layout.item_set_or_look_food);
        recyclerView.setAdapter(setOrLookFoodAdapter);
        if (shopNameList != null)
            setOrLookFoodAdapter.setNewData(shopNameList);
        setOrLookFoodAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_set_food) {
                    if (foods == null || foods.size() == 0) {
                        presenter.getFood();
                    } else {
                        SetFoodCouponDialog.Builder builder = new SetFoodCouponDialog.Builder(SetOrLookFoodActivity.this, foods, R.style.setFoodDialogStyle);
//                    builder.setName(item.getProductName());
                        builder.setButtonClick(new SetFoodCouponDialog.OnButtonClick() {
                            @Override
                            public void onBtnClick(int i) {
//                            item.setCount(i);
//                            helper.setText(R.id.item_count, i + "");
                            }

                            @Override
                            public void onCancel() {
//                            checkBox.setChecked(false);
                                //item.setCount(0);
                                //helper.setText(R.id.item_count, "未添加");
                            }
                        });
                        builder.creater().show();
                    }
                } else if (view.getId() == R.id.item_look_food) {
                    Toasty.warning(SetOrLookFoodActivity.this, "还点我").show();
                }
            }
        });
    }

    public void showFoodPop(SaleBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.delete(bean);
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, SaleEditActivity.class);
            intent.putExtra("bean", bean);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
        });

        laheiPop.setOutsideTouchable(true);
        laheiPop.setFocusable(true);
        laheiPop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        laheiPop.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        laheiPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData();
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }


    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        if (laheiPop != null) {
            laheiPop.dismiss();
        }
    }

    @Override
    public void success(List<SaleBean> datas) {

    }

    @Override
    public void getFoodTypeSuccess(List<MenuTypeEntity> foodTypes) {

    }

    @Override
    public void getFoodSuccess(List<FoodBean> foods) {
        this.foods = new ArrayList<>(foods);
        FoodBean bean = new FoodBean();
        bean.setProductName("全选");
        this.foods.add(0, bean);
    }


}
