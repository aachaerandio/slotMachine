package com.aachaerandio.slotmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aachaerandio.slotmachine.R;
import com.aachaerandio.slotmachine.data.State;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Araceli on 27/08/2014.
 */
public class StateItemAdapter extends ArrayAdapter<State> {

    public StateItemAdapter(Context context, int resource, List<State> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        State item = getItem(position);
        View view;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.list_item_state, parent, false);

            holder = new ViewHolder();
            //holder.id = (TextView)view.findViewById(R.id._id);
            holder.iconA = (ImageView)view.findViewById(R.id.list_imageView1);
            holder.iconB = (ImageView)view.findViewById(R.id.list_imageView2);
            holder.iconC = (ImageView)view.findViewById(R.id.list_imageView3);

            //holder.date = (TextView)view.findViewById(R.id.date);
            //holder.datetime = (TextView)view.findViewById(R.id.datetime);
            view.setTag(holder);

        } else {
            view = convertView;
            // Using ViewHolder is avoided calling findViewById() repeatedly
            holder = (ViewHolder) view.getTag();
        }

        //holder.id.setText(String.valueOf(item.getId()));
        holder.iconA.setImageResource(item.getIconA().listId);
        holder.iconB.setImageResource(item.getIconB().listId);
        holder.iconC.setImageResource(item.getIconC().listId);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        //holder.date.setText(df.format(item.getCreated()));
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        //holder.datetime.setText(tf.format(item.getCreated()));

        return view;
    }




    static class ViewHolder {
        TextView id;
        ImageView iconA;
        ImageView iconB;
        ImageView iconC;
    }

}
