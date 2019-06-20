package com.example.commoditymanagementsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.commoditymanagementsystem.Activity.ItemManager;
import com.example.commoditymanagementsystem.Activity.MainActivity;
import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class Userset extends Fragment {
    private TextView mUsername;
    private TextView mPhone;
    private TextView mType;
    private Button outLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usersetfragment, container, false);
        Bmob.initialize(getContext(), "5b425154964e46cd19279327ae06c390");
        mUsername = view.findViewById(R.id.username);
        mPhone = view.findViewById(R.id.phone);
        mType = view.findViewById(R.id.type);
        outLogin = view.findViewById(R.id.OutLogin);
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            mUsername.setText(user.getUsername());
            mPhone.setText(user.getMobilePhoneNumber());
            int a = user.getFlag();
            if (a == 1) {
                mType.setText("管理员");
            } else if (a == 2) {
                mType.setText("老板");
            }
        } else {
            ItemManager manager = (ItemManager) getActivity();
            String type = manager.getType();
            if (type.equals("buyer")) {
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject("V9oyAAAE", new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        mUsername.setText(user.getUsername());
                        mPhone.setText(user.getMobilePhoneNumber());
                        mType.setText("采购部门");
                    }
                });
            } else {
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject("jFV9444m", new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        mUsername.setText(user.getUsername());
                        mPhone.setText(user.getMobilePhoneNumber());
                        mType.setText("销售部门");
                    }
                });
            }
        }
        outLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                getActivity().finish();
            }
        });

        return view;
    }
}
