package com.gildaswise.notabook.ui.cardview;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.databinding.CardviewSubjectSimpleBinding;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by Gildaswise on 02/06/2017.
 */

public class CardViewSubjectSimple extends AbstractItem<CardViewSubjectSimple, CardViewSubjectSimple.ViewHolder> {

    private Subject subject;

    public CardViewSubjectSimple(Subject subject) {
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
        return R.layout.cardview_subject_simple;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.binding.setSubject(subject);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.binding.setSubject(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardviewSubjectSimpleBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = CardviewSubjectSimpleBinding.bind(itemView);
        }

    }
}
