package com.linson.android.hiandroid2.UI.NewCustomView.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.NewCustomView.MyView.viewLib.CircleImage;
import com.linson.android.hiandroid2.UI.NewCustomView.MyView.viewLib.GroupTest;
import com.linson.android.hiandroid2.UI.NewCustomView.MyView.viewLib.MyRecycleView;
import java.util.ArrayList;
import java.util.List;

public class ViewIndex extends AppCompatActivity implements View.OnClickListener
{
    private CircleImage mCiTest1;
    private GroupTest mGrouptest1;
    private GroupTest mGrouptest2;
    private MyRecycleView mMyrecycle;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mCiTest1 = (CircleImage) findViewById(R.id.ci_test1);
        mGrouptest1 = (GroupTest) findViewById(R.id.grouptest1);
        mGrouptest2 = (GroupTest) findViewById(R.id.grouptest2);
        mMyrecycle = (MyRecycleView) findViewById(R.id.myrecycle);

        //set event handler
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_index);
        findControls();

        //
        initMyRecycle();
    }

    private void initMyRecycle()
    {
        //1.difine adaper .set adapter. set layoutmanager.
        List<myadapterData> myadapterData=new ArrayList<>();

        myadapterData myadapterData1=new myadapterData();
        myadapterData1.Gamename="Game1";
        myadapterData1.GamePic=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.h1));
        myadapterData1.GameType=0;

        myadapterData myadapterData2=new myadapterData();
        myadapterData2.Gamename="Game2";
        myadapterData2.GamePic=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.h2));
        myadapterData2.GameType=0;

        myadapterData myadapterData3=new myadapterData();
        myadapterData3.Gamename="Game3";
        myadapterData3.GamePic=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.h3));
        myadapterData3.GameType=1;

        myadapterData myadapterData4=new myadapterData();
        myadapterData4.Gamename="Game4";
        myadapterData4.GamePic=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.h4));
        myadapterData4.GameType=1;

        myadapterData myadapterData5=new myadapterData();
        myadapterData5.Gamename="Game5";
        myadapterData5.GamePic=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.h5));
        myadapterData5.GameType=0;

        myadapterData.add(myadapterData1);
        myadapterData.add(myadapterData2);
        myadapterData.add(myadapterData3);
        myadapterData.add(myadapterData4);
        myadapterData.add(myadapterData5);

        myadapter myadapter=new myadapter(this, myadapterData);
        mMyrecycle.setAdapter(myadapter);

//        //装饰,唯一系统提供的装饰类。
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        mMyrecycle.addItemDecoration(dividerItemDecoration);
        mMyrecycle.addItemDecoration(new EnLargerDecoration(this));

        //RecyclerView.LayoutManager linearLayoutManager= new MyLinearLayoutManager(this);//派生线性布局实现类。
        RecyclerView.LayoutManager linearLayoutManager= new MyCustomLayoutManager();//派生基虚类
        mMyrecycle.setLayoutManager(linearLayoutManager);
    }



