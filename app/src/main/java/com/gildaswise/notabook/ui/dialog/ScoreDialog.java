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
import com.gildaswise.notabook.core.Score;
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.databinding.DialogScoreBinding;

import io.objectbox.Box;

/**
 * Created by Gildaswise on 11/05/2017.
 */

public class ScoreDialog extends DialogFragment {

    private DialogScoreBinding binding;
    private Score score;
    private Subject subject;
    private DialogInterface.OnDismissListener onDismissListener;

    public static ScoreDialog newInstance(Score score, Subject subject, DialogInterface.OnDismissListener onDismissListener) {
        ScoreDialog fragment = new ScoreDialog();
        fragment.setOnDismissListener(onDismissListener);
        fragment.setScore(score);
        fragment.setSubject(subject);
        return fragment;
    }

    public static ScoreDialog newInstance(Score score, DialogInterface.OnDismissListener onDismissListener) {
        ScoreDialog fragment = new ScoreDialog();
        fragment.setOnDismissListener(onDismissListener);
        fragment.setScore(score);
        fragment.setSubject(score.getSubject());
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogScoreBinding.inflate(LayoutInflater.from(getContext()));
        binding.setScore(getScore());
        binding.switchWeighted.setOnCheckedChangeListener((buttonView, isChecked) -> binding.hcScoreWeight.setVisibility((isChecked) ? View.VISIBLE : View.GONE));
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString((getScore() != null) ? R.string.score_dialog_title_edit : R.string.score_dialog_title_new))
                .setView(binding.getRoot())
                .setCancelable(false)
                .setPositiveButton(R.string.button_save, null)
                .setNegativeButton(R.string.button_cancel, null)
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(getDefaultButtonListener(dialog));
        return dialog;
    }

    private View.OnClickListener getDefaultButtonListener(AlertDialog dialog) {
        return v -> {
            String scoreDescription = binding.scoreDescription.getText().toString();
            Double value = binding.hcScoreValue.getCurrentValue();
            Double weight = (binding.switchWeighted.isChecked()) ? binding.hcScoreWeight.getCurrentValue() : 1.0;
            Box<Score> scoreBox = App.getStorage(getActivity()).boxFor(Score.class);
            if (scoreDescription.isEmpty()) {
                Snackbar.make(binding.content, getActivity().getString(R.string.error_invalid_fields), Snackbar.LENGTH_SHORT).show();
            } else {
                Score score = getScore();
                if (score == null) {
                    score = new Score(subject, value, scoreDescription);
                    if (weight > 1.0) score.setWeight(weight);
                } else {
                    score.setDescription(scoreDescription);
                    score.setValue(value);
                    score.setWeight(weight);
                }
                scoreBox.put(score);
                getOnDismissListener().onDismiss(dialog);
                dialog.dismiss();
            }
        };
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
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
}
