package com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

/**
 * Created by nickstamp on 10/12/2015.
 */
public class ExerciseSelectableAdapter extends RecyclerView.Adapter<ExerciseSelectableAdapter.ExerciseViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<Exercise> items;
    private boolean[] selected;
    private Context context;
    private LayoutInflater inflater;

    public ExerciseSelectableAdapter(Context context, ArrayList<Exercise> items, ArrayList<Integer> ids) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(this.context);

        selected = new boolean[items.size()];
        for (int i = 0; i < items.size(); i++) {
            if (ids.size() > 0 && items.get(i) != null)
                if (ids.contains(items.get(i).getId()))
                    selected[i] = true;
                else
                    selected[i] = false;
        }

    }

    /**
     * Get the selected exercises
     *
     * @return the arraylist with the exercises that are checked
     */
    public ArrayList<Exercise> getSelectedExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                exercises.add(items.get(i));
            }
        }
        return exercises;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.list_item_selectable_exercise, parent, false);

            ExerciseViewHolder holder = new ExerciseViewHolder(view, viewType);
            return holder;
        } else if (viewType == TYPE_HEADER) {

            View view = inflater.inflate(R.layout.list_header_exercise, parent, false);

            ExerciseViewHolder holder = new ExerciseViewHolder(view, viewType);
            return holder;


        }
        return null;

    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        if (holder.type == TYPE_HEADER) {
            holder.title.setText(items.get(position + 1).getMuscle().toString());

        } else {
            Exercise item = items.get(position);

            holder.title.setText(item.getTitle());
            holder.img1.setImageResource(item.getImg1());
            holder.img2.setImageResource(item.getImg2());
            holder.checkBox.setChecked(selected[position]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        int type;
        TextView title;
        ImageView img1, img2;
        CheckBox checkBox;

        public ExerciseViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_HEADER) {
                title = (TextView) itemView.findViewById(R.id.tvTitle);
                type = TYPE_HEADER;
            } else {

                type = TYPE_ITEM;
                title = (TextView) itemView.findViewById(R.id.tvExerciseTitle);
                img1 = (ImageView) itemView.findViewById(R.id.image1);
                img2 = (ImageView) itemView.findViewById(R.id.image2);

                checkBox = (CheckBox) itemView.findViewById(R.id.chbExercise);
                checkBox.setOnCheckedChangeListener(this);
            }

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            selected[getAdapterPosition()] = isChecked;
        }
    }
}