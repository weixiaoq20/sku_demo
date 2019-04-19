package sports.demo.com.myapplication;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sports.demo.com.myapplication.bean.ShopDeatalisBean;
import sports.demo.com.myapplication.bean.SkuInfoData;
import sports.demo.com.myapplication.bean.ZongAttr;
import sports.demo.com.myapplication.widget.NumTextView;

public class MainActivity extends AppCompatActivity {


    View show_btn;

    View view_content;

    String new_json="";

    HashMap<String,String> mHashMap=new HashMap<>();
    private Handler mHandler=new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_btn=findViewById(R.id.show_btn);
        view_content=findViewById(R.id.view_content);
        reponse=FileUtils.getJson(MainActivity.this,"d_sku.json");
        other=FileUtils.getJson(MainActivity.this,"sku.json");
        new_json=FileUtils.getJson(MainActivity.this,"new.json");
        initJson();

        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                outChooseDialog(true);
            }
        });






    }

    private String other="";
    private String reponse = "";
    private String goods_id = "";
    private ArrayList<ZongAttr> mZongAtts = new ArrayList<>();

    private void onSetAttarData() {
        if (mListArrData != null && mListArrData.size() != 0) {
            for (int k = 0; k < mListArrData.size(); k++) {
                ZongAttr zongAttr = new ZongAttr();
                List<ShopDeatalisBean.AttrBean> itemAttrs = mListArrData.get(k);
                zongAttr.setAttr(itemAttrs);
                if (!TextUtils.isEmpty(itemAttrs.toString())) {
                    zongAttr.setName(getAttrName(itemAttrs));
                }
                mZongAtts.add(zongAttr);
            }

        }
    }


    /**
     * 组装条目第一个
     *
     * @param atts
     * @return
     */
    private String getAttrName(List<ShopDeatalisBean.AttrBean> atts) {
        if (!CheckUtil.isNull(atts)) {
            for (int i = 0; i < atts.size(); i++) {
                if (0 == i) {
                    return atts.get(i).getAttribute_name();
                }
            }
        } else {
            return "";
        }
        return "";

    }

    /**
     * 匹配组装属性
     */
    List<List<ShopDeatalisBean.AttrBean>> mListArrData = new ArrayList<>();

    private void initJson() {

        try {
            JSONObject jsbObj = new JSONObject(reponse);

            if (jsbObj.has("thumb")) {
                thumb = jsbObj.optString("thumb");
            }

            if (!jsbObj.isNull("attr")) {
                JSONArray jsbOne = jsbObj.optJSONArray("attr");
                if (!TextUtils.isEmpty(jsbOne.toString())) {
                    for (int x = 0; x < jsbOne.length(); x++) {
                        List<ShopDeatalisBean.AttrBean> mOneAttrs = new ArrayList<>();
                        JSONArray jsonArray = jsbOne.optJSONArray(x);
                        for (int yy = 0; yy < jsonArray.length() && !TextUtils.isEmpty(jsonArray.toString()); yy++) {
                            JSONObject jsbAttr = jsonArray.optJSONObject(yy);
                            ShopDeatalisBean.AttrBean oneItem = JsonUtil.fromJson(jsbAttr.toString(), ShopDeatalisBean.AttrBean.class);
                            if (!TextUtils.isEmpty(oneItem.getGoods_id())) {
                                goods_id = oneItem.getGoods_id();
                            }
                            mOneAttrs.add(oneItem);
                        }
                        mListArrData.add(mOneAttrs);
                    }

                    onSetAttarData();

                    aaaa(0,null,jsbOne);

                }
            }
        } catch (Exception ww) {
            ww.printStackTrace();
        }


        if (!TextUtils.isEmpty(other)) {
            try {
                JSONObject jsonObject = new JSONObject(other);
                if (jsonObject.has("info")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("info");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int jj = 0; jj < jsonArray.length(); jj++) {
                            SkuInfoData item = JsonUtil.fromJson(jsonArray.optJSONObject(jj).toString(), SkuInfoData.class);
                            skuAllData.add(item);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();//
        // 创建iterator对象
        while (iterator.hasNext()) {
            // 用while循环，判断是否有下一个
            Map.Entry<String, Integer> entry = iterator.next();// 声明entry，并用它来装载字符串
            System.out.print("--111---key=" + entry.getKey());// 调用getKey()和getValue()打印
            System.out.print("\n");// 调用getKey()和getValue()打印
            String key = entry.getKey();

            for (SkuInfoData info : skuAllData) {
                String properties = info.getProperties();
                if (!TextUtils.isEmpty(properties)) {
                    boolean shu = isShu(properties, key);
                    if (shu) {
                        Integer value = entry.getValue();
                        entry.setValue((value+entry.getValue()));
                    }
                }
            }
        }
    }


    private boolean isShu(String data, String data2) {//1,8778,9637
        if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(data2)) {
            String[] strings1 = data.split(",");
            String[] strings2 = data2.split(",");
            ArrayList<Integer> arrayList1 = new ArrayList<>();
            ArrayList<Integer> arrayList2 = new ArrayList<>();
            for (int i = 0; i < strings1.length; i++) {
                arrayList1.add(Integer.valueOf(strings1[i]));
            }
            for (int i = 0; i < strings2.length; i++) {
                arrayList2.add(Integer.valueOf(strings2[i]));
            }
            if (arrayList1.containsAll(arrayList2)) {
                return true;
            } else {
                return false;
            }
        }


        return false;


    }
    HashMap<String, Integer> map = new HashMap<>();

    void aaaa(int lenght, ArrayList<String> a, JSONArray aa) throws JSONException {
        ArrayList<String> arrary = new ArrayList<>();
        if (lenght < aa.length()) {
            JSONArray a1 = aa.getJSONArray(lenght);
            for (int i = 0; i < a1.length(); i++) {
                // System.out.println(a1[i]);
                JSONObject jsonObject = a1.getJSONObject(i);
                map.put(jsonObject.getString("attribute_id"), 0);
                arrary.add(jsonObject.getString("attribute_id"));

            }
            if (a != null) {
                for (int j = 0; j < a.size(); j++) {
                    for (int i = 0; i < a1.length(); i++) {
                        JSONObject jsonObject = a1.getJSONObject(i);
                        // System.out.println(a.get(j) + "," + a1[i]);
                        String data = a.get(j) + "," + jsonObject.getString("attribute_id");
                        List<Integer> integers = new ArrayList<>();
                        if (!TextUtils.isEmpty(data) && data.contains(",")) {
                            String[] split = data.split(",");
                            if (split != null && split.length > 0) {
                                for (int k = 0; k < split.length; k++) {
                                    integers.add(Integer.parseInt(split[k]));
                                }
                                Collections.sort(integers, new Comparator<Integer>() {
                                    @Override
                                    public int compare(Integer o1, Integer o2) {
                                        return o1 - o2;
                                    }
                                });
                            }
                        }
                        map.put(getAllChooseIds(integers), 0);
                        arrary.add(a.get(j) + "," + jsonObject.getString("attribute_id"));
                    }
                }
            }
            aaaa(lenght + 1, arrary, aa);
        }

    }

    HashMap<String,SkuInfoData> mInfoMap=new HashMap<>();


    /**
     * 选择规格
     */
    private RecyleSkuAdapter skuAdapter = null;
    private ImageView mSpecityImg = null;//商品图片
    private RelativeLayout ivSubRelayout;//加减符号
    private RelativeLayout mAddLayout;//

    private NumTextView mPriceTv;
    //立即购买
    private TextView mBuyLayout = null;
    //固定格式
    private LinearLayoutManager mLinearManager = null;
    private ShowHomeNoticeWindow popupWindow;
    private int type = 1;
    private View view_add_shop;
    private View view_add_gwc;
    private TextView mJia_tv, mBuy_tv;
    private EditText mCountEdit;
    private RecyclerView mReCycleView;
    private String mBeforeArrtId = "";
    private String thumb = "";
    private String mPriceStr = "12.30";
    private List<SkuInfoData> skuAllData = new ArrayList<>();

    public void outChooseDialog(boolean isShowGwc) {
        if (popupWindow == null) {
            popupWindow = new ShowHomeNoticeWindow(MainActivity.this, R.layout.dialog_show_product_specity, 0x00fffff);
            //商品图片地址
            mSpecityImg = popupWindow.getView(R.id.show_product_specity_img);
            mCountEdit = popupWindow.getView(R.id.etQuanlity);
            mBuyLayout = popupWindow.getView(R.id.show_product_specity_buy_layout);
            TextView tvName = popupWindow.getView(R.id.tv_shop_cost);
            TextView show_product_sku = popupWindow.getView(R.id.show_product_sku);
            mJia_tv = popupWindow.getView(R.id.jia_name);
            mBuy_tv = popupWindow.getView(R.id.shop_detalis_type_tv);
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mZongAtts.size(); i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(mZongAtts.get(i).getName());
            }
            show_product_sku.setText(sb);
            tvName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间添加横线条
            if (!CheckUtil.isNull(thumb)) {
                GlideUtil.loadPicture(thumb, mSpecityImg, R.drawable.bg_productcart_default);
            }
            ImageView iv = popupWindow.getView(R.id.dialog_show_product_specity_close);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
            });
            view_add_gwc = popupWindow.getView(R.id.view_add_gwc);
            view_add_shop = popupWindow.getView(R.id.view_add_shop);
            if (isShowGwc) {
                mBuyLayout.setVisibility(View.GONE);
                popupWindow.getView(R.id.view_2).setVisibility(View.VISIBLE);
            } else {
                mBuyLayout.setVisibility(View.VISIBLE);
                popupWindow.getView(R.id.view_2).setVisibility(View.GONE);

            }
            mPriceTv = popupWindow.getView(R.id.show_product_specity_price_tv);
            mPriceTv.setText(mPriceStr);

            mLinearManager = new LinearLayoutManager(MainActivity.this);
            mLinearManager.setOrientation(LinearLayoutManager.VERTICAL);
            //多规格列表
            mReCycleView = popupWindow.getView(R.id.show_product_specity_recycle);
            mReCycleView.setLayoutManager(mLinearManager);

            skuAdapter = new RecyleSkuAdapter(this, mZongAtts);
            mReCycleView.setAdapter(skuAdapter);
            mReCycleView.setNestedScrollingEnabled(false);
            skuAdapter.setOnFinishBack(new RecyleSkuAdapter.OnFinishBack() {
                @Override
                public void onCallBack(int position, final ZongAttr item, int rb_pos) {
                    if (skuAdapter != null &&
                            skuAdapter.getDatas() != null) {
                        List<ZongAttr> datas = skuAdapter.getDatas();
                        List<Integer> marrayList = new ArrayList<>();
                        for (int k = 0; k < datas.size(); k++) {
                            int mIndex = datas.get(k).getPostion();
                            if (mIndex > -1) {
                                Integer integer = Integer.parseInt(datas.get(k).
                                        getAttr().get(mIndex).getAttribute_id());
                                marrayList.add(integer);
                            }
                        }

                        Collections.sort(marrayList, new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return o1 - o2;
                            }
                        });
                        String attr_id = getAllChooseIds(marrayList);
                        for (SkuInfoData info : skuAllData) {
                            if (attr_id.equals(info.getProperties()) && info.getStock() != 0) {
                                setSkuChooseData(info);
                            }
                        }

                    }

                }
            });
            skuAdapter.setOnCancalListener(new RecyleSkuAdapter.OnCancalListener() {
                @Override
                public void onCallBack(int position, ZongAttr item) {
                    if (popupWindow != null) {
                        TextView tv_sku = popupWindow.getView(R.id.tv_sku);
                        tv_sku.setText("选择");
                        TextView show_product_sku = popupWindow.getView(R.id.show_product_sku);

                        show_product_sku.setText(getAttrNames());
                        show_product_sku.setTextColor(MainActivity.this.getResources().getColor(R.color.title_text_33_content));
                        if (mSpecityImg != null) {
                            if (!CheckUtil.isNull(thumb)) {
                                GlideUtil.loadPicture(thumb, mSpecityImg, R.drawable.bg_productcart_default);
                            }
                        }

                        NumTextView shop_price = popupWindow.getView(R.id.show_product_specity_price_tv);
                        shop_price.setText(mPriceStr);

                    }
                    if (skuAdapter != null) {
                        List<ZongAttr> datas = skuAdapter.getDatas();
                        if (datas != null) {
                            List<Integer> arrayList = new ArrayList<>();
                            for (int i = 0; i < datas.size(); i++) {
                                if (datas.get(i).getPostion() > -1) {
                                    arrayList.add(Integer.parseInt(datas.get(i).getAttr().get(datas.get(i).getPostion()).getAttribute_id()));
                                } else {
                                    arrayList.add(-1);
                                }
                            }

                            for (int i = 0; i < datas.size(); i++) {
                                for (int j = 0; j < datas.get(i).getAttr().size(); j++) {
                                    ArrayList<Integer> arrayList1 = new ArrayList<>();
                                    arrayList1.addAll(arrayList);
                                    arrayList1.remove(i);
                                    arrayList1.remove(Integer.valueOf(-1));
                                    int id = Integer.valueOf(datas.get(i).getAttr().get(j).getAttribute_id());
                                    arrayList1.add(id);
                                    Collections.sort(arrayList1, new Comparator<Integer>() {
                                        @Override
                                        public int compare(Integer o1, Integer o2) {
                                            return o1 - o2;
                                        }
                                    });
                                    String key = getAllChooseIds(arrayList1);
                                    if (map.containsKey(key)) {
                                        if ((map.get(key) > 0)) {
                                            datas.get(i).getAttr().get(j).setStock(true);
                                        }
                                    }
                                }
                            }

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (skuAdapter != null) {
                                        skuAdapter.notifyDataSetChanged();
                                    }
                                }
                            });

                        }
                    }

                }
            });

            skuAdapter.setOnOneListener(new RecyleSkuAdapter.OnOneListener() {
                @Override
                public void onCallback(final int index, final ZongAttr bb, final int ss) {
                    if (skuAdapter != null) {
                        List<ZongAttr> datas = skuAdapter.getDatas();
                        if (datas != null) {
                            List<Integer> arrayList = new ArrayList<>();
                            for (int i = 0; i < datas.size(); i++) {
                                if (datas.get(i).getPostion() > -1) {
                                    arrayList.add(Integer.parseInt(datas.get(i).getAttr().get(datas.get(i).getPostion()).getAttribute_id()));
                                } else {
                                    arrayList.add(-1);
                                }
                            }

                            for (int i = 0; i < datas.size(); i++) {
                                for (int j = 0; j < datas.get(i).getAttr().size(); j++) {
                                    ArrayList<Integer> arrayList1 = new ArrayList<>();
                                    arrayList1.addAll(arrayList);
                                    arrayList1.remove(i);
                                    arrayList1.remove(Integer.valueOf(-1));
                                    int id = Integer.valueOf(datas.get(i).getAttr().get(j).getAttribute_id());
                                    arrayList1.add(id);
                                    Collections.sort(arrayList1, new Comparator<Integer>() {
                                        @Override
                                        public int compare(Integer o1, Integer o2) {
                                            return o1 - o2;
                                        }
                                    });
                                    String key = getAllChooseIds(arrayList1);
                                    if (map.containsKey(key)) {
                                        if ((map.get(key) > 0)) {
                                            datas.get(i).getAttr().get(j).setStock(true);
                                        } else {
                                            datas.get(i).getAttr().get(j).setStock(false);
                                        }

                                    }


                                }
                            }

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (skuAdapter != null) {
                                        skuAdapter.notifyDataSetChanged();
                                    }
                                }
                            });

                        }
                    }

                }
            });
            ivSubRelayout = popupWindow.getView(R.id.ivSub);
            mAddLayout = popupWindow.getView(R.id.ivAdd);


            mCountEdit.setText(String.valueOf(nums));




            //匹配sku校验
