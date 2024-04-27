package es.empresa.comergallego;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListView extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public ListView(Context context, ArrayList<String> values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
        textView.setText(values.get(position));
        textView.setTextColor(Color.BLACK);
        return rowView;
    }
}

