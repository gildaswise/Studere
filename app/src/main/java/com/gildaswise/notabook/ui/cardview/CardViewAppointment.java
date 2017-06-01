package com.gildaswise.notabook.ui.cardview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Appointment;
import com.gildaswise.notabook.databinding.CardviewAppointmentBinding;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by Gildaswise on 31/05/2017.
 */

public class CardViewAppointment extends AbstractItem<CardViewAppointment, CardViewAppointment.ViewHolder> {

    private Appointment appointment;

    public CardViewAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
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
        return R.layout.cardview_appointment;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.binding.setAppointment(appointment);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.binding.setAppointment(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardviewAppointmentBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = CardviewAppointmentBinding.bind(itemView);
        }
    }

}
