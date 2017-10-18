package com.admin.shopkeeper.utils;


import android.util.Log;

import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by admin on 2017/7/7.
 */

public class Print {

    public static void rxPrint(String data) {
        Observable.just(data).map(s -> {
            socketDataArrivalHandler(s);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    public static void socketDataArrivalHandler(String data) {
        try {
            //调用打印机打印数据
            String[] enty = null;
            String[] ss = data.split("\\^");
            for (String s : ss) {
                // MessageBox.Show(s);
                enty = s.split("\\$");
                Timber.d(enty.length + "");
                if (enty.length == 12) {
                    //后厨
                    if (enty[3].equals("1")) {
                        if (enty[10].equals("0")) {
                            Thread.sleep(3000);
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            boolean t = p.Print();
//                            if (t) {
//                                //修改菜品打印状态
////                                string SocketType = System.Configuration.ConfigurationManager.AppSettings["SocketType"];
////                                SendData("A*" + SocketType + "*" + enty[4] + "@1*AS05#");
//                            }
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint(enty[4])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        } else {
                            Thread.sleep(3000);
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            boolean t = p.Print();
//                            if (t) {
//                                //修改菜品打印状态
////                                string SocketType = System.Configuration.ConfigurationManager.AppSettings["SocketType"];
////                                SendData("A*" + SocketType + "*" + enty[4] + "@1*AS05#");
//                            }
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint(enty[4])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                    }
                    //催菜
                    if (enty[3].equals("0")) {
                        if (enty[10].equals("0")) {
                            Thread.sleep(1000);
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            Thread.sleep(1000);
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //前台打印
                    if (enty[3].equals("2")) {
                        if (enty[10].equals("0")) {
                            Thread.sleep(1000);
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            Thread.sleep(1000);
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //帐单
                    if (enty[3].equals("3")) {
                        if (enty[10].equals("0")) {
                            //MessageBox.Show("接收长度" + data.Length);
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            //MessageBox.Show("接收长度" + data.Length);
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //外卖账单
                    if (enty[3].equals("7")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //快餐账单
                    if (enty[3].equals("8")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //退菜
                    if (enty[3].equals("4")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //交班单
                    if (enty[3].equals("5")) {
                        if (enty[10].equals("0")) {
                            Timber.d("true" + enty[3]);
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            Timber.d("false");
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        }
                    }
                    //外卖或者快餐
                    if (enty[3].equals("6")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            boolean t = p.Print();
//                            if (t) {
//                                //修改菜品打印状态
////                                string SocketType = System.Configuration.ConfigurationManager.AppSettings["SocketType"];
////                                SendData("A*" + SocketType + "*" + enty[4] + "@1*AS05#");
//                            }
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint(enty[4])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        } else {
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            boolean t = p.Print();
//                            if (t) {
//                                //修改菜品打印状态
////                                string SocketType = System.Configuration.ConfigurationManager.AppSettings["SocketType"];
////                                SendData("A*" + SocketType + "*" + enty[4] + "@1*AS05#");
//                            }
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint(enty[4])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                    }
                    //排号打印
                    if (enty[3].equals("9")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();
                        } else {
                            PrintClass p = new PrintClass(enty[0], enty[3], enty[11], enty[1], enty[2], enty[5], enty[6], enty[7], enty[8], enty[9]);
                            p.Print();

                        }
                    }
                }
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }
}
