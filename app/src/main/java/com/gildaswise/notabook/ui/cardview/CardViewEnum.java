package com.gildaswise.notabook.ui.cardview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.DisplayableEnum;
import com.gildaswise.notabook.databinding.CardviewEnumBinding;
import com.gildaswise.notabook.utils.Constant;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by Gildaswise on 02/06/2017.
 */

public class CardViewEnum extends AbstractItem<CardViewEnum, CardViewEnum.ViewHolder> {

    private DisplayableEnum displayableEnum;
    private boolean selected;

    public CardViewEnum(DisplayableEnum displayableEnum, boolean selected) {
        this.displayableEnum = displayableEnum;
        this.selected = selected;
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
        return R.id.content;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.cardview_enum;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        Context localContext = holder.itemView.getContext();
        holder.binding.setDisplayableEnum(displayableEnum);
        if(selected) {
            holder.binding.text.setTextColor(ContextCompat.getColor(localContext, R.color.colorPrimary));
            holder.binding.imageCheck.setVisibility(View.VISIBLE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardviewEnumBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = CardviewEnumBinding.bind(itemView);
        }
    }

}
