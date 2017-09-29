package com.admin.shopkeeper.ui.fragment.more;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.LabelAdapter;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.Label;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.ui.activity.messageList.MessageListActivity;
import com.admin.shopkeeper.ui.activity.orderFood.OrderFoodActivity;
import com.admin.shopkeeper.ui.activity.orderList.OrderListActivity;
import com.admin.shopkeeper.ui.activity.person.setting.PersonSettingActivity;
import com.admin.shopkeeper.ui.activity.queue.QueueActivity;
import com.admin.shopkeeper.ui.activity.table.TableOperationActivity;
import com.admin.shopkeeper.utils.SPUtils;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends BaseFragment<MorePresenter> implements IMoreView {

    @BindView(R.id.label)
    RecyclerView recyclerView;
    @BindView(R.id.username)
    AppCompatTextView textView;
    private SpeechSynthesizer speechSynthesizer;

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initPresenter() {
        presenter = new MorePresenter(getActivity(), this);
        presenter.init();


    }

    @Override
    public void initView() {


        List<Label> labels = new ArrayList<>();
        labels.add(new Label("堂点", R.mipmap.person_center_01));
        labels.add(new Label("快餐", R.mipmap.person_center_02));
        labels.add(new Label("店内预定", R.mipmap.person_center_03));
        labels.add(new Label("交班打印", R.mipmap.person_center_07));
        labels.add(new Label("消息", R.mipmap.message_manager));
        labels.add(new Label("订单管理", R.mipmap.order_manager));
        labels.add(new Label("排号", R.mipmap.person_center_04));

        textView.setText(App.INSTANCE().getUser().getName());

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);

        LabelAdapter labelAdapter = new LabelAdapter(R.layout.item_label);
        recyclerView.setAdapter(labelAdapter);
        labelAdapter.setNewData(labels);

        labelAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent;
            switch (position) {
                case 0://堂点
                    intent = new Intent(getActivity(), TableOperationActivity.class);
                    intent.putExtra(Config.PARAM1, TableOperationActivity.P4);
                    startActivity(intent);
                    break;
                case 1://快餐
                    intent = new Intent(getActivity(), OrderFoodActivity.class);
                    intent.putExtra(Config.PARAM1, OrderFoodActivity.P5);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getActivity(), OrderFoodActivity.class);
                    intent.putExtra(Config.PARAM1, OrderFoodActivity.P6);
                    startActivity(intent);//预定
                    break;
                case 3:
                    if (App.INSTANCE().getUser().getPermissionValue().contains("jiaoban")) {
                        presenter.jiaoBanPrint();
                    } else {
                        warning("没有交班权限");
                    }
                    break;
                case 4:
                    intent = new Intent(getActivity(), MessageListActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(getActivity(), OrderListActivity.class);
                    startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(getActivity(), QueueActivity.class);
                    startActivity(intent);
                    break;
            }
        });


    }


    @Override
    public void warning(String s) {
        Toasty.warning(getActivity(), s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void success(String s) {
        Toasty.success(getActivity(), s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void error(String s) {
        Toasty.error(getActivity(), s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void toLogin() {
        SPUtils.getInstance().put(SPUtils.PREFERENCE_LOGIN, false);
        SPUtils.getInstance().put(SPUtils.PREFERENCE_USER, "");
        App.INSTANCE().removeAlias(App.INSTANCE().getShopID(), "PHONE");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
