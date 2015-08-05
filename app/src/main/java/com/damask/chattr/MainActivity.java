package com.damask.chattr;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class MainActivity extends ActionBarActivity {
    private ListView lv;
    private Firebase rootRef;
    private MyAdapter ad;
    ArrayList<ChatMessage> messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messenger = new ArrayList<ChatMessage>();
        ad = new MyAdapter();
        lv = (ListView) findViewById(R.id.messages);
        lv.setAdapter(ad);

        Firebase.setAndroidContext(this);
        rootRef = new Firebase("https://brilliant-inferno-9405.firebaseio.com");

        rootRef.child("messages").keepSynced(true);
        rootRef.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ChatMessage current;
                ArrayList<ChatMessage> msgs = new ArrayList<ChatMessage>((int) (dataSnapshot.getChildrenCount()));
                for (DataSnapshot m : dataSnapshot.getChildren()) {
                    current = m.getValue(ChatMessage.class);
                    messenger.add(current);
                    Log.i("User", current.getmText());
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View v) {
        EditText textField = (EditText) findViewById(R.id.textField);
        String message = textField.getText().toString();

        if (message.length()>0) {
            rootRef.child("messages").push().setValue(new ChatMessage("Damask", message));
            textField.setText("");
        }
    }


    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return messenger.size();
        }
        @Override
        public Object getItem(int position) {
            return messenger.get(position%messenger.size());
        }
        @Override
        public long getItemId(int position) {
            return position % messenger.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if(convertView!=null ){
                tv=(TextView)convertView;
            }else {
                tv = new TextView(MainActivity.this);
                tv.setTextSize(15);
                tv.setPadding(5, 5, 5, 5);
            }
            String user = messenger.get(position%messenger.size()).getmUser();
            String messageBody = messenger.get(position%messenger.size()).getmText();
            tv.setText(user + ": " + messageBody);
            return tv;
        }
    }

}
