package com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Activity.EditSetsActivity;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Activity.EditWorkoutActivity;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Set;
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
        this.items = items;
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

        //for each set of the exercise , create a text view and add it to the layout of the item
        for (Set s : item.getSets()) {
            TextView tv = new TextView(context);
            tv.setText(s.getReps() + "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 4, 4, 4);
            tv.setLayoutParams(params);
            tv.setBackgroundResource(R.drawable.set_view);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(context.getResources().getColor(R.color.primary_text));

            holder.llExercisesInWorkout.addView(tv);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView img1, img2;
        LinearLayout llExercisesInWorkout;

        public WorkoutExerciseViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvExerciseTitle);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
            img2 = (ImageView) itemView.findViewById(R.id.image2);

            llExercisesInWorkout = (LinearLayout) itemView.findViewById(R.id.llExerciseInWorkout);
            itemView.findViewById(R.id.exercise_workout_root).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditSetsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("exercise", items.get(getAdapterPosition()));
            intent.putExtras(bundle);
            ((EditWorkoutActivity) context).startActivityForResult(intent, 200);
//            context.startActivity(intent);
        }
    }
}
