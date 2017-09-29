package com.admin.shopkeeper.ui.fragment.statement;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.BussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface IStatementView extends IBaseView {
    void error(String msg);

    void success(List<BussinessBean> bussinessBeen);
}
