package sports.demo.com.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import sports.demo.com.myapplication.R;

/**
 * 类描述:多行RadioGroup
 * 作者:aositeluoke
 * 时间:2016年04月13日
 */
public class MultiLineRadioGroup extends RelativeLayout implements View.OnClickListener {
    private List<View> views = new ArrayList<>();
    //记录多选位置
    private List<Integer> choosePosition = new ArrayList<Integer>();
    public int singlePosition = -1;//记录选择的位置
    private ChooseCallBack chooseCallBack;
    private float verticalSpacing; //两行之间的边距
    private float horizontalSpacing; //两列之间的边距
    //选择模式(单选或多选)
    public static final int SINGLE = 0;
    public static final int MULTI = 1;
    private int chooseMode = SINGLE;


    //获取多选的位置
    public List<Integer> getChoosePosition() {
        return choosePosition;
    }

    /**
     * System Service
     */
    private LayoutInflater mInflater;
    private ViewTreeObserver mViewTreeObserber;
    private boolean mInitialized;

    public boolean ismInitialized() {
        return mInitialized;
    }

    public void setmInitialized(boolean mInitialized) {
        this.mInitialized = mInitialized;
    }

    /**
     * 布局宽度
     */
    private int layoutWidth;

    public MultiLineRadioGroup(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public MultiLineRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public MultiLineRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, 0);
    }

    /**
     * initalize instance
     *
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    private void initialize(Context ctx, AttributeSet attrs, int defStyle) {
        mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewTreeObserber = getViewTreeObserver();
        mViewTreeObserber
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (!mInitialized) {
                            mInitialized = true;
                            drawChooseView();
                        }
                    }
                });
        TypedArray mTypeArray = ctx.obtainStyledAttributes(attrs, R.styleable.MultiLineRadioGroup, 0, 0);
        horizontalSpacing = mTypeArray.getDimension(R.styleable.MultiLineRadioGroup_horizontalSpacing, 0);
        verticalSpacing = mTypeArray.getDimension(R.styleable.MultiLineRadioGroup_verticalSpacing, 0);
        chooseMode = mTypeArray.getInteger(R.styleable.MultiLineRadioGroup_chooseMode, SINGLE);
        mTypeArray.recycle();
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    /**
     * onSizeChanged
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        layoutWidth = w;
    }

    public void drawChooseView() {
        if (!mInitialized) {
            return;
        }
        removeAllViews();
        singlePosition=-1;
        int leftId = 0;//左边控件id
        int listIndex = 1;
        float lineWith = getPaddingLeft() + getPaddingRight();//当前行宽度
        for (View item : views) {
            item.setId(listIndex);
            item.setOnClickListener(this);
            LayoutParams params = (LayoutParams) item.getLayoutParams();
            if (params == null)
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            //获取item的宽度
            int w = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int h = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.EXACTLY);
            item.measure(w, h);
            float itemWith = item.getMeasuredWidth();
            if (layoutWidth <= lineWith + itemWith) {
                //换行
                params.addRule(RelativeLayout.BELOW, leftId);
                if (leftId != 0)
                    params.setMargins(0, this.findViewById(leftId).getHeight() + (int) verticalSpacing, 0, 0);
                leftId = item.getId();
                lineWith = getPaddingLeft() + getPaddingRight();
            } else {
                //不需要换行
                if (listIndex == 1) {
                    //第一个
                    leftId = item.getId();
                } else {
                    params.setMargins(this.findViewById(leftId).getHeight() + (int) horizontalSpacing, 0, 0, 0);
                    lineWith += this.findViewById(leftId).getWidth();
                    params.addRule(RelativeLayout.ALIGN_TOP, leftId);
                    params.addRule(RelativeLayout.RIGHT_OF, leftId);
                    leftId = item.getId();
                }

            }
            if (item instanceof RadioButton && ((RadioButton) item).isChecked()) {
                singlePosition = item.getId() - 1;
                if (chooseCallBack != null)
                    chooseCallBack.onChoose(this,(RadioButton) item, singlePosition,false);
            }
            addView(item, params);
            lineWith += itemWith+horizontalSpacing;
            listIndex++;
        }

    }

    //设置所有为未选中
    private void setFalse() {
        for (View item : views) {
            ((RadioButton) item).setChecked(false);
        }
    }

    //设置选中
    private void setTrue(View view) {
        if (chooseMode == SINGLE) {
            setFalse();
            ((RadioButton) view).setChecked(true);
        } else {
            if (choosePosition.contains(Integer.valueOf(view.getId() - 1)))
                choosePosition.remove(Integer.valueOf(view.getId() - 1));
            else
                choosePosition.add(Integer.valueOf(view.getId() - 1));
        }

    }

    public void addRadioButton(RadioButton mRadioButton) {
        //如果是多选模式,只能返回
        if (chooseMode == MULTI)
            return;
        // mRadioButtons.add(mRadioButton);
        views.add(mRadioButton);
        drawChooseView();
    }

    public void deleteAllViews() {
        views.clear();
        removeAllViews();
        drawChooseView();
    }

    public void addCheckBox(CheckBox mCheckBox) {
        //如果是单选模式return
        if (chooseMode == SINGLE)
            return;
        views.add(mCheckBox);
        drawChooseView();
    }


    @Override
    public void onClick(View v) {
        setTrue(v);
        int position = v.getId() - 1;
        if (chooseCallBack == null)
            return;
        if (chooseMode == SINGLE) {
            if (singlePosition == position){
                chooseCallBack.onChooseBack(this, (RadioButton) v, true);
                return;
            }

            singlePosition = position;
            chooseCallBack.onChoose(this, (RadioButton) v, position,true);
        } else {
            chooseCallBack.onMultiChoose((CheckBox) v, choosePosition);
        }
    }

    public void setChooseCallBack(ChooseCallBack chooseCallBack) {
        this.chooseCallBack = chooseCallBack;
    }

    public interface ChooseCallBack {
        void onChoose(MultiLineRadioGroup multiLineRadioGroup, RadioButton view, int position, boolean isTrigger);

        void onMultiChoose(CheckBox view, List<Integer> list);
        void  onChooseBack(MultiLineRadioGroup multiLineRadioGroup, RadioButton view, boolean isTrigger);
    }

    public int getChooseMode() {
        return chooseMode;
    }

    public void setChooseMode(int chooseMode) {
        this.chooseMode = chooseMode;
    }

    public float getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(float verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public float getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public void setHorizontalSpacing(float horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

}
