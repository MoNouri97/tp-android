package com.example.contactmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class MyAdapter extends BaseAdapter implements Filterable {

    private Context con;
    private ArrayList<Contact> filteredContacts;
    private ArrayList<Contact> allContacts;

    MyAdapter(Context con,ArrayList<Contact> data){
        this.con = con;
        this.filteredContacts = new ArrayList<>(data);
        this.allContacts = new ArrayList<>(data);

    }
    @Override
    public int getCount() {
        return filteredContacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return filteredContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(con);
        //parse code xml
        LinearLayout l = (LinearLayout) inf.inflate(R.layout.view_contact,null);
        TextView tvnom = l.findViewById(R.id.tvnom_view);
        TextView tvprenom = l.findViewById(R.id.tvprenom_view);
        TextView tvnum = l.findViewById(R.id.tvnum_view);
        Contact c = getItem(position);
        tvnom.setText(c.nom);
        tvprenom.setText(c.prenom);
        tvnum.setText(c.numero);
        return l;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint.toString().isEmpty()){
                filteredContacts.addAll(allContacts);
            }
            ArrayList<Contact> result = new ArrayList<Contact>();
            for (Contact c : allContacts) {
                if (c.nom.toLowerCase().contains(constraint) || c.prenom.toLowerCase().contains(constraint)) {
                    result.add(c);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = result;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredContacts.clear();
            filteredContacts.addAll((Collection<? extends Contact>) results.values);
            notifyDataSetChanged();
        }
    };
}