//custom crcycle.
//onLayoutChildren smoothScrollToPosition.2个都是需要必须自己复写的，为什么不做成虚函数呢？估计也是怕刚接触，无法写默认实现函数把。不过这样搞一个空白实现，提示信息，也是太迷，还不如虚函数。

    //region extends linearlayoutmanager
    public static class MyLinearLayoutManager extends LinearLayoutManager
    {
        public MyLinearLayoutManager(Context context)
        {
            super(context);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
        {
            super.onLayoutChildren(recycler, state);
            View vv= recycler.getViewForPosition(0);
        }

        @Override
        public void onLayoutCompleted(RecyclerView.State state)
        {
            super.onLayoutCompleted(state);
        }
    }
    //endregion

    //region custom layoutManager
    public static class MyCustomLayoutManager extends RecyclerView.LayoutManager
    {
        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams()
        {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
        {
            //recycler和item view.
            int itemSum=state.getItemCount();
            int left=0,top=0,right=0,bottom=0;
            for(int i=0;i<itemSum;i++)
            {
                View view_item=recycler.getViewForPosition(i);
                addView(view_item);
                measureChildWithMargins(view_item, 0, 0);
                int tempwidth = getDecoratedMeasuredWidth(view_item);
                int tempheight = getDecoratedMeasuredHeight(view_item);


                right=left+tempwidth;
                bottom=top+tempheight;

                layoutDecorated(view_item,left , top,right ,bottom);
                top+=tempheight;
            }
        }
    }
    //endregion

    //region decoration
    //custom decoration，可以看出这里和模式设计里面的装饰模式并不是同一个模式.只是表达装饰的意思，和模式无相关。
    //想做一个进度展示效果.1首先左侧空出50px，放入一个宽度为50px的9宫格图，图有3个类型，分别表示已完成，进行中，未进行。依据adapter中的数据来选择。
    //测试时候发现parent.getChildAt(i)中的i，是指可视的item们的索引。所以猜测内部已经计算出需要显示的item集合了。后面才提供的重载方法。
    //装饰的方法，只能获得RecyclerView和需要显示的item。所以如果需要为特别的item做特殊处理，最好是把特征放入在view上，而不是通过adapter的数据集合。
    //如何放入到view中呢？我们做adapter 的时候，2个方法重载,都没有直接view传入，所以我们可以把view自己放入到holder中，当绑定holder的时候，给view加入一些数据，如tag。
    //这样装饰的时候，就可以知道哪个item需要做特殊处理。
    //如果做图片展示作用。那么固定高度，容纳3个item。但是会有4个，2个显示不完全，我们固定为第2个是中间的。做特殊扩大处理。如果需要处理四周。那么自定义装饰可以处理。
    //接下来还需要对item进行处理。滑动中的item处理不能依靠adapter。adapter只能初始化那些item特殊处理，但是不能根据滑动后处于中间的状态进行处理。
    //这样就需要layoutmanager上场了。
    public static class EnLargerDecoration extends RecyclerView.ItemDecoration
    {
        private Context mContext;

        public EnLargerDecoration(Context context)
        {
            mContext=context;
        }

        //similar margin.
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(60, 0, 0, 0);//必须放在基类函数后面，以便在正常测量后进行修改。因为正常默认会outRect.set(0, 0, 0, 0)
        }

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
        {
            int childCount=parent.getChildCount();
            for(int i=0;i<childCount;i++)
            {
                View item= parent.getChildAt(i);

                Bitmap bitmap_init;
                if((int)item.getTag()==1)
                {
                    bitmap_init=BitmapFactory.decodeResource(mContext.getResources()  ,R.drawable.watingok );
                }
                else
                {
                    bitmap_init=BitmapFactory.decodeResource(mContext.getResources()  ,R.drawable.doingok);
                }
                Bitmap bitmap_start_scale=Bitmap.createScaledBitmap(bitmap_init,60 ,item.getHeight() , true);
                c.drawBitmap(bitmap_start_scale, 0, item.getTop(), new Paint());

            }
            super.onDraw(c, parent, state);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
        {
            super.onDrawOver(c, parent, state);
        }
    }
    //endregion

    //region data struct
    public static class myadapterData
    {
        public String Gamename;
        public Drawable GamePic;
        public int GameType;
    }
    //endregion

    //region adapter
    //onCreateViewHolder:加载view和定义view中的子控件  。onBindViewHolder：数据绑定到view中的子控件。
    //ViewHolder：就是一份子控件清单,为什么不取名叫childenList?太多起名中国人无法直接领会。
    public static class myadapter extends RecyclerView.Adapter
    {
        private Context mContext;
        private List<myadapterData> mdata;

        public myadapter(Context context,List<myadapterData> data)
        {
            mContext=context;
            mdata=data;
        }


        @Override
        public int getItemViewType(int position)
        {
            return mdata.get(position).GameType;//假设gametype为1是个特殊的item。   0是普通的item样式。
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            LSComponentsHelper.LS_Log.Log_INFO("create view");
            if(i==0)
            {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_myrecyle, viewGroup, false);
                return new myviewh(view);
            }
            else
            {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_myrecyle_center, viewGroup, false);
                return new myviewh2(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myviewh1, int i)
        {
            if(myviewh1 instanceof myviewh)
            {
                myviewh vv = (myviewh) myviewh1;
                vv.mTextView.setText(mdata.get(i).Gamename);
                vv.mImageView.setImageDrawable(mdata.get(i).GamePic);
                vv.mView.setTag(mdata.get(i).GameType);
//                vv.mView.setScaleX(0.8f);
//                vv.mView.setScaleY(0.8f);
            }
            else if(myviewh1 instanceof myviewh2)
            {
                myviewh2 vv = (myviewh2) myviewh1;
                vv.mname.setText(mdata.get(i).Gamename);
                vv.mpic.setImageDrawable(mdata.get(i).GamePic);
                vv.mView.setTag(mdata.get(i).GameType);
//                vv.mView.setScaleX(1.2f);
//                vv.mView.setScaleY(1.2f);
            }
        }


        @Override
        public int getItemCount()
        {
            return mdata.size();
        }


        //viewholder.
        public static class myviewh extends RecyclerView.ViewHolder
        {
            private TextView mTextView;
            private ImageView mImageView;
            private View mView;

            public myviewh(@NonNull View itemView)
            {
                super(itemView);
                if(itemView!=null)
                {
                    mTextView = itemView.findViewById(R.id.textView12);
                    mImageView = itemView.findViewById(R.id.imageView17);
                    mView=itemView;
                }
            }
        }


        public static class myviewh2 extends RecyclerView.ViewHolder
        {
            private TextView mname;
            private ImageView mpic;
            private View mView;

            public myviewh2(@NonNull View itemView)
            {
                super(itemView);
                if(itemView!=null)
                {
                    mname = itemView.findViewById(R.id.tv_name);
                    mpic = itemView.findViewById(R.id.iv_pic);
                    mView=itemView;
                }
            }
        }


    }
    //endregion
}