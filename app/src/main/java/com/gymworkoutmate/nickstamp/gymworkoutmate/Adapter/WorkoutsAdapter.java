package com.gymworkoutmate.nickstamp.gymworkoutmate.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Exercise;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Model.Workout;
import com.gymworkoutmate.nickstamp.gymworkoutmate.R;

import java.util.ArrayList;

/**
 * Created by nickstamp on 10/12/2015.
 */
public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutsHolder> {

    private ArrayList<Workout> items;
    private Context context;
    private LayoutInflater inflater;
    private final int resId = R.layout.list_item_workout;

    public WorkoutsAdapter(Context context, ArrayList<Workout> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public WorkoutsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = inflater.inflate(resId, parent, false);

        return new WorkoutsHolder(layout);
    }

    @Override
    public void onBindViewHolder(WorkoutsHolder holder, int position) {

        Workout item = items.get(position);

        holder.tvWorkoutName.setText(item.getTitle());
        holder.tvWorkoutSubtitle.setText(item.getType().toString() + " , " + item.getMuscle().toString());
        for (Exercise ex : item.getExercises()) {
            TextView tv = new TextView(context);
            tv.setText(ex.getTitle());
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            Drawable img = context.getResources().getDrawable(R.drawable.bullet);
            img.setBounds(0, 0, 10, 10);
            tv.setCompoundDrawables(img, null, null, null);
            tv.setCompoundDrawablePadding(8);
            tv.setPadding(4, 4, 4, 4);
            tv.setTextColor(context.getResources().getColor(R.color.primary_text));
            holder.llWorkoutExercises.addView(tv);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class WorkoutsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvWorkoutName, tvWorkoutSubtitle;
        LinearLayout llWorkoutExercises;

        public WorkoutsHolder(View itemView) {
            super(itemView);

            tvWorkoutName = (TextView) itemView.findViewById(R.id.tvWorkoutName);
            tvWorkoutSubtitle = (TextView) itemView.findViewById(R.id.tvWorkoutSubtitle);
            llWorkoutExercises = (LinearLayout) itemView.findViewById(R.id.llWorkoutExercises);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, WorkoutDetailsActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("item", items.get(getAdapterPosition()));
//            intent.putExtras(bundle);
//            context.startActivity(intent);
        }
    }
}
