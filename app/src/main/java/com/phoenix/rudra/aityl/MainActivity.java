/* Sheldon - MainActivity.java
 *
 * Copyright (C) 2017 Rudra Nil Basu <rudra.nil.basu.1996@gmail.com>
 *
 * Authors:
 *   Rudra Nil Basu <rudra.nil.basu.1996@gmail.com>
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.phoenix.rudra.aityl;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.phoenix.rudra.aityl.Adapter.ChatMessageAdapter;
import com.phoenix.rudra.aityl.POJO.ChatMessage;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.Timer;
import org.xml.sax.ContentHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Bot bot;
    public static Chat chat;

    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);

        //to save logfiles by date
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        final String logFile = "log-" + date + ".txt";
        File logDir = new File(Environment.getExternalStorageDirectory().toString() + "/hari/logs");
        logDir.mkdirs();
        File logOutput = new File(logDir, logFile);


        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = mEditTextMessage.getText().toString();

                String response = chat.multisentenceRespond(message);
                if (TextUtils.isEmpty(message)) {
                    return;
                }

                sendMessage(message);
                mimicOtherMessage(response);

                //writing responses and messages to file
                String msg = "User: " + message;
                String resp = "Bot: " + response;
                FileOutputStream logStream;
                try {
                    logStream = openFileOutput(logFile, Context.MODE_PRIVATE | Context.MODE_APPEND);
                    logStream.write(msg.getBytes());
                    logStream.write(resp.getBytes());
                    logStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });

        // add "Hari" folder form assets to internal storage
        // checking for SD card availability
        boolean sdCardState = isSDCardAvailable();
        // receiving the asset from the app directory
        AssetManager assets = getResources().getAssets();
        File botDir = new File(Environment.getExternalStorageDirectory().toString() + "/hari/bots/Hari");
        boolean b = botDir.mkdirs();
        if (botDir.exists()) {
            // Reading the file
            try {
                for (String dir: assets.list("Hari")) {
                    File subdir = new File(botDir.getPath() + "/" + dir);
                    boolean subdir_check = subdir.mkdirs();
                    for (String file : assets.list("Hari/" + dir)) {
                        File _file = new File(botDir.getPath() + "/" + dir + "/" + file);
                        if (_file.exists()) {
                            continue;
                        }

                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        inputStream = assets.open("Hari/" + dir + "/" + file);
                        outputStream = new FileOutputStream(botDir.getPath() + "/" + dir + "/" + file);
                        // copy file from assets to SD card
                        copyFile(inputStream, outputStream);
                        inputStream.close();
                        inputStream = null;
                        outputStream.flush();
                        outputStream.close();
                        outputStream = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // get the working dir
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/hari";
        System.out.println("Working Directory = " + MagicStrings.root_path);
        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        //Assign the AIML files to bot for processing
        bot = new Bot("Hari", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String[] args = null;
        mainFunction(args);
    }

    public static boolean isSDCardAvailable()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void writeToLog(String data, Context context) {

    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }
    }

    public static void mainFunction(String[] args)
    {
        MagicBooleans.trace_mode = false;
        System.out.println("trace mode = " + MagicBooleans.trace_mode);
        Graphmaster.enableShortCuts = true;
        Timer timer = new Timer();
        String request = "Hello.";
        String response = chat.multisentenceRespond(request);

        System.out.println("Human: "+request);
        System.out.println("Robot: " + response);
    }

    private void sendMessage(String message)
    {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);

        // default "Hello World" Response
        mimicOtherMessage("Hello World");
    }

    private void mimicOtherMessage(String message)
    {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }

    private void sendMessage()
    {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);
    }

    private void mimicOtherMessage()
    {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }
}
