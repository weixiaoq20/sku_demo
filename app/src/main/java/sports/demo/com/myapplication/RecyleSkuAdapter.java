package sports.demo.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sports.demo.com.myapplication.bean.ShopDeatalisBean;
import sports.demo.com.myapplication.bean.ZongAttr;
import sports.demo.com.myapplication.widget.MultiLineRadioGroup;

/*
*  类名字 OrderAdapter
*  作者:wwy
*  时间: 2018/3/16 17:25 
   邮箱Emial:964277512@qq.com
*  注解:
*/
public class RecyleSkuAdapter extends RecyclerView.Adapter<RecyleSkuAdapter.RecyclerHolder> {
    private Context context;

    private List<ZongAttr> datas;

    public RecyleSkuAdapter(Context context, List<ZongAttr> datas) {
        this.context = context;
        this.datas = datas;
    }

    public List<ZongAttr> getDatas() {
        return datas;
    }

    public void setDatas(List<ZongAttr> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyleSkuAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_detalis_sku, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {

        final ZongAttr itemObject = this.datas.get(position);
        if (itemObject!=null){
            if (!TextUtils.isEmpty(itemObject.getName())){
                holder.tvSKUName.setText(itemObject.getName());
            }else{
                holder.tvSKUName.setText("");
            }


            //绘制单选
            holder.multiRadioGroup.deleteAllViews();
            holder.multiRadioGroup.setTag(position);
            List<ShopDeatalisBean.AttrBean> values = itemObject.getAttr();
            for (int i = 0; i < values.size(); i++) {
                ShopDeatalisBean.AttrBean oItem = values.get(i);
                String value = oItem.getAttribute_value();
                boolean stock = oItem.isStock();
                RadioButton radioButton = getRadioButton(value, stock, itemObject.getPostion() == i);
                if (stock) {
                    radioButton.setBackground(context.getResources().getDrawable(R.drawable.selector_single_choose));
                } else {
                    radioButton.setBackground(context.getResources().getDrawable(R.drawable.selector_enable_single_choose));
                    radioButton.setTextColor(context.getResources().getColor(R.color.auxiliary_instructions_99));
                }

                holder.multiRadioGroup.addRadioButton(radioButton);
            }
            holder.multiRadioGroup.setChooseCallBack(new MultiLineRadioGroup.ChooseCallBack() {
                @Override
                public void onChoose(MultiLineRadioGroup multiLineRadioGroup, RadioButton view, int pos, boolean mRefresh) {
                    if (mRefresh) {
                        datas.get(Integer.parseInt(multiLineRadioGroup.getTag() + "")).setPostion(pos);
                        //判断属性是否已经匹配完了
                        if (datas != null && datas.size() > 1) {
                            if (onOneListener != null) {
                                onOneListener.onCallback(position, itemObject, pos);
                            }
                        }
                        if (isCheckFinish()) {
                            if (onFinishBack != null) {
                                onFinishBack.onCallBack(position, itemObject, pos);
                            }
                        }


                    }

                }

                @Override
                public void onMultiChoose(CheckBox view, List<Integer> list) {
                }

                @Override
                public void onChooseBack(MultiLineRadioGroup multiLineRadioGroup, RadioButton view, boolean isTrigger) {
                    if (isTrigger) {
                        multiLineRadioGroup.singlePosition = -1;
                        datas.get(Integer.parseInt(multiLineRadioGroup.getTag() + "")).setPostion(-1);
                        view.setChecked(false);

                        if (onCancalListener != null) {
                            onCancalListener.onCallBack(position, itemObject);
                        }

                    }
                }
            });
        }

    }

    /**
     * @param str
     * @param isEnable 是否可选
     * @param isCheck  当前是否属于选中状态
     * @return
     */
    //获取一个标签
    public RadioButton getRadioButton(String str, boolean isEnable, boolean isCheck) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.item_single_choose, null);
        radioButton.setEnabled(isEnable);
        radioButton.setChecked(isCheck);
        radioButton.setText(str);
        return radioButton;
    }


    private OnFinishBack onFinishBack;

    private OnOneListener onOneListener;

    private onBeforListener onBeforListener;


    private OnCancalListener onCancalListener;




    public OnCancalListener getOnCancalListener() {
        return onCancalListener;
    }

    public void setOnCancalListener(OnCancalListener onCancalListener) {
        this.onCancalListener = onCancalListener;
    }

    public RecyleSkuAdapter.onBeforListener getOnBeforListener() {
        return onBeforListener;
    }

    public void setOnBeforListener(RecyleSkuAdapter.onBeforListener onBeforListener) {
        this.onBeforListener = onBeforListener;
    }

    public OnOneListener getOnOneListener() {
        return onOneListener;
    }

    public void setOnOneListener(OnOneListener onOneListener) {
        this.onOneListener = onOneListener;
    }

    public OnFinishBack getOnFinishBack() {
        return onFinishBack;
    }

    public void setOnFinishBack(OnFinishBack onFinishBack) {
        this.onFinishBack = onFinishBack;
    }

    public interface OnFinishBack {
        void onCallBack(int position, ZongAttr item, int rb_pos);
    }

    public interface OnOneListener {
        void onCallback(int position, ZongAttr item, int rb_pos);
    }

    public interface onBeforListener {
        void onCallBack(int before);
    }


    public interface OnCancalListener {
        void onCallBack(int position, ZongAttr item);
    }



    /**
     * 判断所有属性都已经选完
     */
    List<ZongAttr> mZongDatas = new ArrayList<>();

    public boolean isCheckFinish() {
        boolean isfinsh = false;
        mZongDatas.clear();
        for (int i = 0; i < this.datas.size(); i++) {
            if (this.datas.get(i).getPostion() > -1) {
                mZongDatas.add(this.datas.get(i));
            }
        }

        if (mZongDatas.size() == this.datas.size()) {
            isfinsh = true;
        }

        return isfinsh;
    }


    private View.OnClickListener onClickListener;

    public void setOnItmClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView tvSKUName;
        MultiLineRadioGroup multiRadioGroup;

        private RecyclerHolder(View itemView) {
            super(itemView);
            tvSKUName=itemView.findViewById(R.id.tvSKUName);
            multiRadioGroup=itemView.findViewById(R.id.multiRadioGroup);
        }
    }
}
