package ru.nikartm.drag_and_drop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ru.nikartm.drag_and_drop.R;
import ru.nikartm.drag_and_drop.adapter.CallbackItemTouch;
import ru.nikartm.drag_and_drop.adapter.ItemListAdapter;
import ru.nikartm.drag_and_drop.adapter.ItemTouchHelperCallback;
import ru.nikartm.drag_and_drop.model.DragItem;
import ru.nikartm.drag_and_drop.util.Utils;

public class ListItemFragment extends Fragment implements CallbackItemTouch {

    private ItemListAdapter adapter;
    private List<DragItem> itemList;

    public ListItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initList();
    }

    private void initList() {
        itemList = Utils.createItems();
        adapter = new ItemListAdapter(itemList);
        adapter.setClickListener(this);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_item_grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void itemDrag(int oldPosition, int newPosition) {
        itemList.add(newPosition, itemList.remove(oldPosition));
        adapter.notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void itemSwipe(int swipePosition) {
        itemList.remove(swipePosition);
        adapter.notifyItemRemoved(swipePosition);
    }

    @Override
    public void itemClicked(View view, int position) {
        Toast.makeText(getContext(), "Selected position : " + itemList.get(position).getName(),
                Toast.LENGTH_SHORT).show();
    }
}
