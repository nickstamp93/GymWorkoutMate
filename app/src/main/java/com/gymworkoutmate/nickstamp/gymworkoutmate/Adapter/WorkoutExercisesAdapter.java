package com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

/**
 * Created by nickstamp on 10/12/2015.
 */
public class WorkoutExercisesAdapter extends RecyclerView.Adapter<WorkoutExercisesAdapter.WorkoutExerciseViewHolder> {
    private ArrayList<Exercise> items;
    private Context context;
    private LayoutInflater inflater;

    public WorkoutExercisesAdapter(Context context, ArrayList<Exercise> items) {
        this.context = context;
        this.items = new ArrayList<>();
        this.items.addAll(items);
        inflater = LayoutInflater.from(this.context);

    }

    @Override
    public WorkoutExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_workout_exercise, parent, false);

        WorkoutExerciseViewHolder holder = new WorkoutExerciseViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(WorkoutExerciseViewHolder holder, int position) {

        Exercise item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.img1.setImageResource(item.getImg1());
        holder.img2.setImageResource(item.getImg2());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img1, img2;
        LinearLayout llExercisesInWorkout;

        public WorkoutExerciseViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvExerciseTitle);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
            img2 = (ImageView) itemView.findViewById(R.id.image2);

            llExercisesInWorkout = (LinearLayout) itemView.findViewById(R.id.llExerciseInWorkout);
        }
    }
}
