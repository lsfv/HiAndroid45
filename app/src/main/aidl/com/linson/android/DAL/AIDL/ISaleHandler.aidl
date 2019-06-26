// ISaleHandler.aidl
package com.linson.android.DAL.AIDL;

import com.linson.android.DAL.AIDL.AIDL_Sale;
import com.linson.android.DAL.AIDL.IOnCallback;
interface ISaleHandler {
    void addSale(in AIDL_Sale sale);
    List<AIDL_Sale> getList();
    void register(in IOnCallBack hander);
    void unRegister(in IOnCallBack hander);
}