package com.north.light.libdatesel.v1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.v1.bean.LibDateMonthInYearDetailInfo;
import com.north.light.libdatesel.v1.model.LibDateCalendarManager;
import com.north.light.libdatesel.utils.LibDateCalendarTrainUtils;
import com.north.light.libdatesel.v1.widget.LibDateDivCalendarDetailInfo;
import com.north.light.libdatesel.v1.widget.LibDateDivCalendarDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzt
 * time 2021/6/17 10:03
 *
 * @author lizhengting
 * 描述：年的月份详情
 */
public class LibDateMonthInYearAdapter extends RecyclerView.Adapter<LibDateMonthInYearAdapter.MonthDetailHolder> {
    private OnClickListener mListener;
    /**
     * 数据集合
     */
    private List<LibDateMonthInYearDetailInfo> mData = new ArrayList<>();

    /**
     * 设置数据
     */
    public void setData(List<LibDateMonthInYearDetailInfo> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonthDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_fragment_lib_month_in_year_item, parent, false);
        return new MonthDetailHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull MonthDetailHolder holder) {
        if (holder != null && holder instanceof MonthDetailHolder) {
            holder.mContent.clearSelectStatus();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthDetailHolder holder, int position) {
        try {
            LibDateMonthInYearDetailInfo content = mData.get(position);
            holder.mTitle.setText(content.getMonth() + "月");
            holder.mContent.setMode(2);
            holder.mContent.setData(LibDateCalendarTrainUtils.getMonthList(content.getDayInfoList()), LibDateCalendarManager.getInstance().getCurYMD(0));
            holder.mContent.setOnDateClickListener(new LibDateDivCalendarDetailView.OnClickListener() {
                @Override
                public void dayDetail(LibDateDivCalendarDetailInfo info) {

                }

                @Override
                public void monthDetail(String year, String month) {
                    if(mListener!=null){
                        mListener.changeToMonth(year,month);
                    }
                }

                @Override
                public void widgetHeight(int height) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MonthDetailHolder extends RecyclerView.ViewHolder {
        private LibDateDivCalendarDetailView mContent;
        private TextView mTitle;

        public MonthDetailHolder(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.recy_fragment_lib_month_in_year_item_content);
            mTitle = itemView.findViewById(R.id.recy_fragment_lib_month_in_year_item_title);
        }
    }

    //监听事件
    public interface OnClickListener {
        void changeToMonth(String year, String month);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }
}
