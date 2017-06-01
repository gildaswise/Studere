package com.gildaswise.notabook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildaswise.notabook.App;
import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Appointment;
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.databinding.FragmentDefaultBinding;
import com.gildaswise.notabook.ui.activity.AppointmentActivity;
import com.gildaswise.notabook.ui.cardview.CardViewAppointment;
import com.gildaswise.notabook.utils.ActivityUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.List;

import io.objectbox.Box;

/**
 * Created by Gildaswise on 18/05/2017.
 */

public class AgendaFragment extends Fragment implements MainActivityFragment {

    private FragmentDefaultBinding binding;
    private FastItemAdapter<CardViewAppointment> adapter;
    private Box<Appointment> appointmentBox;

    public AgendaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDefaultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FastItemAdapter<>();
        adapter.getItemAdapter().withComparator((o1, o2) -> o2.getAppointment().getDate().compareTo(o1.getAppointment().getDate()));
        adapter.withOnClickListener(getDefaultOnClickListener());
        adapter.withOnLongClickListener(getDefaultOnLongClickListener());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        appointmentBox = App.getStorage(getActivity()).boxFor(Appointment.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        App.startNewThread(() -> {
            List<Appointment> agenda = appointmentBox.getAll();
            App.startNewThread(() -> {
                for (Appointment item : agenda) {
                    CardViewAppointment newCardView = new CardViewAppointment(item);
                    newCardView.withIdentifier(item.getId());
                    ActivityUtils.runOnUiThread(getActivity(), () -> {
                        int position = adapter.getItemAdapter().getAdapterPosition(item.getId());
                        if (adapter.getAdapterItemCount() > 0 && position > -1) {
                            if (adapter.getAdapterItem(position) != null) {
                                adapter.set(position, newCardView);
                                return;
                            }
                        }
                        adapter.add(newCardView);
                    });
                }
            });
        });
    }

    @Override
    public void onFloatingActionButtonClick() {
        startActivity(new Intent(getActivity(), AppointmentActivity.class));
    }

    public FastAdapter.OnClickListener<CardViewAppointment> getDefaultOnClickListener() {
        return (v, adptr, item, position) -> {
            Intent intent = new Intent(getActivity(), AppointmentActivity.class);
            intent.putExtra("appointment_id", item.getAppointment().getId());
            startActivity(intent);
            return true;
        };
    }

    public FastAdapter.OnLongClickListener<CardViewAppointment> getDefaultOnLongClickListener() {
        return (v, adptr, item, position) -> {
            PopupMenu menu = new PopupMenu(getContext(), v);
            Appointment selectedAppointment = item.getAppointment();
            menu.inflate(R.menu.default_popup_menu);
            menu.getMenu().removeItem(R.id.action_edit);
            menu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_remove: {
                        remove(selectedAppointment, position);
                        return true;
                    }
                }
                return true;
            });
            menu.show();
            return true;
        };
    }

    private void remove(Appointment selectedAppointment, int adapterPosition) {
        new AlertDialog.Builder(getActivity())
                .setTitle(String.format(getActivity().getString(R.string.dialog_title_remove), selectedAppointment.getTitle()))
                .setPositiveButton(android.R.string.yes, (dg, which) -> {
                    appointmentBox.remove(selectedAppointment);
                    adapter.remove(adapterPosition);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}
