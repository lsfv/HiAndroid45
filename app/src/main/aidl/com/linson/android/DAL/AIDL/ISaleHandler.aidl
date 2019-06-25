// ISaleHandler.aidl
package com.linson.android.DAL.AIDL;

import com.linson.android.DAL.AIDL.AIDL_Sale;
interface ISaleHandler {
    void addSale(in AIDL_Sale sale);
    List<AIDL_Sale> getList();
}