package com.example.commoditymanagementsystem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.commoditymanagementsystem.Fragment.FindGood;
import com.example.commoditymanagementsystem.Fragment.GoodManager;
import com.example.commoditymanagementsystem.Fragment.Userset;
import com.example.commoditymanagementsystem.R;

import cn.bmob.v3.BmobUser;

public class ItemManager extends AppCompatActivity {

    private String type="";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard://商品查找
                    replacefragment(new FindGood(),R.id.fragment);
                    return true;
                case R.id.navigation_home://商品管理
                    if(type.equals("saleman")){
                        AlertDialog.Builder dia = new AlertDialog.Builder(ItemManager.this);
                        dia.setTitle("销售人员禁用！");
                        dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dia.show();

                    }else{
                        replacefragment(new GoodManager(),R.id.fragment);
                    }
                    return true;
                case R.id.navigation_notifications://个人设置
                    replacefragment(new Userset(), R.id.fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_manager);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dashboard);
        Intent intent = getIntent();
        type=intent.getStringExtra("type");
        //replacefragment(new FindGood(),R.id.fragment);

    }


    private void replacefragment(Fragment fragment, int layout) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layout, fragment);
        transaction.commit();
    }

    public String getType() {
        return type;
    }
}
