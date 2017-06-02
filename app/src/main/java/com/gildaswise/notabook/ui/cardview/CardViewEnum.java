package com.gildaswise.notabook.ui.cardview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gildaswise.notabook.core.DisplayableEnum;
import com.gildaswise.notabook.utils.Constant;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by Gildaswise on 02/06/2017.
 */

public class CardViewEnum extends AbstractItem<CardViewEnum, CardViewEnum.ViewHolder> {

    private DisplayableEnum displayableEnum;

    public CardViewEnum(DisplayableEnum displayableEnum) {
        this.displayableEnum = displayableEnum;
    }

    public DisplayableEnum getDisplayableEnum() {
        return displayableEnum;
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return android.R.id.text1;
    }

    @Override
    public int getLayoutRes() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.text.setText(displayableEnum.getStringRes());
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.text.setText(Constant.STRING_EMPTY);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
