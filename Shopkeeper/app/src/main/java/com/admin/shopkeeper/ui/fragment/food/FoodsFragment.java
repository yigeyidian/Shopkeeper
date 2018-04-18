package com.admin.shopkeeper.ui.fragment.food;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodAdapter;
import com.admin.shopkeeper.adapter.MenuClassAdapter;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.ui.activity.foodsList.FoodsListPresenter;
import com.admin.shopkeeper.ui.activity.foodsList.IFoodsListView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import timber.log.Timber;

public class FoodsFragment extends BaseFragment<FoodsListPresenter> implements IFoodsListView {

    @BindView(R.id.leftRecyclerView)
    RecyclerView left;
    @BindView(R.id.rightRecyclerView)
    RecyclerView rigth;
    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;

    private MenuClassAdapter menuAdapter;

    private FoodAdapter foodAdapter;

    public static FoodsFragment newInstance() {
        FoodsFragment fragment = new FoodsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_foods;
    }

    @Override
    protected void initPresenter() {
        presenter = new FoodsListPresenter(getActivity(), this);
        presenter.init();
    }


    @Override
    public void initView() {
        initPTRLayout();
        initAdapter();
        presenter.getDBFood();
    }

    private void initPTRLayout() {
        MaterialHeader header = new MaterialHeader(getActivity());
        int c[] = {ContextCompat.getColor(getActivity(), R.color.colorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrLayout.setDurationToCloseHeader(1000);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);
        ptrLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getFoods();
                presenter.getMeal();
            }
        });
    }

    private void initAdapter() {
        menuAdapter = new MenuClassAdapter(R.layout.item_menu_classification);

        left.setLayoutManager(new LinearLayoutManager(getActivity()));
        left.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((adapter, view, position) -> {


            MenuTypeEntity type = menuAdapter.getItem(position);
            assert type != null;
            if (!type.isSelected()) {
                for (MenuTypeEntity m : menuAdapter.getData()) {
                    m.setSelected(false);
                }
                type.setSelected(true);
                foodAdapter.setNewData(type.getFoods());

            }
            menuAdapter.notifyDataSetChanged();


        });


        foodAdapter = new FoodAdapter(R.layout.item_food);
        rigth.setLayoutManager(new LinearLayoutManager(getActivity()));
        rigth.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.gray)
                .sizeResId(R.dimen._1sdp)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp).build());
        rigth.setAdapter(foodAdapter);
        left.addOnScrollListener(listener);
        rigth.addOnScrollListener(listener);
    }


    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int topRowVerticalPosition =
                    (recyclerView == null || recyclerView.getChildCount() == 0)
                            ? 0 : recyclerView.getChildAt(0).getTop();
            ptrLayout.setEnabled(topRowVerticalPosition >= 0);
        }
    };

    @Override
    public void error(String message) {
        refreshComplete();
        Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void warning(String s) {
        refreshComplete();
        Toasty.warning(getActivity(), s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void success(List<MenuTypeEntity> menuTypeEntities) {
        if (menuTypeEntities.size() > 0) {
            refreshComplete();
            Toasty.normal(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
            menuTypeEntities.get(0).setSelected(true);
            menuAdapter.setNewData(menuTypeEntities);
            foodAdapter.setNewData(menuTypeEntities.get(0).getFoods());
        }
    }


    @Override
    public void getService() {
        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
    }

    private void refreshComplete() {
        if (ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        presenter.doDestroy();
    }
}
