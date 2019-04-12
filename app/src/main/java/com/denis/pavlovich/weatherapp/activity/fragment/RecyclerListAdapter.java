package com.denis.pavlovich.weatherapp.activity.fragment;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.R;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_recycler_view, viewGroup, false);
        TextView textView = view.findViewById(R.id.city_name);
        textView.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.item_selector));
        return new RecyclerListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListViewHolder recyclerListViewHolder, int i) {
        recyclerListViewHolder.textView.setText(list.get(i));
        if (i == selectedPosition) {
            recyclerListViewHolder.textView.setSelected(true);
            selectedItem = new WeakReference<>(recyclerListViewHolder.textView);
            //  Чтобы при запуске приложения в горизонтальном режиме, сразу показать информацию о погоде
            if (recyclerListViewHolder.textView.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                recyclerListViewHolder.textView.callOnClick();
            }
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

        View view;

        RecyclerListViewHolder(@NonNull final View itemView, final CitiesFragment.OnItemClickListener listener) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.city_name);
            view = itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getLayoutPosition();
                    listener.itemClicked(selectedPosition, String.valueOf(textView.getText()));
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
