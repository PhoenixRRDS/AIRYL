package com.phoenix.airyl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.phoenix.airyl.Pojo.ChatMsg;
import com.phoenix.airyl.R;

import java.util.List;

/**
 * Created by mouri on 10/11/17.
 */

public class ChatMsgAdapter extends ArrayAdapter<ChatMsg> {

    public static final int MY_MSG = 0, OTHER_MSG = 1, MY_IMG = 2, OTHER_IMG = 3;

    public ChatMsgAdapter (Context context, List<ChatMsg> data) {
        super(context, R.layout.item_user_msg, data);
    }

    @Override
    public int getViewTypeCount () {
        //my msg, other msg, my img, other img
        return 4;
    }

    @Override
    public int getItemViewType (int pos) {
        ChatMsg item = getItem(pos);

        if (item.isMine() && !item.isImg()) return MY_MSG;
        else if (!item.isMine() && !item.isImg()) return OTHER_IMG;
        else if (item.isMine() && item.isImg()) return MY_IMG;
        else return OTHER_IMG;
    }

    @Override
    public View getView (int pos, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(pos);
        if (viewType == MY_MSG) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user_msg, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(pos).getContent());
        }
        else if (viewType == OTHER_MSG) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bot_msg, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(pos).getContent());
        }
        else if (viewType == MY_IMG) {

        }
        else {}

        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }
}
