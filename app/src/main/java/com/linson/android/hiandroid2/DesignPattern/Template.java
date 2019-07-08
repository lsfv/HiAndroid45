package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class Template
{
    public void Run()
    {
        myQuestion myQuestion=new myQuestion();
        LSComponentsHelper.LS_Log.Log_INFO( myQuestion.ShowMe());
    }
    public abstract class QuestionTemplate
    {
        public String ShowMe()
        {
            return asker()+":\r\n     "+body();
        }
        public abstract String asker();
        public abstract String body();
    }

    public class myQuestion extends QuestionTemplate
    {
        @Override
        public String asker()
        {
            return "TT";
        }

        @Override
        public String body()
        {
            return "what is different before template and briage";
        }
    }
}
