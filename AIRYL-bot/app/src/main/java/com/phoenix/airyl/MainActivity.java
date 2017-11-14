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

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mBtnSend;
    private EditText mEditTxtMsg;
    private ImageView mImgView;
    private ChatMsgAdapter mAdapter;

    public Bot bot;
    public static Chat chat;

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

        boolean a = isSDCARDAvailable(); //checking SD card availability
        AssetManager assets = getResources().getAssets(); //receiving assets from app dir
        File jaydir = new File(Environment.getExternalStorageDirectory().toString() + "airyl/bots/AIRYL");
        boolean b = jaydir.mkdirs();
        if (jaydir.exists()) {
            try { //Reading the file
                for (String dir : assets.list("AIRYL")) {
                    File subdir = new File (jaydir.getPath() + "/" + dir);
                    boolean subdir_check = subdir.mkdirs();
                    for (String file: assets.list("AIRYL/" + dir)) {
                        File f = new File(jaydir.getPath() + "/" + dir + "/" + file);
                        if (f.exists()) {
                            continue;
                        }
                        InputStream in = null;
                        OutputStream out = null;
                        in = assets.open("AIRYL/" + dir + "/" + file);
                        out = new FileOutputStream(jaydir.getPath() + "/" + dir + "/" + file);
                        //copy file from mem to SD card or 2ndary storage
                        copyFile(in,out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //get the working dir
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "airyl/";
        System.out.println("Working Directory: " + MagicStrings.root_path);
        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        //assign aiml files to bot for processing
        bot = new Bot("AIRYL", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String args[] = null;
        mainFunction(args);
    }

    //checking SD card availability
    public static boolean isSDCARDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
    }

    //copying the file
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != 1) {
            out.write(buffer, 0 ,read);
        }
    }

    //request and response of user and bot respectively
    public static void mainFunction(String[] args) {
        MagicBooleans.trace_mode = false;
        System.out.println("trace mode = " + MagicBooleans.trace_mode);
        Graphmaster.enableShortCuts = true;
        Timer timer = new Timer();
        String request = "Hello.";
        String response = chat.multisentenceRespond(request);

        System.out.println("Human: " + request);
        System.out.println("Bot: " + response);
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
