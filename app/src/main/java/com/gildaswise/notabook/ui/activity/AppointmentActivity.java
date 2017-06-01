package com.gildaswise.notabook.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.gildaswise.notabook.App;
import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Appointment;
import com.gildaswise.notabook.core.exception.ErrorMessageException;
import com.gildaswise.notabook.databinding.ActivityAppointmentBinding;
import com.gildaswise.notabook.utils.DateUtils;

import org.threeten.bp.LocalDateTime;

import io.objectbox.Box;

public class AppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityAppointmentBinding binding;
    private Box<Appointment> appointmentBox;
    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointment);
        appointmentBox = App.getStorage(this).boxFor(Appointment.class);
        Long appointmentId = (getIntent() != null) ? getIntent().getLongExtra("appointment_id", 0) : 0;
        appointment = (appointmentId > 0) ? appointmentBox.get(appointmentId) : new Appointment(DateUtils.now());
        setupUI();
    }

    private void setupUI() {
        binding.setAppointment(appointment);
        binding.buttonClose.setOnClickListener(getOnCloseListener());
        binding.buttonSave.setOnClickListener(getOnSaveListener());
        binding.appointmentDate.setOnClickListener(getOnEditDateListener());
        binding.appointmentTime.setOnClickListener(getOnEditTimeListener());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.appointment_dialog_close_title)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> super.onBackPressed())
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public View.OnClickListener getOnCloseListener() {
        return v -> onBackPressed();
    }

    public View.OnClickListener getOnSaveListener() {
        return v -> {
            String appointmentTitle = binding.appointmentTitle.getText().toString();
            String appointmentDescription = binding.appointmentDescription.getText().toString();
            App.handleErrorMessageException(binding.getRoot(), () -> {
                appointment.setTitle(appointmentTitle);
                appointment.setDescription(appointmentDescription);
                appointmentBox.put(appointment);
                finish();
            });
        };
    }

    public View.OnClickListener getOnEditDateListener() {
        return v -> {
            LocalDateTime today = DateUtils.toLocalDateTime(appointment.getDate());
            DatePickerDialog picker = new DatePickerDialog(this, this, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
            picker.show();
        };
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        LocalDateTime date = DateUtils
                .toLocalDateTime(appointment.getDate())
                .withDayOfMonth(dayOfMonth)
                .withMonth(month)
                .withYear(year);
        App.handleErrorMessageException(binding.getRoot(), () -> {
            appointment.setDate(DateUtils.toDate(date));
            binding.setAppointment(appointment);
        });
    }

    public View.OnClickListener getOnEditTimeListener() {
        return v -> {
            LocalDateTime today = DateUtils.toLocalDateTime(appointment.getDate());
            TimePickerDialog picker = new TimePickerDialog(this, this, today.getHour(), today.getMinute(), DateFormat.is24HourFormat(this));
            picker.show();
        };
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalDateTime date = DateUtils
                .toLocalDateTime(appointment.getDate())
                .withHour(hourOfDay)
                .withMinute(minute);
        App.handleErrorMessageException(binding.getRoot(), () -> {
            appointment.setDate(DateUtils.toDate(date));
            binding.setAppointment(appointment);
        });
    }
}
