package com.example.commoditymanagementsystem.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.commoditymanagementsystem.Activity.ChangeMessage;
import com.example.commoditymanagementsystem.Activity.ItemManager;
import com.example.commoditymanagementsystem.Activity.newGood;
import com.example.commoditymanagementsystem.Activity.oldGood;
import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GoodManager extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goodmanagerfragment, container, false);
        final Button getGood = view.findViewById(R.id.getGood);
        Button changeGoodmes = view.findViewById(R.id.changeGoodMes);
        final EditText inputGood = view.findViewById(R.id.inputGood);
        changeGoodmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BmobUser.isLogin()) {
                    Intent intent = new Intent(getActivity(), ChangeMessage.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dia = new AlertDialog.Builder(getActivity());
                    dia.setTitle("采购部门禁用！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                }

            }
        });

        getGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputGood.getText().toString().equals("")) {
                    AlertDialog.Builder dia = new AlertDialog.Builder(getActivity());
                    dia.setTitle("请输入信息！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                } else {
                    BmobQuery<Good> categoryBmobQuery = new BmobQuery<>();
                    categoryBmobQuery.addWhereEqualTo("name", inputGood.getText().toString());
                    categoryBmobQuery.findObjects(new FindListener<Good>() {
                        @Override
                        public void done(List<Good> object, BmobException e) {
                            if (e == null) {
                                Intent intent = new Intent(getActivity(), oldGood.class);
                                intent.putExtra("objectId", object.get(0).getObjectId());
                                intent.putExtra("num", object.get(0).getNum() + "");
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), newGood.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}
