package com.esoft.devtodolist.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.esoft.devtodolist.R;
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity;
import com.esoft.devtodolist.activity.noteListActivity.NoteListActivity;
import com.esoft.devtodolist.app.App;
import com.esoft.devtodolist.model.NoteModel;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private SortedList<NoteModel> sortedList;

    public SortedList<NoteModel> getSortedList() {
        return sortedList;
    }

    public NotesAdapter() {
        sortedList = new SortedList<>(NoteModel.class, new SortedList.Callback<NoteModel>() {
            @Override
            public int compare(NoteModel o1, NoteModel o2) {
                if (!o2.getDone() && o1.getDone()) {
                    return 1;
                }
                if (o2.getDone() && !o1.getDone()) {
                    return -1;
                }
                return (int) (o2.getTimestamp() - o1.getTimestamp());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(NoteModel oldItem, NoteModel newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(NoteModel item1, NoteModel item2) {
                return item1.getId() == item2.getId();
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<NoteModel> notes) {
        sortedList.replaceAll(notes);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteText;
        ImageView btnNoteSet;
        NoteModel note;
        CheckBox checkBox;
        PopupMenu popupMenu;

        boolean silentUpdate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note_text);
            checkBox = itemView.findViewById(R.id.checkNote);
            btnNoteSet = itemView.findViewById(R.id.btn_settings_note);

            btnNoteSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    popupMenu = new PopupMenu(itemView.getContext(), btnNoteSet);
                    popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        popupMenu.setForceShowIcon(true);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.deleteNote:
                                    App.getInstance().getRepositoryDao().delete(note);
                                    return true;

                                case R.id.editNote:
                                    new NewNoteActivity().start((Activity) itemView.getContext(), note);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

                    popupMenu.show();
                }
            });


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (silentUpdate) {
                        note.setDone(isChecked);
                        App.getInstance().getRepositoryDao().update(note);
                    }
                    updateStrokeOut();
                }
            });
        }

        public void bind(NoteModel note) {
            this.note = note;
            noteText.setText(note.getTextHead());
            updateStrokeOut();

            silentUpdate = false;
            checkBox.setChecked(note.getDone());
            silentUpdate = true;

        }

        public void updateStrokeOut() {
            if (note.getDone()) {
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }


}