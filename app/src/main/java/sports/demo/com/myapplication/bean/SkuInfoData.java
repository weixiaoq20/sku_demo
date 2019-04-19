package sports.demo.com.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ProjectName: Xmili-android$
 * @Package: com.sclonsee.miliyoutuan.bean$
 * @ClassName: SkuInfoData$
 * @注释:
 * @Author: 夏瑞
 * @CreateDate: 2019/4/12$ 17:06$
 * @UpdateUser: 夏瑞
 * @UpdateDate: 2019/4/12$ 17:06$
 * @UpdateRemark:
 * @Version: 1.0
 */
public class SkuInfoData implements Parcelable {


    /**
     * id : 441725
     * new_price : 89.00
     * properties : 2,2787,8777,9881
     * properties_name : 白色,M,上衣L,送 化妆包1个
     * shop_price : 99.00
     * stock : 1000
     * thumb : http://milicdn.sclonsee.com/goods/thumb/2019/03/5c9b933d846c1.png
     */

    private String id;
    private String new_price;
    private String properties;
    private String properties_name;
    private String shop_price;
    private int stock;
    private String thumb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getProperties_name() {
        return properties_name;
    }

    public void setProperties_name(String properties_name) {
        this.properties_name = properties_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.new_price);
        dest.writeString(this.properties);
        dest.writeString(this.properties_name);
        dest.writeString(this.shop_price);
        dest.writeInt(this.stock);
        dest.writeString(this.thumb);
    }

    public SkuInfoData() {
    }

    protected SkuInfoData(Parcel in) {
        this.id = in.readString();
        this.new_price = in.readString();
        this.properties = in.readString();
        this.properties_name = in.readString();
        this.shop_price = in.readString();
        this.stock = in.readInt();
        this.thumb = in.readString();
    }

    public static final Parcelable.Creator<SkuInfoData> CREATOR = new Parcelable.Creator<SkuInfoData>() {
        @Override
        public SkuInfoData createFromParcel(Parcel source) {
            return new SkuInfoData(source);
        }

        @Override
        public SkuInfoData[] newArray(int size) {
            return new SkuInfoData[size];
        }
    };
}
