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
import com.gildaswise.notabook.core.Subject;
import com.gildaswise.notabook.databinding.FragmentDefaultBinding;
import com.gildaswise.notabook.ui.activity.SubjectActivity;
import com.gildaswise.notabook.ui.cardview.CardViewSubject;
import com.gildaswise.notabook.ui.dialog.SubjectDialog;
import com.gildaswise.notabook.utils.ActivityUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.List;

import io.objectbox.Box;

public class SubjectFragment extends Fragment implements MainActivityFragment {

    private FragmentDefaultBinding binding;
    private FastItemAdapter<CardViewSubject> adapter;
    private Box<Subject> subjectBox;

    public SubjectFragment() {}

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
        adapter.getItemAdapter().withComparator((o1, o2) -> Integer.compare(o2.getSubject().getCreatedAt().compareTo(o1.getSubject().getCreatedAt()), Boolean.compare(o2.getSubject().getFinished(), o1.getSubject().getFinished())));
        adapter.withOnClickListener(getDefaultOnClickListener());
        adapter.withOnLongClickListener(getDefaultOnLongClickListener());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectBox = App.getStorage(getActivity()).boxFor(Subject.class);
        onRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    public FastAdapter.OnClickListener<CardViewSubject> getDefaultOnClickListener() {
        return (v, adptr, item, position) -> {
            Intent intent = new Intent(getActivity(), SubjectActivity.class);
            intent.putExtra("subject_id", item.getSubject().getId());
            startActivity(intent);
            return true;
        };
    }

    private FastAdapter.OnLongClickListener<CardViewSubject> getDefaultOnLongClickListener() {
        return (v, adptr, item, position) -> {
            PopupMenu menu = new PopupMenu(getContext(), v);
            Subject selectedSubject = item.getSubject();
            menu.inflate(R.menu.default_popup_menu);
            menu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit: {
                        edit(selectedSubject);
                        return true;
                    }
                    case R.id.action_remove: {
                        remove(selectedSubject, position);
                        return true;
                    }
                }
                return true;
            });
            menu.show();
            return true;
        };
    }

    private void edit(Subject selectedSubject) {
        SubjectDialog dialog = SubjectDialog.newInstance(selectedSubject, dlg -> onRefresh());
        dialog.show(getActivity().getSupportFragmentManager(), "dialog_subject");
    }

    private void remove(Subject selectedSubject, int adapterPosition) {
        new AlertDialog.Builder(getActivity())
                .setTitle(String.format(getActivity().getString(R.string.dialog_title_remove), selectedSubject.getName()))
                .setPositiveButton(android.R.string.yes, (dg, which) -> {
                    subjectBox.remove(selectedSubject);
                    adapter.remove(adapterPosition);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onRefresh() {
        App.startNewThread(() -> {
            List<Subject> subjects = subjectBox.getAll();
            App.startNewThread(() -> {
                for (Subject item : subjects) {
                    CardViewSubject newCardView = new CardViewSubject(item);
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
        SubjectDialog dialog = SubjectDialog.newInstance(null, dlg -> onRefresh());
        dialog.show(getActivity().getSupportFragmentManager(), "dialog_subject");
    }
}
