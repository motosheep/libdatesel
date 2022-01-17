package com.north.light.libdatesel.v2.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.v2.DateMainV2;
import com.north.light.libdatesel.v2.bean.LibDateDayInMonthDetailInfoV2;
import com.north.light.libdatesel.v2.bean.LibDateXV2MonthInfo;
import com.north.light.libdatesel.v2.model.LibDateCalendarManagerV2;
import com.north.light.libdatesel.v2.widget.LibCalendarMonthViewV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lzt
 * @Date: 2022/1/7 18:49
 * @Description:年月选择v2月份adapter
 */
public class LibDateXV2MonthAdapter extends RecyclerView.Adapter<LibDateXV2MonthAdapter.MonthDetailHolder> {
    private OnClickListener mListener;
    private List<LibDateXV2MonthInfo> mData = new ArrayList<>();
    private Context mContext;
    private int mMode = 1;
    //高度缓存类
    private Map<Integer, Integer> heightCacheMap = new HashMap<>();

    public LibDateXV2MonthAdapter(Context context, int mode) {
        this.mContext = context;
        this.mMode = mode;
    }

    @NonNull
    @Override
    public MonthDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_fragment_lib_date_x_v2_month_item,
                parent, false);
        return new MonthDetailHolder(view);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewRecycled(@NonNull MonthDetailHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull final MonthDetailHolder holder, final int position) {
        final LibDateXV2MonthInfo info = mData.get(position);
        if (info != null) {
            //数据设置------------------------------------------------------------------------------
            final String year = info.getYear();
            final String month = info.getMonth();
            holder.mContent.setMode(mMode);
            holder.mContent.setEvent(info.getEventList());
            holder.mContent.setOnWidgetParamsCallBack(new LibCalendarMonthViewV2.OnWidgetParamsCallBack() {
                @Override
                public void height(int height) {
                    Log.d("onBindViewHolder", "height:" + (info.getYear() + info.getMonth()) + "--height:" + height);
                    if (height >= 0) {
                        heightCacheMap.put(position, height);
                        if (mListener != null) {
                            mListener.heightChange();
                        }
                    }
                }
            });
            holder.mContent.setOnDateClickListener(new LibCalendarMonthViewV2.OnDateClickListener() {
                @Override
                public void date(LibDateDayInMonthDetailInfoV2 info) {
                    Log.d("onBindViewHolder", "选择了数据:" + (info.getYear() + info.getMonth() + info.getDayOfNum()));
                    if (mListener != null) {
                        mListener.date(info);
                    }
                }

                @Override
                public void year(String year, String month) {
                    if (mListener != null) {
                        mListener.year(year, month);
                    }
                }
            });
            try {
                List<LibDateDayInMonthDetailInfoV2> infoList = LibDateCalendarManagerV2.getInstance().getDayByMonth(year, month);
                holder.mContent.setData(infoList, year, month);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(List<LibDateXV2MonthInfo> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 滑动到当前月份
     */
    public int gotoCurrentDate(String year, String month) {
        if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month)) {
            return 0;
        }
        for (int i = 0; i < mData.size(); i++) {
            if (!TextUtils.isEmpty(mData.get(i).getYear()) &&
                    !TextUtils.isEmpty(mData.get(i).getMonth()) &&
                    mData.get(i).getYear().equals(year) &&
                    mData.get(i).getMonth().equals(month)) {
                //滚动到当前位置
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取某个位置下的信息
     */
    public LibDateXV2MonthInfo getInfoByPos(int currentPosition) {
        try {
            return mData.get(currentPosition);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新日历事件
     */
    public void updateEvent(String year, String month, List<String> eventList) {
        if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month)) {
            return;
        }
        if (eventList == null || eventList.size() == 0) {
            return;
        }
        for (int i = 0; i < mData.size(); i++) {
            if (!TextUtils.isEmpty(mData.get(i).getYear()) &&
                    !TextUtils.isEmpty(mData.get(i).getMonth()) &&
                    mData.get(i).getYear().equals(year) &&
                    mData.get(i).getMonth().equals(month)) {
                //滚动到当前位置
                mData.get(i).setEventList(eventList);
                notifyItemChanged(i);
            }
        }
    }

    public static class MonthDetailHolder extends RecyclerView.ViewHolder {
        private LibCalendarMonthViewV2 mContent;

        public MonthDetailHolder(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.recy_fragment_lib_date_x_v2_month_item_content);
        }
    }

    /**
     * 获取某个位置下的高度
     */
    public int getHeightWithPos(int pos) {
        Integer height = heightCacheMap.get(pos);
        if (height == null) {
            return 0;
        }
        return height;
    }

    //监听事件
    public interface OnClickListener {
        void date(LibDateDayInMonthDetailInfoV2 info);

        void year(String year, String month);

        void heightChange();
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }
}
