package com.damask.chattr;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;


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
        rootRef = new Firebase("https://dazzling-heat-7847.firebaseio.com");

        rootRef.child("quotes").keepSynced(true);
        rootRef.child("quotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ChatMessage current;
                ArrayList<ChatMessage> msgs = new ArrayList<ChatMessage>((int) (dataSnapshot.getChildrenCount()));
                for (DataSnapshot m : dataSnapshot.getChildren()) {
                    current = m.getValue(ChatMessage.class);
                    messenger.add(current);
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

//    public void sendMessage(View v) {
//        EditText textField = (EditText) findViewById(R.id.textField);
//        String message = textField.getText().toString();
//
//        if (message.length()>0) {
//            String timestamp = new Timestamp(new java.util.Date().getTime()).toString();
//            rootRef.child("quotes").push().setValue(new ChatMessage("Damask", timestamp, message, null));
//            textField.setText("");
//        }
//    }


    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return messenger.size();
        }
        @Override
        public Object getItem(int position) {
            return messenger.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position % messenger.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;

            if(convertView==null){
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.avatar = (ImageView) view.findViewById(R.id.avatar);
                holder.messageBody = (TextView) view.findViewById(R.id.messageBody);
                holder.timestamp = (TextView) view.findViewById(R.id.timestamp);
                holder.username = (TextView) view.findViewById(R.id.username);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            ChatMessage message = messenger.get(position);
            holder.username.setText(message.getAuthor());
            holder.messageBody.setText(message.getTitle());
            holder.timestamp.setText(message.getDate());
            Picasso.with(MainActivity.this).load(message.getPicUrl()).into(holder.avatar);

            return view;
        }
    }

    private class ViewHolder {
        TextView messageBody;
        TextView username;
        TextView timestamp;
        ImageView avatar;
    }


}
