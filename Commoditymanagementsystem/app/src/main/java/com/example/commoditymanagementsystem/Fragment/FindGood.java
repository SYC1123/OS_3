package com.example.commoditymanagementsystem.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.commoditymanagementsystem.GoodAdapter;
import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindGood extends Fragment {
    List<Good> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bmob.initialize(getContext(), "5b425154964e46cd19279327ae06c390");
        final View view = inflater.inflate(R.layout.findgoodfragment, container, false);
        final ListView listView = view.findViewById(R.id.goodlist);
        BmobQuery<Good> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> categories, BmobException e) {
                if (e == null) {
                    Log.d("aaaa", "done: ");
                    list = categories;
                   // Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
                    GoodAdapter adapter = new GoodAdapter(getContext(), R.layout.good_item, list);
                    listView.setAdapter(adapter);
                } else {
                    Log.d("error", "done: "+e.toString());
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Good good=list.get(position);
                AlertDialog.Builder dia = new AlertDialog.Builder(getContext());
                dia.setTitle("商品信息");
                dia.setMessage(good.getName()+"\n"
                +"类别："+good.getCategory()+"\n"
                +"位置："+good.getLocation()+"\n"
                +"库存："+good.getNum()+"\n"
                +"价格："+good.getUnit_price()+"元");
                // dia.setCancelable(false);
                dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                    }
                });
                dia.show();

            }
        });
        return view;
    }
}
