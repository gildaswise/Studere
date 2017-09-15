package com.gildaswise.notabook.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gildaswise.notabook.App;
import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Score;
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.databinding.ActivitySubjectBinding;
import com.gildaswise.notabook.ui.cardview.CardViewScore;
import com.gildaswise.notabook.ui.dialog.ScoreDialog;
import com.gildaswise.notabook.utils.ActivityUtils;
import com.gildaswise.notabook.utils.Constant;
import com.gildaswise.notabook.utils.SubjectUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter_extensions.ActionModeHelper;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.objectbox.Box;

public class SubjectActivity extends AppCompatActivity {

    private ActivitySubjectBinding binding;
    private FastItemAdapter<CardViewScore> adapter;
    private Subject selectedSubject;
    private Box<Score> scoreBox;
    private Box<Subject> subjectBox;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subject);
        adapter = new FastItemAdapter<>();
        scoreBox = App.getStorage(this).boxFor(Score.class);
        subjectBox = App.getStorage(this).boxFor(Subject.class);
        selectedSubject = subjectBox.get(getIntent().getLongExtra("subject_id", 0));
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
        setTitle(String.format(getString(R.string.activity_subject_title_pattern), getString(R.string.app_name), Constant.shorten(selectedSubject.getName())));
        setupUI();
        onRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void onRefresh() {
        if (selectedSubject != null) {

            selectedSubject = subjectBox.get(selectedSubject.getId());
            List<Score> scoreList = selectedSubject.getScores();
            App.log("SubjectActivity - onRefresh", "Subject (" + selectedSubject.getName() + ") has " + scoreList.size() + " scores");

            boolean isEmpty = scoreList.isEmpty();
            boolean displayCardView = !isEmpty && scoreList.size() > 1;

            binding.cardviewInfo.setVisibility((displayCardView) ? View.VISIBLE : View.GONE);
            binding.emptyMessage.setVisibility((isEmpty) ? View.VISIBLE : View.GONE);

            if(!isEmpty) {

                // Prepare CardView containing Subject's status
                if(displayCardView) {
                    String status = SubjectUtils.getSubjectStatus(this, selectedSubject);
                    status += Constant.STRING_SPACE + String.format(getString(R.string.subject_status_average), selectedSubject.getAverage());
                    binding.textSubjectStatus.setText(App.getHtmlFormattedString(status));
                }

                // Fill adapter in another thread due to using forEach
                App.startNewThread(() -> {
                    for (Score score : scoreList) {
                        CardViewScore newCardViewScore = new CardViewScore(score);
                        newCardViewScore.withIdentifier(score.getId());
                        ActivityUtils.runOnUiThread(this, () -> {
                            int position = adapter.getPosition(score.getId());
                            if (adapter.getAdapterItemCount() > 0 && position > -1 && adapter.getAdapterItem(position) != null) {
                                adapter.set(position, newCardViewScore);
                                return;
                            }
                            adapter.add(newCardViewScore);
                        });
                    }
                });

            }

        } else {
            Toast.makeText(this, R.string.error_invalid_subject, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupUI() {
        binding.fab.setOnClickListener(getDefaultFABClickListener());
        adapter.setHasStableIds(true);
        adapter.withMultiSelect(true);
        adapter.withAllowDeselection(true);
        adapter.withSelectOnLongClick(true);
        adapter.withSelectWithItemUpdate(true);
        adapter.withOnClickListener(getDefaultAdapterClickListener());
        adapter.withOnLongClickListener(getDefaultAdapterLongClickListener());
        adapter.getItemAdapter().withComparator((o1, o2) -> o2.getScore().getCreatedAt().compareTo(o1.getScore().getCreatedAt()));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public FastAdapter.OnClickListener<CardViewScore> getDefaultAdapterClickListener() {
        return (v, adpt, item, position) -> {
            PopupMenu menu = new PopupMenu(this, v);
            Score selectedScore = item.getScore();
            menu.setGravity(Gravity.END);
            menu.inflate(R.menu.default_popup_menu);
            menu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit: {
                        ScoreDialog dialog = ScoreDialog.newInstance(selectedScore, dlg -> onRefresh());
                        dialog.show(getSupportFragmentManager(), "dialog_score");
                        return true;
                    }
                    case R.id.action_remove: {
                        new AlertDialog.Builder(this)
                                .setTitle(String.format(this.getString(R.string.dialog_title_remove), selectedScore.getDescription()))
                                .setPositiveButton(android.R.string.yes, (dg, which) -> {
                                    scoreBox.remove(selectedScore);
                                    adapter.remove(position);
                                    onRefresh();
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .show();
                        return true;
                    }
                }
                return true;
            });
            menu.show();
            return true;
        };
    }

    public View.OnClickListener getDefaultFABClickListener() {
        return v -> {
            ScoreDialog dialog = ScoreDialog.newInstance(null, selectedSubject, dlg -> onRefresh());
            dialog.show(getSupportFragmentManager(), "dialog_score");
        };
    }

    public FastAdapter.OnLongClickListener<CardViewScore> getDefaultAdapterLongClickListener() {
        return (view, adptr, item, position) -> {
            adapter.toggleSelection(position);
            Set<CardViewScore> selectedItems = adapter.getSelectedItems();
            updateSelectionSnackbar(selectedItems);
            return true;
        };
    }

    private void updateSelectionSnackbar(Set<CardViewScore> selectedItems) {
        App.startNewThread(() -> {
            List<Score> scores = new ArrayList<>();
            int selectedCount = selectedItems.size();
            boolean showSnackbar = (selectedCount > 0);
            if (showSnackbar) for (CardViewScore cardView : selectedItems){scores.add(cardView.getScore());}
            String snackbarText = String.format(getString(R.string.subject_selected_item), selectedCount);
            Double average = Subject.getAverage(scores);
            if (average > Subject.DEFAULT_AVERAGE) snackbarText += Constant.NEW_LINE_HTML + String.format(getString(R.string.subject_snackbar_average), average);
            Spanned snackbarSpannedText = App.getHtmlFormattedString(snackbarText);
            runOnUiThread(() -> {
                if(snackbar == null){
                    snackbar = Snackbar.make(binding.getRoot(), snackbarSpannedText, Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.hide, v -> snackbar.dismiss());
                    snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {onDismissSnackbar();}
                    });
                    if (showSnackbar) snackbar.show();
                } else {
                    if (showSnackbar) {snackbar.setText(snackbarSpannedText); snackbar.show();}
                    else {
                        snackbar.dismiss();
                    }
                }
            });
        });
    }

    private void onDismissSnackbar() {
        for (CardViewScore cardViewScore : adapter.getAdapterItems()) {if(cardViewScore.isSelected()) adapter.deselect(adapter.getAdapterPosition(cardViewScore));}
        snackbar = null;
    }

}
