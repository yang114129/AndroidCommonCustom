package com.yang.custom;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.message.entity.UMessage;
import com.yang.custom.activity.PushActivity;
import com.yang.custom.base.BaseActivity;
import com.yang.custom.widget.ReadTextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initializeView() {
        initToolBarRightTxt("HAHA", "OO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(PushActivity.class);
            }
        });
        checkNet(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "REFRESH", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initializeData() {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("go_app", "2");
        maps.put("url", "baidu");
        String data = new JSONObject(maps).toString();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg_id", "123");
        map.put("display_type", "notification");
        map.put("body", maps);
        map.put("extra", maps);
        try {
            UMessage msg = new UMessage(new JSONObject(map));
            String s = msg.extra.get("url");
            System.out.print(s + "hahahah--" + new JSONObject(map).toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str_en = "<p> renxu autumn July, Jiwang, Suzi and guest boating tours under Chibi. Xu breeze, rippleless. Wine is off, chanting the moon poetry, song slim chapter. A little while, months of Dongshan, wandering in the bullfight. Dew Yokoe, Shuiguang next day. Even a reed like, Ling Wan Qing loss. The vast Feng seems such as virtual Yufeng, and not to stop; waving as aloof, emergence and immortal. (Feng Tong) </p> <p>    and drinking music, I and song. The Song said: \"Zhao Gui Xi Lan paddle upstream come blow out light and air. You come to my arms, at beauty come one day.\" A blowing flute, and the Yi song. The hum of course, such as resentment, such as mu of bamboo, linger on faintly, as if weeping and complaining. Dance Youhe the cry of a hidden dragon boat. </p> <p> Suzi stern, sat, and asked the guests said: \"what is it?\" The guests said: \"yuemingxingxi, Ukraine magpie flying south.\" This is not a poem of Cao Mengde? Seomang Xiakou, looking east Wuchang mountains, Miao Yu, between green and the non shuro Meng moral trapped in between? The broken Jingzhou, Jiangling, East River, a convoy of ships thousands of miles, empty word flags, pour wine, having a lance sideward and poetizing Linjiang, a hero of the age and where is also solid,? Kuangwu and son, in Jiang Nagisa, Lu fish and shrimp and the friends of the elk, riding a leaf boat, belonging to lift bottle gourd. Send ephemera in the world, a boundless sea. Sad moment of my life, the infinite envy of Yangtze river. With the fly to roam, hold the moon and long end. Don't know will suddenly left, supporting ring in Beifeng.\" </p>";

        ReadTextView readtxt = (ReadTextView) findViewById(R.id.readtxt);
        TextView txt = (TextView) findViewById(R.id.txt);
        txt.setText(Html.fromHtml(str_en).toString());
        readtxt.setText(Html.fromHtml(str_en).toString());
        txt.setVisibility(View.GONE);
    }
}
