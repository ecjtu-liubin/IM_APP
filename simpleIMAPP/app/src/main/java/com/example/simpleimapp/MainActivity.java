package com.example.simpleimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<People> peopleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton radio3 = (RadioButton) findViewById(R.id.radio3);
        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Me.class);
                startActivity(intent);
            }
        });

        initPeoples();

        PeopleAdapter adapter = new PeopleAdapter(MainActivity.this, R.layout.friend_list, peopleList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Tell.class);
                startActivity(intent);
            }
        });
    }

    private void initPeoples() {
        for (int i = 0; i < 3; i++) {
            People cp1 = new People("张三", "最新消息显示区域", R.drawable.img_1);
            peopleList.add(cp1);
            People cp2 = new People("李四", "最新消息显示区域", R.drawable.img_2);
            peopleList.add(cp2);
            People cp3 = new People("王五", "最新消息显示区域", R.drawable.img_3);
            peopleList.add(cp3);
        }
    }

    public class PeopleAdapter extends ArrayAdapter<People> {

        private int resourceId;

        public PeopleAdapter(Context context, int textViewResourceID, List<People> objects) {
            super(context, textViewResourceID, objects);
            resourceId = textViewResourceID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            People people = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ImageView peopleImage = (ImageView) view.findViewById(R.id.img1);
            TextView peopleName = (TextView) view.findViewById(R.id.cp1);
            TextView peopleNews = (TextView) view.findViewById(R.id.cp1_1);

            peopleImage.setImageResource(people.getImageId());
            peopleName.setText(people.getName());
            peopleNews.setText(people.getNews());

            return view;
        }
    }

    public class People {

        private String name;
        private int imageId;
        private String news;

        public People(String name, String news, int imageId) {
            this.imageId = imageId;
            this.name = name;
            this.news = news;
        }

        public String getName() {
            return name;
        }

        public String getNews() {
            return news;
        }

        public int getImageId() {
            return imageId;
        }

    }
}