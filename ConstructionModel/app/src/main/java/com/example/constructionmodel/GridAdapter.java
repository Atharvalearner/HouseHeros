package com.example.constructionmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class GridAdapter extends BaseAdapter implements Filterable {
    Context context;
    String[] occ_names;
    int[] occ_images;
    private ArrayList<String> temporaryListFull;

    public GridAdapter(Context context, String[] occ_names, int[] occ_images) {
        this.context = context;
        this.occ_names = occ_names;
        this.occ_images = occ_images;
        this.temporaryListFull = new ArrayList<String>(Arrays.asList(occ_names));
    }

    @Override
    public int getCount() {
        return occ_names.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.workers_block_view,null);

        ImageView imageView = view1.findViewById(R.id.occupation_image);
        TextView textView = view1.findViewById(R.id.occupation_name);

        imageView.setImageResource(occ_images[position]);
        textView.setText(occ_names[position]);

        return view1;
    }

    @Override
    public Filter getFilter() {
        return tempFlter;
    }
    private Filter tempFlter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filterdList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filterdList.addAll(temporaryListFull);
            }else {
                for(String item: occ_names){
                    if(item.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterdList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            occ_names = ((ArrayList<String>) filterResults.values).toArray(new String[0]);
            notifyDataSetChanged();
        }
    };
}
