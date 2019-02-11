package com.denis.pavlovich.weatherapp.activity.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RecyclerListViewHolder> {

    private final List<String> list;
    private final CitiesFragment.OnItemClickListener listener;
    //поддерживаю только single select режим
    private static int selectedPosition = 0;
    //Чтобы избегать утечек памяти, если элемент будет из памяти удален
    private static WeakReference<? extends View> selectedItem;


    RecyclerListAdapter(List<String> list, CitiesFragment.OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_activated_1, viewGroup, false);
        textView.setBackground(viewGroup.getResources().getDrawable(
                R.drawable.item_selector,
                viewGroup.getContext().getTheme()));
        RecyclerListViewHolder viewHolder = new RecyclerListViewHolder(textView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListViewHolder recyclerListViewHolder, int i) {
        recyclerListViewHolder.textView.setText(list.get(i));
        if (i == selectedPosition) {
            recyclerListViewHolder.textView.setSelected(true);
            selectedItem = new WeakReference<>(recyclerListViewHolder.textView);
        } else {
            recyclerListViewHolder.textView.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RecyclerListViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        RecyclerListViewHolder(@NonNull final TextView itemView, final CitiesFragment.OnItemClickListener listener) {
            super(itemView);
            this.textView = itemView;
            this.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getLayoutPosition();
                    listener.itemClicked(selectedPosition);
                    if (selectedItem != null) {
                        View view = selectedItem.get();
                        if (view != null) {
                            view.setSelected(false);
                        }
                    }
                    itemView.setSelected(true);
                    selectedItem = new WeakReference<>(itemView);
                }
            });

        }
    }
}
