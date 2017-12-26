package com.admin.shopkeeper.ui.activity.activityOfBoss.setFood;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodManagerAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MealBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.mealEdit.MealEditActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SetFoodActivity extends BaseActivity<SetFoodPresenter> implements ISetFoodView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_food_search)
    AppCompatEditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private FoodManagerAdapter adapter;
    private PopupWindow laheiPop;
    int page = 1;
    private MealBean mealBean;

    @Override
    protected void initPresenter() {
        presenter = new SetFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        mealBean = (MealBean) getIntent().getSerializableExtra("bean");
        toolbar.setTitle("设置菜品");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new FoodManagerAdapter(R.layout.item_foodmanager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getData().get(position));
        });
        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.getData(page, "", mealBean.getId());
        });
        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getData(page, "", mealBean.getId());
        }, recyclerView);
        presenter.getData(page, "", mealBean.getId());

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    ivClear.setVisibility(View.INVISIBLE);
                    adapter.setNewData(datas);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                    searchFood(s.toString().trim());
                }
            }
        });
        etSearch.clearFocus();
    }

    @OnClick(R.id.iv_clear)
    public void clearClick() {
        etSearch.setText("");
    }

    private void searchFood(String str) {
        List<FoodBean> list = new ArrayList<>();
        for (FoodBean bean : datas) {
            if(bean.getProductName().contains(str)) {
                list.add(bean);
            }
        }
        adapter.setNewData(list);
        adapter.loadMoreComplete();
        adapter.loadMoreEnd();
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
        if (laheiPop != null) {
            laheiPop.dismiss();
        }
    }

    List<FoodBean> datas = new ArrayList<>();

    @Override
    public void success(List<FoodBean> foods) {
        if (page == 1) {
            datas.clear();
        }
        datas.addAll(foods);
        adapter.setNewData(datas);
        refreshLayout.setRefreshing(false);
        if (foods.size() < 20) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }

    public void showDeletePop(FoodBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.dialog_set_food, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView setFoodTV = (TextView) laheiView.findViewById(R.id.pop_setFood);
        setFoodTV.setVisibility(View.VISIBLE);
        setFoodTV.setOnClickListener(view -> {
            Intent intent = new Intent(SetFoodActivity.this, SetFoodActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        });
        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });

        /*laheiView.findViewById(R.id.pop_jialiao).setOnClickListener(v -> {
            Intent intent = new Intent(MealTypeManagerActivity.this, SeasonActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        });
        laheiView.findViewById(R.id.pop_shuxing).setOnClickListener(v -> {
            Intent intent = new Intent(MealTypeManagerActivity.this, ShuxingActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        });
        laheiView.findViewById(R.id.pop_kouwei).setOnClickListener(v -> {
            Intent intent = new Intent(MealTypeManagerActivity.this, KouweiActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        });*/
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, MealEditActivity.class);
            intent.putExtra("food", bean);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData(page, "", mealBean.getId());
        }
    }
}
