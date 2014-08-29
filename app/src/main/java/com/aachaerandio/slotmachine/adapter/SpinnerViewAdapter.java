package com.aachaerandio.slotmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.aachaerandio.slotmachine.R;
import com.aachaerandio.slotmachine.data.State;

import java.util.List;

/**
 * Created by Araceli on 27/08/2014.
 */
public class SpinnerViewAdapter extends ArrayAdapter<State.SlotIcon> {

    public SpinnerViewAdapter(Context context, int resource, List<State.SlotIcon> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        State.SlotIcon item = getItem(position);
        View view;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.spin_list_item, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView)view.findViewById(R.id.list_imageView);

            view.setTag(holder);

        } else {
            view = convertView;
            // Using ViewHolder is avoided calling findViewById() repeatedly
            holder = (ViewHolder) view.getTag();
        }

        holder.icon.setImageResource(item.slotId);

        return view;
    }




    static class ViewHolder {
        ImageView icon;

    }

}
