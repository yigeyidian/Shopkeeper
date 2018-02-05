package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FindFoodAdapter;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood.SetOrLookFoodPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class FindFoodCouponDialog extends AppCompatDialog {

    public FindFoodCouponDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private FindFoodCouponDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;

        private String title;
        private String name;
        int page = 1;


        private OnRefresh onRefresh;
        private OnLoadMore onLoadMore;
        private List<FindFoodCouponDownBean> list = new ArrayList<>();
        public FindFoodAdapter adapter;
        SetOrLookFoodPresenter mPresenter;
        public SwipeRefreshLayout refreshLayout;
        public RecyclerView recyclerView;

        public void setOnRefresh(OnRefresh onRefresh) {
            this.onRefresh = onRefresh;
        }
        public void setOnLoadMore(OnLoadMore onLoadMore) {
            this.onLoadMore = onLoadMore;
        }

        public Builder(Context context, List<FindFoodCouponDownBean> foodBeanList, int theme , SetOrLookFoodPresenter presenter) {
            this.context = context;
            this.theme = theme;
            this.list = foodBeanList;
            this.mPresenter = presenter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public FindFoodCouponDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_look_food_coupon_down, null);
            dialog = new FindFoodCouponDialog(context, theme, view);


            TextView tvTitle = (TextView) view.findViewById(R.id.title);

            tvTitle.setText("查看设置商品");

            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
            refreshLayout.setOnRefreshListener(() -> {
                setOnRefresh(onRefresh);
            });
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                    .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                    .color(context.getResources().getColor(R.color.item_line_color))
                    .build());
            adapter = new FindFoodAdapter(R.layout.item_find_food , context);
            recyclerView.setAdapter(adapter);
            adapter.setNewData(list);

            adapter.setOnLoadMoreListener(() -> {
                page++;
                setOnLoadMore(onLoadMore);
            }, recyclerView);







            return dialog;
        }


        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }
    public  interface OnRefresh{
        void onRefresh();
    }
    public  interface OnLoadMore{
        void onLoadMore(int i);
    }

}
