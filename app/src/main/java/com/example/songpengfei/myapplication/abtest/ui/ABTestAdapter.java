package com.example.songpengfei.myapplication.abtest.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanmaster.mguard.R;
import com.example.songpengfei.myapplication.abtest.ABTest;
import com.example.songpengfei.myapplication.abtest.model.ABTestModel;

import java.util.List;

public class ABTestAdapter extends RecyclerView.Adapter {

    private List<ABTestModel> data;
    private LayoutInflater inflater;

    public ABTestAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ABTestVH(inflater.inflate(R.layout.item_abtest, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ABTestVH) {
            final ABTestModel model = data.get(position);
            ((ABTestVH) holder).tvKeyName.setText("name : " + model.keyName);
            ((ABTestVH) holder).tvKey.setText("key : " + model.key);
            ((ABTestVH) holder).tvAction.setText("action : " + model.action);


            ((ABTestVH) holder).adapter.clear();

            int index = 0;
            final String[] valuess = model.values.split(",");
            ((ABTestVH) holder).adapter.addAll(valuess);
            for (int i = 0; i < valuess.length; i++) {
                if (valuess[i].equals(model.value)) {
                    index = i;
                    break;
                }
            }

            ((ABTestVH) holder).vSpinner.setSelection(index);

            ((ABTestVH) holder).vSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String value = valuess[position];
                    model.value = value;
//                    ABTest.getDefault().writeValue(model.action, model.key, model.value);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void addAll(List<ABTestModel> uiABTestModel) {
        data = uiABTestModel;
        notifyDataSetChanged();
    }

    public static class ABTestVH extends RecyclerView.ViewHolder {
        public TextView tvKey;
        public TextView tvAction;
        public TextView tvKeyName;
        public Spinner vSpinner;
        public ArrayAdapter<Object> adapter;

        public ABTestVH(View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.key);
            tvAction = itemView.findViewById(R.id.action);
            tvKeyName = itemView.findViewById(R.id.name);
            vSpinner = itemView.findViewById(R.id.spinner);
            adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item);
            vSpinner.setAdapter(adapter);
        }
    }
}
