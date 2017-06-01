package com.gildaswise.notabook.ui.cardview;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Score;
import com.gildaswise.notabook.databinding.CardviewScoreBinding;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by Gildaswise on 11/05/2017.
 */

public class CardViewScore extends AbstractItem<CardViewScore, CardViewScore.ViewHolder> {

    private Score score;

    public CardViewScore(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
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
        return R.layout.cardview_score;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        Context localContext = holder.binding.getRoot().getContext();
        Double defaultAverage = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(localContext).getString("defaultAverage", "7.0"));
        holder.binding.setScore(score);
        holder.binding.textScoreValue.setTextColor(ContextCompat.getColor(localContext, (score.getValue() > defaultAverage) ? R.color.md_blue_500 : R.color.md_red_500));
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.binding.setScore(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardviewScoreBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = CardviewScoreBinding.bind(itemView);
        }
    }

}
