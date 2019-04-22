package com.example.a433gamelistwithfile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends BaseAdapter {
    private List<Game> games;
    private LayoutInflater inflater;

    GameListAdapter(Context context, List<Game> games) {
        if (games == null) {
            this.games = new ArrayList<>();
        } else {
            this.games = games;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(Game item) {
        this.games.add(item);
        notifyDataSetChanged();
    }

    void removeItem(int position) {
        games.remove(position);
        notifyDataSetChanged();

    }

    // Обязательный метод абстрактного класса BaseAdapter.
    // Он возвращает колличество элементов списка.
    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Game getItem(int position) {
        if (position < games.size()) {
            return games.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.game_list_item, parent, false);
        }
        final Game game = games.get(position);

        TextView title = view.findViewById(R.id.title);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        Button addButton = view.findViewById(R.id.addButton);
        final EditText gameName = view.findViewById(R.id.gameName);
        title.setText(game.getName());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                StringBuilder stringBuilder = new StringBuilder();

                for (Game game : games) {
                    stringBuilder.append(game.getName());
                    stringBuilder.append(";");
                }
                ((MainActivity) v.getContext()).saveText(stringBuilder.toString());
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(new Game(gameName.getText().toString()));
                StringBuilder stringBuilder = new StringBuilder();
                for (Game game : games) {
                    stringBuilder.append(game.getName());
                    stringBuilder.append(";");
                }
                ((MainActivity) v.getContext()).saveText(stringBuilder.toString());
            }
        });


        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),
                        "Name: " + game.getName() + "\n" +
                                "Pressed: " + true,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return view;
    }

}
