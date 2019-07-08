package com.linson.android.hiandroid2.DesignPattern;


import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.LinkedList;
import java.util.List;

//又是一个貌似看懂结构，但无法理解的模式。
//根据之前的经验，例子非常重要，状态模式也是无法理解，做一个合适的例子，马上理解。恩。看定义，搞清正确场合，制定正确例子。
//Represent an operation to be performed on the elements of an object structure.
//Visitor lets you define a new operation without changing the classes of the elements on which it operates.
//表示作用于某结构所有元素的一个操作，它可以在不改变数据结构的前提下定义作用于这些元素的新的操作。
//fk,不能理解英文，中文自己感觉肯定是直译。会对本质含义存在点不破的可能。再找一个解释吧。
//将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构的前提下可以添加作用于这些元素的新的操作，
//为数据结构中的每个元素提供多种访问方式。它将对数据的操作与数据结构进行分离，是行为类模式中最复杂的一种模式。
//意思是说，某类的数据不变，但是需要增加新的操作，作用于这些数据。请设计出一个模式。
//突然想到之前做的个人基因检测，一个人的基因数据是基本不变，稳定的。但是给不同的团队，不同的研究方向，会得出各自领域的数据和结论。
//预感这个例子会失败。
//失败，没有理解访问者模式的定义。中文翻译，居然也看不懂。做完了例子。一点不像访问者模式。而且没有发现有任何需要改进的地方。
//如果需要增加新的研究机构，直接新设立类就可以啊。没问题啊。。。
//看了一个解释。原来改变的不是研究所。而是研究所的研究方法。。。。
//这里：if(item.minfo1==1)如果研究方法需要改变。那么analyze,就需要改变了。感觉有戏。
//命令模式，也是这样，把方法踢出来，并包含一个执行方法的主体。组成一个类，就变成了命令模式。
//状态模式，同样，把判断的分支，加上执行主体，组成一个类，就变成了状态模式。
//莫非这里，把判断的分支，加上执行的主体，组成一个类。就变成了访问者模式？
//比较无言的一种模式，
// 1.首先被访问者的数据是固定的，一个或多个都可以，不一定要是集合。个人感觉如果只有一个元素，那就是状态模式啊。
//2.数据固定，但是对数据的的信息的处理会经常变动。所以if else ，违法了开闭原则，要像状态模式一样，放到一个类里面。
//3.因为是有多个元素，每个元素，都有至少一个处理类，个人感觉就是 多个状态下的的状态模式。
//绝对是非常少的合适的场景。而且也没有必要去看。看状态模式就好了。一样的思想。
public class Visister
{

    public void Run()
    {
        GeneLibrary linson=new GeneLibrary(Linsongene());


        SkinColorStudio skinColorStudio=new SkinColorStudio();
        HairStudio hairStudio=new HairStudio();

        LSComponentsHelper.LS_Log.Log_INFO(skinColorStudio.analyze(linson));
        LSComponentsHelper.LS_Log.Log_INFO(hairStudio.analyze(linson));
    }

    public void Run2()
    {
        GeneLibrary linson=new GeneLibrary(Linsongene());


        SkinColorStudioV2 skinColorStudioV2=new SkinColorStudioV2();

        LSComponentsHelper.LS_Log.Log_INFO(skinColorStudioV2.analyze(linson));
    }


    //region  第一版，开始没看出来需要升级的地方。后来看解释，变化的是访问元素的方法，会经常变动。
    public List<GeneItem> Linsongene()
    {
        List<GeneItem> ret=new LinkedList<>();
        GeneItem temp=new GeneItem(1, 3, "ok");
        GeneItem temp2=new GeneItem(1, -2, "ok");
        GeneItem temp3=new GeneItem(2, 2, "ok");
        GeneItem temp4=new GeneItem(2, 1, "ok");

        ret.add(temp);
        ret.add(temp2);
        ret.add(temp3);
        ret.add(temp4);

        return  ret;
    }

    public class GeneItem
    {
        public int minfo1;
        public int minfo2;
        public String minfo3;
        public GeneItem (int i,int i2,String i3)
        {
            minfo1=i;
            minfo2=i2;
            minfo3=i3;
        }
    }
    public class GeneLibrary
    {
        public List<GeneItem> mGeneItems;
        public GeneLibrary(List<GeneItem> geneItems)
        {
            mGeneItems=geneItems;
        }
    }

    public interface IStuido
    {
        public String analyze(GeneLibrary geneLibrary);
    }

