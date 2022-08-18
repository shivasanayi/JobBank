package com.example.rs.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.rs.myapplication.Model.GetJobsObject;
import com.example.rs.myapplication.R;


public class GetJobsAdapter extends RecyclerView.Adapter<GetJobsAdapter.GetJobsViewHolder> {

    ArrayList<GetJobsObject> gamelist;

    public GetJobsAdapter(ArrayList<GetJobsObject> gamelist) {
        this.gamelist = gamelist;
    }

    @NonNull
    @Override
    public GetJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_jobs, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        GetJobsViewHolder rcv = new GetJobsViewHolder(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull GetJobsViewHolder holder, int position) {

        holder.Job.setText("نام شغل :" + gamelist.get(position).getName());
        holder.City.setText("شهر :" + gamelist.get(position).getCity());
        holder.Address.setText("آدرس :" + gamelist.get(position).getAddress());

        holder.Job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return gamelist.size();
    }

    public class GetJobsViewHolder extends RecyclerView.ViewHolder {
        public TextView Job, City, Address;

        public GetJobsViewHolder(View view) {
            super(view);
            Job = view.findViewById(R.id.JobTitle);
            City = view.findViewById(R.id.City);
            Address = view.findViewById(R.id.Address);

        }
    }


}
