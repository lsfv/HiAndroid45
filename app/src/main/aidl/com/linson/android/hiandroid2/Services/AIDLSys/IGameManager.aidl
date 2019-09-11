// IGameManager.aidl
package com.linson.android.hiandroid2.Services.AIDLSys;

// Declare any non-default types here with import statements
import com.linson.android.hiandroid2.Services.AIDLSys.MyGame;


interface IGameManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,double aDouble, String aString);
    int getdouble(int num);

    MyGame modifyGame(in MyGame game);
}