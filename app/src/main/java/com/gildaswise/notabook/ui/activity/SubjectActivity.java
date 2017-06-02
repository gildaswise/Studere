package com.gildaswise.notabook.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.view.Gravity;
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

import java.util.List;

import io.objectbox.Box;

public class SubjectActivity extends AppCompatActivity {

    private ActivitySubjectBinding binding;
    private FastItemAdapter<CardViewScore> adapter;
    private Subject selectedSubject;
    private Box<Score> scoreBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subject);
        adapter = new FastItemAdapter<>();
        scoreBox = App.getStorage(this).boxFor(Score.class);
        selectedSubject = App.getStorage(this).boxFor(Subject.class).get(getIntent().getLongExtra("subject_id", 0));
        setSupportActionBar(binding.toolbar);
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
            selectedSubject.resetScores();
            List<Score> scoreList = selectedSubject.getScores();
            App.log("SubjectActivity - onRefresh", "Subject (" + selectedSubject.getName() + ") has " + scoreList.size() + " scores");
            boolean isEmpty = scoreList.isEmpty();
            boolean displayCardView = !isEmpty && scoreList.size() > 1;
            binding.cardviewInfo.setVisibility((displayCardView) ? View.VISIBLE : View.GONE);
            binding.emptyMessage.setVisibility((isEmpty) ? View.VISIBLE : View.GONE);
            if(!isEmpty) {

                //Prepare CardView containing Subject's status
                if(displayCardView) {
                    String status = SubjectUtils.getSubjectStatus(this, selectedSubject);
                    status += Constant.STRING_SPACE + String.format(getString(R.string.subject_status_average), selectedSubject.getAverage());
                    binding.textSubjectStatus.setText(
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ?
                                    Html.fromHtml(status, Html.FROM_HTML_MODE_COMPACT) : Html.fromHtml(status)
                    );
                }

                //Fill adapter in another thread due to using forEach
                App.startNewThread(() -> {
                    for (Score score : scoreList) {
                        CardViewScore newCardViewScore = new CardViewScore(score);
                        newCardViewScore.withIdentifier(score.getId());
                        ActivityUtils.runOnUiThread(this, () -> {
                            int position = adapter.getItemAdapter().getAdapterPosition(score.getId());
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
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.getItemAdapter().withComparator((o1, o2) -> o2.getScore().getCreatedAt().compareTo(o1.getScore().getCreatedAt()));
        adapter.withOnClickListener(getDefaultAdapterClickListener());
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
}