//            skuAdapter.setOnFinishBack(new RecyleSkuAdapter.OnFinishBack() {
//                @Override
//                public void onCallBack(int pos, final ZongAttr itemObject,final int rb_pos) {
//                    LogUtil.e("-------pos----"+itemObject);

//
//                    getSkuData();


//                }
//            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                //在dismiss中恢复透明度
                public void onDismiss() {
                    WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
                    lp.alpha = 1f;
                    MainActivity.this.getWindow().setAttributes(lp);
                }
            });
            WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
            lp.alpha = 0.5f;
            MainActivity.this.getWindow().setAttributes(lp);
            popupWindow.showAtLocation(MainActivity.this.findViewById(R.id.view_content), Gravity.CENTER, 0, 0);
        } else {
            View view = popupWindow.getContentView();
            View view_2 = view.findViewById(R.id.view_2);
            if (isShowGwc) {
                mBuyLayout.setVisibility(View.GONE);
                view_2.setVisibility(View.VISIBLE);
            } else {
                mBuyLayout.setVisibility(View.VISIBLE);
                view_2.setVisibility(View.GONE);
            }
            //产生背景变暗效果
            WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
            lp.alpha = 0.5f;
            MainActivity.this.getWindow().setAttributes(lp);
            popupWindow.showAtLocation(view_content, Gravity.BOTTOM, 0, 0);
        }
    }


    private String attr_ids = "";

    private String getAllChooseIds(List<Integer> integers) {
        StringBuffer buffer = new StringBuffer("");
        for (Integer intege : integers) {
            buffer.append(intege + ",");
        }
        String result = buffer.toString();
        return result.substring(0, result.length() - 1);
    }

    private String getChooseIds(List<Integer> integers) {
        StringBuffer buffer = new StringBuffer("");
        for (Integer intege : integers) {
            buffer.append(intege + ",");
        }
        String result = buffer.toString();
        return result;
    }


    private void setAttrStatus(String attribute_id) {
        if (skuAdapter != null) {
            List<ZongAttr> datas = this.skuAdapter.getDatas();

            for (ZongAttr item : datas) {
                for (ShopDeatalisBean.AttrBean oItem : item.getAttr()) {
                    if (oItem.getAttribute_id().equals(attribute_id)) {
                        oItem.setStock(false);
                    }
                }
            }
        }
    }

    private void setResetSkuStatus() {
        if (skuAdapter != null) {
            List<ZongAttr> datas = this.skuAdapter.getDatas();
            for (ZongAttr item : datas) {
                for (ShopDeatalisBean.AttrBean oItem : item.getAttr()) {
                    oItem.setStock(true);
                }
            }
        }
    }




    private int nums = 2;

    private String getLastArrId(String mAttrId) {
        String lastStr = "";
        if (mAttrId.contains(",") && TextUtils.isEmpty(mAttrId)) {
            String[] split = mAttrId.split(",");
            if (split != null && split.length > 0) {
                lastStr = split[split.length - 1];
            }
        }
        return lastStr;
    }


    private Integer setResetData(List<String> data1, List<String> data2) {
        Integer integer = -1;
        // 注意：一定要使用创建对象的格式创建数组
        Integer[] a = new Integer[data1.size()];
        Integer[] b = new Integer[data2.size()];
        for (int k = 0; k < data1.size(); k++) {
            a[k] = Integer.parseInt(data1.get(k));
        }

        for (int k = 0; k < data2.size(); k++) {
            b[k] = Integer.parseInt(data2.get(k));
        }
        List _a = Arrays.asList(a);
        List _b = Arrays.asList(b);
        Collection realA = new ArrayList<Integer>(_a);
        Collection realB = new ArrayList<Integer>(_b);
        realA.retainAll(realB);
        Set result = new HashSet();
        result.addAll(_a);
        result.addAll(_b);
        Collection aa = new ArrayList(realA);
        Collection<Integer> bb = new ArrayList(result);
        bb.removeAll(aa);
        if (bb.size() != 0) {
            integer = ((ArrayList<Integer>) bb).get(0);
        }
        return integer;
    }

    private String getAttrNames() {
        StringBuffer stringBuffer = new StringBuffer("");
        if (skuAdapter != null && skuAdapter.getDatas() != null) {
            List<ZongAttr> datas = skuAdapter.getDatas();
            for (int kk = 0; kk < datas.size(); kk++) {
                ZongAttr attr = datas.get(kk);
                stringBuffer.append(attr.getName() + ",");
            }

        }

        String result = stringBuffer.toString();
        return result.substring(0, result.length() - 1);
    }


    private void setSkuChooseData(SkuInfoData info) {

        if (mCountEdit != null) {
            if (!CheckUtil.isNull(mCountEdit.getText().toString())) {
                nums = Integer.parseInt(mCountEdit.getText().toString());
            }
        }

        if (popupWindow != null) {
            TextView show_product_sku = popupWindow.getView(R.id.show_product_sku);
            TextView tv_sku = popupWindow.getView(R.id.tv_sku);
            NumTextView shop_price = popupWindow.getView(R.id.show_product_specity_price_tv);
            shop_price.setText(info.getShop_price());

            StringBuffer sb = new StringBuffer("");
            if (nums > 0) {
                tv_sku.setText("已选");
                show_product_sku.setTextColor(getResources().getColor(R.color.new_status_bar));
                for (int i = 0; i < mZongAtts.size(); i++) {
                    if (mZongAtts.get(i).getPostion() > -1) {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        List<ShopDeatalisBean.AttrBean> attr = mZongAtts.get(i).getAttr();
                        int postion = mZongAtts.get(i).getPostion();
                        sb.append(attr.get(postion).getAttribute_value());
                    }
                }

            }
            show_product_sku.setText(sb);
        }


        if (mSpecityImg != null) {
            if (!CheckUtil.isNull(info.getThumb())) {
                GlideUtil.loadPicture(info.getThumb(), mSpecityImg, R.drawable.bg_productcart_default);
            }
        }
    }

}
