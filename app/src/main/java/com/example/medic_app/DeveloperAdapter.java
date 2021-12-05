package com.example.medic_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.Viewholder> {

    private Context context;
    private ArrayList<DeveloperModel> developerModelArrayList;

    public DeveloperAdapter(Context context, ArrayList<DeveloperModel> developerModelArrayList){
        this.context = context;
        this.developerModelArrayList = developerModelArrayList;
    }

    @NonNull
    @Override
    public DeveloperAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_us_card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeveloperAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        DeveloperModel model = developerModelArrayList.get(position);
        holder.developerNameTV.setText(model.getDevelopername());
        holder.developeremailTV.setText("" + model.getDeveloperemail());
        holder.developerWorkTV.setText("" + model.getDeveloperwork());
        holder.developerIV.setImageResource(model.getDeveloperimage());
    }
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return developerModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView developerIV;
        private TextView developerNameTV, developeremailTV,developerWorkTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            developerNameTV = itemView.findViewById(R.id.peoplename);
            developeremailTV = itemView.findViewById(R.id.peoplemail);
            developerWorkTV = itemView.findViewById(R.id.peoplework);
            developerIV = itemView.findViewById(R.id.peoplephotos);
            itemView.setOnTouchListener(new OnSwipeTouchListener(context){
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Remove Contributors from about us?");
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.ic_baseline_exit_to_app_48);
                    builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position=getPosition();
                            developerModelArrayList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, developerModelArrayList.size());
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            });

        }
    }

}
