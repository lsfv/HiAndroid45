package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

//感觉解释器模式的难点在于设计解释器和匹配规则上，而不在于解释器模式。
//所以想了一个死板的规则，测试某个字符串是否是 0～9数字的四则运算，比较好操作
//文法: <expression> ::= <num><operate><num>
//<num> ::= [0~9]
//<operate> ::= +\-\*\/\
//v1.在做解释器文法这种模式之前，还是做一个更直观的非文法的版本，来看看到底区别在哪里。
//很明显，第一版的问题，在于规则 if(isNum(obj1)&&isOperate(obj2)&&isNum(obj3)) hardcode在判断逻辑中，不符合开闭原则
//v2.第二版，已经符合开闭原则，但是看了下解释器模式，还是根本上不是一个东西。解释器的目的不是符合开闭原则，开闭原则只是它的必须要满足的条件。
//解释器的模式是固定的，用来解释一整套语法规则。 终结符和非终结符都视为一个可判断的符号。所以 必须增加一个终结符和非终结符通用的抽象类。
//这样。抽象符号类，终结符号类，非终结符号类。 context(我们会设立的一个处理类).就构成了典型的解释器模式。
//个人感觉，基本不会自己独立去实现这个模式，java,c#都是一种更高级语言，如果非要开启解释模式，那么肯定可以找到某一领域的库，或者干脆换个专用的语言就好了。
//而简单的解释，用if else .类，方法。就可以。不需要一个解释器模式.
public class Interpreter
{
    public void Run()
    {
        String expression1="2+9";
        String expression2="2=9";
        String expression3="a-2";

        CheckExpression checkExpression=new CheckExpression();
        boolean res1= checkExpression.check(expression1);
        boolean res2= checkExpression.check(expression2);
        boolean res3= checkExpression.check(expression3);

        LSComponentsHelper.LS_Log.Log_INFO(res1+"."+res2+"."+res3+".");
    }

    public void Run2()
    {
        String expression1="2+9";
        String expression2="2=9";
        String expression3="a-2";

        CheckExpression2 checkExpression=new CheckExpression2();
        boolean res1= checkExpression.check(expression1);
        boolean res2= checkExpression.check(expression2);
        boolean res3= checkExpression.check(expression3);

        LSComponentsHelper.LS_Log.Log_INFO(res1+"."+res2+"."+res3+".");
    }

    public boolean isNum(String obj)
    {
        boolean res=false;
        try
        {
            Integer.parseInt(obj);
            res=true;
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return res;
    }

    public boolean isOperate(String objstr)
    {
        boolean res=false;
        if(objstr.equals("+")||objstr.equals("-")||objstr.equals("*")||objstr.equals("/"))
        {
            res=true;
        }
        return res;
    }

    //region non-interreter ,regular is with in the check logic. we need kick it out .
    public class CheckExpression
    {
        public boolean check(String expression)
        {
            boolean res=false;
            //num ope num
            String obj1=expression.substring(0,1);
            String obj2=expression.substring(1,2);
            String obj3=expression.substring(2,3);
            if(isNum(obj1)&&isOperate(obj2)&&isNum(obj3))
            {
                res=true;
            }
            return res;
        }
    }
    //endregion

    //region 符合开闭原则,并把元素的判断和整个表达式的判断，都抽象为一个符号，也就是终结符号和非终结符号，也就是可判断正确与否的符号。
    //concext class ，很多时候都喜欢叫做context ,对于中文来说，可能会理解为一个处理类。
    public class CheckExpression2
    {
        public IRegulation mIRegulation=new SimpleCaculateRegulation();
        public boolean check(String expression)
        {
            boolean res=false;
            if(mIRegulation.Check(expression))
            {
                res=true;
            }
            return res;
        }
    }

    //Expression 抽象表达式
    public interface IRegulation
    {
        public Boolean Check(String expression);
    }

    //非终结符表达式。也就是句子。不是某个不可拆分的符号。
    public class SimpleCaculateRegulation implements IRegulation
    {
        @Override
        public Boolean Check(String expression)
        {
            boolean res=false;
            String obj1=expression.substring(0,1);
            String obj2=expression.substring(1,2);
            String obj3=expression.substring(2,3);

            NumRegulation numRegulation=new NumRegulation();
            OperateRegulation operateRegulation=new OperateRegulation();

            if(numRegulation.Check(obj1)&&operateRegulation.Check(obj2)&&numRegulation.Check(obj3))
            {
                res=true;
            }
            return res;
        }
    }

    //终结符表达式,数字
    public class NumRegulation implements IRegulation
    {
        public Boolean Check(String obj)
        {
            boolean res = false;
            try
            {
                Integer.parseInt(obj);
                res = true;
            } catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
            return res;
        }
    }

    //终结符表达式，符号
    public class OperateRegulation implements  IRegulation
    {
        public Boolean Check(String objstr)
        {
            boolean res = false;
            if (objstr.equals("+") || objstr.equals("-") || objstr.equals("*") || objstr.equals("/"))
            {
                res = true;
            }
            return res;
        }
    }


    //endregion

}