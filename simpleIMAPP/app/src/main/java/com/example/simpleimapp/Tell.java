package com.example.simpleimapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Tell extends AppCompatActivity {

    private final int VIEW_TYPE = 0xb01;
    private final int VIEW_TYPE_LEFT = -10;
    private final int VIEW_TYPE_RIGHT = -11;

    private final int MESSAGE = 0xb02;

    private ArrayList<HashMap<Integer, Object>> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell);

        final ListView listView = (ListView) findViewById(android.R.id.list);

        items = new ArrayList<HashMap<Integer, Object>>();

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                HashMap<Integer, Object> map = new HashMap<Integer, Object>();
                map.put(VIEW_TYPE, VIEW_TYPE_LEFT);
                map.put(MESSAGE, "消息内容" + i);
                items.add(map);
            } else {
                HashMap<Integer, Object> map = new HashMap<Integer, Object>();
                map.put(VIEW_TYPE, VIEW_TYPE_RIGHT);
                map.put(MESSAGE, "消息内容" + i);
                items.add(map);
            }
        }

        final MyAdapter adapter = new MyAdapter(this, -1);
        listView.setAdapter(adapter);

        final EditText msgEditText = (EditText) findViewById(R.id.msgEditText);
        Button button = (Button) findViewById(R.id.msgSend);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = msgEditText.getText() + "";

                HashMap<Integer, Object> map = new HashMap<Integer, Object>();
                map.put(VIEW_TYPE, VIEW_TYPE_RIGHT);
                map.put(MESSAGE, msg);
                items.add(map);

                adapter.notifyDataSetChanged();

                // 发送后清空输入框内容
                msgEditText.setText(null);

                // 输入框发送消息后将ListView滚动到最底部
                listView.setSelection(ListView.FOCUS_DOWN);
            }
        });
    }

    private class MyAdapter extends ArrayAdapter {

        private LayoutInflater layoutInflater;

        public MyAdapter(Context context, int resource) {
            super(context, resource);
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            int type = getItemViewType(pos);
            String msg = getItem(pos);

            switch (type) {
                case VIEW_TYPE_LEFT:
                    convertView = layoutInflater.inflate(R.layout.left, null);
                    TextView textLeft = (TextView) convertView.findViewById(R.id.textView);
                    textLeft.setText(msg);
                    break;

                case VIEW_TYPE_RIGHT:
                    convertView = layoutInflater.inflate(R.layout.right, null);
                    TextView textRight = (TextView) convertView.findViewById(R.id.textView);
                    textRight.setText(msg);
                    break;
            }

            return convertView;
        }

        @Override
        public String getItem(int pos) {
            String s = items.get(pos).get(MESSAGE) + "";
            return s;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int pos) {
            int type = (Integer) items.get(pos).get(VIEW_TYPE);
            return type;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }

}