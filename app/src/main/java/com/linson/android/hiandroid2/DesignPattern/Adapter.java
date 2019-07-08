package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class Adapter
{
    public interface Ichannel
    {
        public int reasePower();
    }

    public static class China220VChannel implements Ichannel
    {
        public int reasePower()
        {
            return 220;
        }
    }

    public static class AdapterAmanican implements Ichannel
    {
        private China220VChannel mChina220VChannel=new China220VChannel();

        @Override
        public int reasePower()
        {
            int v=mChina220VChannel.reasePower();
            v=v-110;
            return v;
        }
    }

    public static class AmanicanVCD
    {
        public String play(Ichannel channel)
        {
            if(channel.reasePower()==110)
            {
                return "the fv is ok.i can play sm";
            }
            else
            {
                return "fv is not ok";
            }
        }
    }

    public static void Run()
    {
        AmanicanVCD myvcd=new AmanicanVCD();
        String result =myvcd.play(new China220VChannel());
        String result2=myvcd.play(new AdapterAmanican());

        LSComponentsHelper.LS_Log.Log_INFO(result+result2);
    }
}
