package sports.demo.com.myapplication.bean;
/*
*  类名字 ZongAttr
*  作者:夏瑞
*  时间: 2018-04-27 10:32 
   邮箱Emial:1970258244@qq.com
*  注解:数据重新组装
*/

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class ZongAttr implements Parcelable {

    private String name;

    private List<ShopDeatalisBean.AttrBean> attr;
    private int postion=-1;

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public ZongAttr(){}
    public ZongAttr(String name, List<ShopDeatalisBean.AttrBean> attr) {
        this.name = name;
        this.attr = attr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopDeatalisBean.AttrBean> getAttr() {
        return attr;
    }

    public void setAttr(List<ShopDeatalisBean.AttrBean> attr) {
        this.attr = attr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.attr);
        dest.writeInt(this.postion);
    }

    @Override
    public String toString() {
        return "ZongAttr{" +
                "name='" + name + '\'' +
                ", attr=" + attr +
                ", postion=" + postion +
                '}';
    }

    protected ZongAttr(Parcel in) {
        this.name = in.readString();
        this.attr = in.createTypedArrayList(ShopDeatalisBean.AttrBean.CREATOR);
        this.postion = in.readInt();
    }

    public static final Parcelable.Creator<ZongAttr> CREATOR = new Parcelable.Creator<ZongAttr>() {
        @Override
        public ZongAttr createFromParcel(Parcel source) {
            return new ZongAttr(source);
        }

        @Override
        public ZongAttr[] newArray(int size) {
            return new ZongAttr[size];
        }
    };
}
