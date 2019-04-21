package com.example.a433gamelistwithfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random();
    private GameListAdapter adapter;
    private List<Game> gameList = new ArrayList<>();
    public static final String FOLDER_NAME = "MY FOLDER";
    private static final String FILE_NAME = "games.txt";
    private static final String LOG_TAG = "СМОТРИ СЮДА";
    private Writer writer;
    File gameFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveText(getString(R.string.games));

        ListView listView = findViewById(R.id.BlizzardApps);
        updateList();

        adapter = new GameListAdapter(this, gameList);
        listView.setAdapter(adapter);
    }

    public File getNoteFile() {
        return new File(getNoteFolder(this, FOLDER_NAME), FILE_NAME);
    }

    public File getNoteFolder(Context context, String albumName) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    private void saveText(String game) {
        gameFile = getNoteFile();
        try {
            writer = new FileWriter(gameFile);
            writer.append(game);
            writer.close();
        } catch (IOException e) {
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String loadText() {
        gameFile = getNoteFile();
        if (!gameFile.canRead()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileReader(gameFile))) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(";");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            Toast.makeText(this, "Cannot access file!", Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "loadText", e);
        }
        return null;
    }

    public void updateList() {
        gameList.clear();
        String filetext = loadText();
        String[] arrayContent = filetext.split(";");
        for (String item : arrayContent) {
            gameList.add(new Game(item));
        }
        //return values;
    }
}
