package sports.demo.com.myapplication;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;


/**
 * Summary ：加载网络图片使用的工具类，基于Glide
 * Created by zhangdm on 2016/2/20.
 */
public class GlideUtil {

    public static final String TAG = "GlideUtil";

    /**
     * Glide的请求管理器类
     */
    private static RequestManager mRequestManager;
    private static Context mContext;

    public static RequestManager getmRequestManager() {
        return mRequestManager;
    }

    /**
     * 初始化Glide工具
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        mRequestManager = Glide.with(context);
    }

    /**
     * Glide工具类是否已经初始化
     *
     * @return 已初始化则返回true
     */
    public static boolean isInit() {
        if (mContext == null || mRequestManager == null) {
            LogUtil.i(TAG, TAG + "not init");
            return false;
        }
        return true;
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadPicture(Object url, ImageView imageView) {

        loadPicture(url, imageView, R.drawable.ic_yujiazhai);
    }

    public static void loadPicture(Object url, ImageView imageView, Transformation transform) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager.load(url).dontAnimate();
        builder = builder.placeholder(R.drawable.ic_yujiazhai);
        builder.transform(transform);
        builder.into(imageView);
    }


    /**
     * 加载头像
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadHead(String url, ImageView imageView) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager.load(url).dontAnimate();
        builder = builder.placeholder(R.drawable.default_header);
        builder.into(imageView);
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url        网络地址
     * @param imageView  目标控件
     * @param defaultImg 默认的图片 若不需要则输入-1
     */
    public static void loadPicture(Object url, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager.load(url).dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);

    }

    public static void preload(Object url) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager.load(url).dontAnimate();
        builder.preload();
    }



    public static void loadPictureNoMemoryCache(Object url, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }


        DrawableRequestBuilder builder = mRequestManager.load(url).dontAnimate();
//        builder.skipMemoryCache(true);
//        if (defaultImg != -1) {
//            builder = builder.placeholder(defaultImg);
//        }
        builder.into(imageView);

    }

    public static void loadPicture(Object url, ImageView imageView, int w, int h) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager.load(url).override(w, h).dontAnimate();
//        if (defaultImg != -1) {
//            builder = builder.placeholder(defaultImg);
//        }
        builder.into(imageView);

    }

    public static void loadPicture(String url, GlideDrawableImageViewTarget listener, int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager.load(url).dontAnimate();
//        if (defaultImg != -1) {
//            builder = builder.placeholder(defaultImg);
//        }
        builder.into(listener);
    }



    //加载本地图片
    public static void loadPicImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(new File(path)).into(imageView);
    }

    public static void loadPicImage(Context context, String path, int w, int h, ImageView imageView) {
        Glide.with(context).load(new File(path)).override(w, h).into(imageView);
    }


    public static void loadUrlPicture(final String url, final SimpleTarget<byte[]> simpleTarget) {
        mRequestManager.load(url).asBitmap().toBytes().into(simpleTarget);
    }


    /**
     * 清空缓存
     */
    public static void clear() {
        Glide.get(mContext).clearMemory();
        Glide.get(mContext).clearDiskCache();
    }

    /**
     * 清空内存缓存
     */
    public static void clearMemory() {
        Glide.get(mContext).clearMemory();
    }


}
