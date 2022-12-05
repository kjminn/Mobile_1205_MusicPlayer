package kr.ac.yeonsung.kjminn.mobile_1205_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list1;
    Button btnPlay, btnStop;
    TextView textMusic;
    ProgressBar progressMusic;

    ArrayList<String> arrList;
    String selectedMusic;
    String musicPath = Environment.getExternalStorageDirectory().getPath() + "/";
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Music Player");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        arrList = new ArrayList<String>();

        File[] files= new File(musicPath).listFiles();
        String fileName, fileExt;
        for(File file: files){
            fileName = file.getName();
            fileExt = fileName.substring(fileName.length()-3);
            if(fileExt.equals("mp3")){
                arrList.add(fileName);
            }
        }
        list1 = findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, arrList);
        list1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list1.setAdapter(adapter);
        list1.setItemChecked(0, true);
        selectedMusic = arrList.get(0);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedMusic = arrList.get(position);
            }
        });

        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);
        textMusic = findViewById(R.id.text_music);
        progressMusic = findViewById(R.id.progress_music);
        btnStop.setClickable(false);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = new MediaPlayer();
                try {
                    player.setDataSource(musicPath + selectedMusic);
                    player.prepare();
                    player.start();
                    btnPlay.setClickable(false);
                    btnStop.setClickable(true);
                    textMusic.setText("실행중인 음악: "+selectedMusic);
                    progressMusic.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                player.reset();
                btnPlay.setClickable(true);
                btnStop.setClickable(false);
                textMusic.setText("실행중인 음악: ");
                progressMusic.setVisibility(View.INVISIBLE);
            }
        });

    }
}