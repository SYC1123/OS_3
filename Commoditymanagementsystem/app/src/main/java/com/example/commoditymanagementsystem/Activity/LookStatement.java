package com.example.commoditymanagementsystem.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LookStatement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_statement);
        WebView webView = findViewById(R.id.view);
        webView.getSettings().setJavaScriptEnabled(true);//设置浏览器属性，让WebView支持JavaScript脚本
        webView.setWebViewClient(new WebViewClient());//当需要从一个网页跳转到另一个网页时，我们希望目标网页仍然在当前WebView中显示，而不是打开系统浏览器
        webView.loadUrl("https://www.bmob.cn/app/browser/256901/1404062");//传入相应的网址就可以打开了
        final TextView income = findViewById(R.id.income);
        final TextView pay = findViewById(R.id.pay);

        Date date = new Date();
        //String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Statement> statementBmobQuery = new BmobQuery<>();
        statementBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
        statementBmobQuery.findObjects(new FindListener<Statement>() {
            @Override
            public void done(List<Statement> object, BmobException e) {
                if (e == null) {
                    float income_sum = 0;
                    float pay_sum = 0;
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).getState().equals("采购")) {
                            float a = (object.get(i).getNum() * object.get(i).getPrice());
                            pay_sum += a;
                        } else {
                            float a = (object.get(i).getNum() * object.get(i).getPrice());
                            income_sum += a;
                        }
                    }
                    pay.setText(pay_sum+"元");
                    income.setText(income_sum+"元");
                } else {

                }
            }
        });
    }

}
