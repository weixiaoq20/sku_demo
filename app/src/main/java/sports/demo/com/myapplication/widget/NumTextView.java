package sports.demo.com.myapplication.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class NumTextView extends android.support.v7.widget.AppCompatTextView {
    public NumTextView(Context context) {
        super(context);
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "OswaldMedium.ttf");
        setTypeface(iconfont);
    }

    public NumTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "OswaldMedium.ttf");
        setTypeface(iconfont);
    }

    public NumTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "OswaldMedium.ttf");
        setTypeface(iconfont);
    }
}