    public class SkinColorStudio implements IStuido
    {
        @Override
        public String analyze(GeneLibrary geneLibrary)
        {
            int res=0;
            for(GeneItem item :geneLibrary.mGeneItems)
            {
                if(item.minfo1==1)
                {
                    //LSComponentsHelper.LS_Log.Log_INFO(item.minfo2+"");
                    res+=item.minfo2;
                }
                else
                {
                    //LSComponentsHelper.LS_Log.Log_INFO(item.minfo2+"");
                    res-=item.minfo2;
                }
            }
            if(res<=0)
            {
                return  "your sikn is yellow"+res;
            }
            else
            {
                return "your skin is black"+res;
            }
        }
    }

    public class HairStudio implements IStuido
    {
        @Override
        public String analyze(GeneLibrary geneLibrary)
        {
            int res=0;
            for(GeneItem item :geneLibrary.mGeneItems)
            {
                if(item.minfo1==2)
                {
                    res+=item.minfo2;
                }
                else
                {
                    res-=item.minfo2;
                }
            }
            if(res>=0)
            {
                return  "your will be drop you hair in middle"+res;
            }
            else
            {
                return "congratulation ,you will have a good hair in your life!"+res;
            }
        }
    }

    //endregion
    public abstract class AnlyzeItem
    {
        protected GeneItem mGeneItem;
        public AnlyzeItem(GeneItem geneItem)
        {
            mGeneItem=geneItem;
        }
        public abstract int checkME();
    }
    public class SkinAnlyzeItem1v1 extends AnlyzeItem
    {
        public SkinAnlyzeItem1v1(GeneItem geneItem)
        {
            super(geneItem);
        }

        @Override
        public int checkME()
        {
            if(mGeneItem.minfo1==1)
            {
                return mGeneItem.minfo2;
            }
            else
            {
                return -mGeneItem.minfo2;
            }
        }
    }

    public class SkinAnlyzeItem2v1 extends AnlyzeItem
    {
        public SkinAnlyzeItem2v1(GeneItem geneItem)
        {
            super(geneItem);
        }

        @Override
        public int checkME()
        {
            if(mGeneItem.minfo1==1)
            {
                return mGeneItem.minfo2;
            }
            else
            {
                return -mGeneItem.minfo2;
            }
        }
    }

    public class SkinAnlyzeItem3v1 extends AnlyzeItem
    {
        public SkinAnlyzeItem3v1(GeneItem geneItem)
        {
            super(geneItem);
        }

        @Override
        public int checkME()
        {
            if(mGeneItem.minfo1==1)
            {
                return mGeneItem.minfo2;
            }
            else
            {
                return -mGeneItem.minfo2;
            }
        }
    }

    public class SkinAnlyzeItem4v1 extends AnlyzeItem
    {
        public SkinAnlyzeItem4v1(GeneItem geneItem)
        {
            super(geneItem);
        }

        @Override
        public int checkME()
        {
            if(mGeneItem.minfo1==1)
            {
                return mGeneItem.minfo2;
            }
            else
            {
                return -mGeneItem.minfo2;
            }
        }
    }



    public class SkinColorStudioV2 implements IStuido
    {
        @Override
        public String analyze(GeneLibrary geneLibrary)
        {
            int res=0;
            int index=0;
            //这里还是有if，但是注意假设，访问者模式是假设数据不变的。所以这里的if结构是不会在变的。就4个元素。
            //变化的是具体方法。所以把方法踢倒一个类里面。并且把元素也给他。
            for(GeneItem item :geneLibrary.mGeneItems)
            {
                if(index==0)
                {
                    SkinAnlyzeItem1v1 skinAnlyzeItem1v1=new SkinAnlyzeItem1v1(item);
                    res+=skinAnlyzeItem1v1.checkME();
                }
                else if(index==1)
                {
                    SkinAnlyzeItem2v1 skinAnlyzeItem2v1=new SkinAnlyzeItem2v1(item);
                    res+=skinAnlyzeItem2v1.checkME();
                }
                else if(index==2)
                {
                    SkinAnlyzeItem3v1 skinAnlyzeItem3v1=new SkinAnlyzeItem3v1(item);
                    res+=skinAnlyzeItem3v1.checkME();
                }
                else if(index==3)
                {
                    SkinAnlyzeItem4v1 skinAnlyzeItem4v1=new SkinAnlyzeItem4v1(item);
                    res+=skinAnlyzeItem4v1.checkME();
                }
                index++;
            }
            if(res<=0)
            {
                return  "your sikn is yellow:"+res;
            }
            else
            {
                return "your skin is black"+res;
            }
        }
    }
    //region

    //endregion
}