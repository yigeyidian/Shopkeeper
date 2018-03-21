package com.admin.shopkeeper.ui.activity.activityOfBoss.collectionStatistics;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class CollectionStatisticsAdapter extends PanelAdapter {

    private static final int TITLE_TYPE = 4;
    private static final int ROOM_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int ORDER_TYPE = 2;

    List<ShopCollectionBean> datas = new ArrayList<>();

    public CollectionStatisticsAdapter() {
    }

    @Override
    public int getRowCount() {
        return datas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    public void setDatas(List<ShopCollectionBean> datas) {
        this.datas = datas;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        PanelViewHolder viewHolder = (PanelViewHolder) holder;
        if (row == 0) {
            setTitleView(viewHolder, column);
        } else {
            ShopCollectionBean bean = datas.get(row - 1);
            setDataView(viewHolder, column, bean);
            viewHolder.textView.setOnClickListener(view -> {
                if (lishener != null) {
                    lishener.onItemClick(row - 1);
                }
            });
        }
    }

    private void setDataView(PanelViewHolder holder, int column, ShopCollectionBean bean) {
        UIUtils.setNullDrawable(holder.textView);
        if (bean == datas.get(datas.size() - 1)) {
            holder.textView.setTextColor(Color.parseColor("#FF8208"));
            holder.ll.setBackgroundColor(Color.parseColor("#FDF8E4"));
            holder.line.setVisibility(View.GONE);
        } else {
            holder.textView.setTextColor(Color.parseColor("#333333"));
            holder.ll.setBackgroundColor(Color.WHITE);
            holder.line.setVisibility(View.VISIBLE);
        }

        switch (column) {
            case 0:
                holder.textView.setText(TextUtils.isEmpty(bean.getNames()) ? "智慧餐厅" : bean.getNames());
                break;
            case 1:
                holder.textView.setText(bean.getDinnerDate());
                break;
            case 2:
                holder.textView.setText(String.valueOf(bean.getTotalMoney()));
                break;
            case 3:
                holder.textView.setText(String.valueOf(bean.getChongzhi()));
                break;
            case 4:
                holder.textView.setText(String.valueOf(bean.getFreeMoney()));
                break;
            case 5:
                holder.textView.setText(String.valueOf(bean.getChargeMoney()));
                break;
        }
    }

    int[] status = {0, 0, 0, 0};

    private void setTitleView(PanelViewHolder holder, int column) {
        holder.textView.setTextColor(Color.parseColor("#888888"));
        holder.ll.setBackgroundColor(Color.parseColor("#f4f4f4"));
        holder.line.setVisibility(View.GONE);
        switch (column) {
            case 0:
                holder.textView.setText("商家名称");
                break;
            case 1:
                holder.textView.setText("统计时间");
                break;
            case 2:
                holder.textView.setText("销售收入");
                break;
            case 3:
                holder.textView.setText("充值收入");
                break;
            case 4:
                holder.textView.setText("优惠金额");
                break;
            case 5:
                holder.textView.setText("销售实收");
                break;
        }

        if (column < 2) {
            UIUtils.setNullDrawable(holder.textView);
            return;
        }
        if (status[column - 2] == 0) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
        }

        if (status[column - 2] % 3 == 0) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
        } else if (status[column - 2] % 3 == 1) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_a_z);
        } else if (status[column - 2] % 3 == 2) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_z_a);
        }

        holder.textView.setOnClickListener(view -> {
            if (lishener != null) {
                status[column - 2]++;
                for (int i = 0; i < status.length; i++) {
                    if (i != column - 2) {
                        status[i] = 0;
                    }
                }
                if (status[column - 2] % 3 == 0) {
                    UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
                } else if (status[column - 2] % 3 == 1) {
                    UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_a_z);
                } else if (status[column - 2] % 3 == 2) {
                    UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_z_a);
                }

                lishener.onSort(column, status[column - 2]);
            }
        });
    }

    OnItemClickLishener lishener;

    public void setOnItemClickListener(OnItemClickLishener listener) {
        this.lishener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PanelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_panel, parent, false));
    }

    public int getItemViewType(int row, int column) {
        if (column == 0) {
            //name
            return ROOM_TYPE;
        }
        if (row == 0) {
            //标题
            return DATE_TYPE;
        }
        return ORDER_TYPE;
    }

    private static class PanelViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        View line;
        LinearLayout ll;

        public PanelViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title);
            line = view.findViewById(R.id.line);
            ll = view.findViewById(R.id.item_ll);
        }
    }

    interface OnItemClickLishener {
        void onItemClick(int raw);

        void onSort(int col, int status);
    }
}
