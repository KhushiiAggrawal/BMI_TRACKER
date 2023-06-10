package com.example.bmicalculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<Person> history;

    public HistoryAdapter(Context context,ArrayList<Person> history ){
        this.context = context;
        this.history = history;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.history_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.img.setImageURI(Uri.parse(history.get(position).uri));
        holder.nametxt.setText(history.get(position).name);
        holder.heighttxt.setText("h: "+history.get(position).height + "m");
        holder.weighttxt.setText("w: "+history.get(position).weight + "kg");
        holder.bmitxt.setText("BMI: "+history.get(position).bmi +"kg/m2");

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context)
                        .setTitle("Delete History")
                        .setMessage("Are you sure you want to delete ?")
                        .setIcon(R.drawable.deletedd)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                history.remove(position);
                                notifyItemRemoved(position);
                                try {
                                    SavingAndLoadingHistory.remove_data(context, history);
                                    new File(history.get(position).uri).delete();
                                    Toast.makeText(context,"removed image",Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nametxt,heighttxt,weighttxt,bmitxt;
        ImageView img,deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nametxt=itemView.findViewById(R.id.nametxt);
            heighttxt=itemView.findViewById(R.id.heighttxt);
            weighttxt=itemView.findViewById(R.id.weighttxt);
            bmitxt=itemView.findViewById(R.id.bmitxt);
            img=itemView.findViewById(R.id.img);
            deletebtn=itemView.findViewById(R.id.deletebtn);

        }
    }
}
