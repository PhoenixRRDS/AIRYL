package com.phoenix.airyl;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.phoenix.airyl.Adapter.ChatMsgAdapter;
import com.phoenix.airyl.Pojo.ChatMsg;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mBtnSend;
    private EditText mEditTxtMsg;
    private ImageView mImgView;
    private ChatMsgAdapter mAdapter;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mBtnSend = (FloatingActionButton) findViewById(R.id.btn_send);
        mEditTxtMsg = (EditText) findViewById(R.id.et_message);
        mImgView = (ImageView) findViewById(R.id.iv_image);

        mAdapter = new ChatMsgAdapter(this, new ArrayList<ChatMsg>());

        //code for sending message
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mEditTxtMsg.getText().toString();
                sendMsg(msg);
                mEditTxtMsg.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });

    }

    private void sendMsg (String msg) {
        ChatMsg chatmsg = new ChatMsg(msg, true, false);
        mAdapter.add(chatmsg);
        //respond as Hello world
        mimicOtherMsg("Hello world");
    }

    private void mimicOtherMsg (String msg) {
        ChatMsg chatmsg = new ChatMsg(msg, false, false);
        mAdapter.add(chatmsg);
    }

    private void sendMsg () {
        ChatMsg chatmsg = new ChatMsg(null, true, true);
        mAdapter.add(chatmsg);
        mimicOtherMsg();
    }

    private void mimicOtherMsg() {
        ChatMsg chatMsg = new ChatMsg(null, false, true);
        mAdapter.add(chatMsg);
    }
}
