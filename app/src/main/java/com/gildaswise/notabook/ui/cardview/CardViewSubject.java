package com.gildaswise.notabook.ui.cardview;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.databinding.CardviewSubjectBinding;
import com.gildaswise.notabook.utils.SubjectUtils;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by Gildaswise on 04/05/2017.
 */

public class CardViewSubject extends AbstractItem<CardViewSubject, CardViewSubject.ViewHolder> {

    private Subject subject;

    public CardViewSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
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
        return R.layout.cardview_subject;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        Context localContext = holder.binding.getRoot().getContext();
        Double defaultAverage = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(localContext).getString("defaultAverage", "7.0"));
        holder.binding.setSubject(subject);
        holder.binding.statusSubject.setText(SubjectUtils.getSubjectStatus(localContext, subject));
        holder.binding.averageSubject.setTextColor(ContextCompat.getColor(localContext, (subject.getAverage() > defaultAverage) ? R.color.md_blue_500 : R.color.md_red_500));
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.binding.setSubject(null);
        holder.binding.statusSubject.setText(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardviewSubjectBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = CardviewSubjectBinding.bind(itemView);
        }

    }
}
