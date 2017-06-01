package com.gildaswise.notabook.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.gildaswise.notabook.App;
import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.core.exception.ErrorMessageException;
import com.gildaswise.notabook.databinding.DialogSubjectBinding;

import io.objectbox.Box;

/**
 * Created by Gildaswise on 10/05/2017.
 */

public class SubjectDialog extends DialogFragment {

    private DialogSubjectBinding binding;
    private Subject subject;
    private DialogInterface.OnDismissListener onDismissListener;

    public static SubjectDialog newInstance(Subject subject, DialogInterface.OnDismissListener onDismissListener) {
        Bundle args = new Bundle();
        SubjectDialog fragment = new SubjectDialog();
        fragment.setArguments(args);
        fragment.setSubject(subject);
        fragment.setOnDismissListener(onDismissListener);
        return fragment;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogSubjectBinding.inflate(LayoutInflater.from(getContext()));
        binding.setSubject(getSubject());
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString((getSubject() != null) ? R.string.subject_dialog_title_edit : R.string.subject_dialog_title_new))
                .setView(binding.getRoot())
                .setCancelable(false)
                .setPositiveButton(R.string.button_save, null)
                .setNegativeButton(R.string.button_cancel, null)
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(getDefaultButtonListener(dialog));
        return dialog;
    }

    public View.OnClickListener getDefaultButtonListener(AlertDialog dialog) {
        return view -> {
            String subjectName = binding.editSubjectName.getText().toString();
            String subjectDescription = binding.editSubjectDescription.getText().toString();
            Box<Subject> subjectBox = App.getStorage(getActivity()).boxFor(Subject.class);
            Subject subject = getSubject();
            try {
                if (subject == null) {
                    subject = new Subject(subjectName, subjectDescription);
                } else {
                    subject.setName(subjectName);
                    subject.setDescription(subjectDescription);
                }
                subjectBox.put(subject);
                getOnDismissListener().onDismiss(dialog);
                dialog.dismiss();
            } catch (ErrorMessageException exception) {
                Snackbar.make(binding.content, getActivity().getString(exception.getMessageStringRes()), Snackbar.LENGTH_SHORT).show();
            }
        };
    }
}
