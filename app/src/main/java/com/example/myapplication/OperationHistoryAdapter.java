package com.example.myapplication;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class OperationHistoryAdapter extends BaseQuickAdapter<OperationHistoryEntity, BaseViewHolder> {


    public OperationHistoryAdapter(@Nullable List<OperationHistoryEntity> data) {
        super(R.layout.item_timeline, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OperationHistoryEntity item) {
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.item_context, item.getContext());
        helper.setTag(R.id.item_title, item.getTime());
        helper.itemView.setTag(item);

        switch (item.getStatus()) {
            case TimeLineItemDecoration
                    .STATUS_COMPLETE:
                helper.setTextColor(R.id.item_title, Color.WHITE);
                helper.setBackgroundColor(R.id.item_title,mContext.getResources().getColor(R.color.ffa246));
                break;
            case TimeLineItemDecoration
                    .STATUS_MEDIATION:
                helper.setTextColor(R.id.item_title, mContext.getResources().getColor(R.color.bgff4020));
                helper.setBackgroundColor(R.id.item_title,mContext.getResources().getColor(R.color.ffdfbf));
                break;
            case TimeLineItemDecoration
                    .STATUS_REPORT:
                helper.setTextColor(R.id.item_title, Color.WHITE);
                helper.setBackgroundColor(R.id.item_title,mContext.getResources().getColor(R.color.ffa246));
                break;
            default:
                break;
        }
    }
}
