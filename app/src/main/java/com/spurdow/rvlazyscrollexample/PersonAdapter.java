package com.spurdow.rvlazyscrollexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidluvellejoseph on 2/28/16.
 */
public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Person> mList;

    public PersonAdapter(List<Person> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.person_row , parent , false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Person person = getPerson(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txt_person_name.setText(person.getName());
    }

    public Person getPerson(int position){
        return mList.get(position);
    }

    public void add(Person p ){
        mList.add(p);
        notifyItemInserted(mList.indexOf(p));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<Person> clone(){
        return new ArrayList<>(mList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_person_name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_person_name = (TextView) itemView.findViewById(R.id.txt_person_name);
        }
    }
}
