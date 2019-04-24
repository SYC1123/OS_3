package com.example.os_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static DCT DCT_head = new DCT("0", "0",null);
    static COCT COCT_head = new COCT("0", "0",null);
    static CHCT CHCT_head = new CHCT("0",null);
    static PCB_3 ready_head = new PCB_3("head", 0, "0");
    static PCB_3 block_head = new PCB_3("head", 0, "0");
    static PCB_3 run_head = new PCB_3("head", 0, "0");
    Switch DCT1, DCT2, DCT3, DCT4, DCT5;
    Switch COCT1, COCT2, COCT3;
    Switch CHCT1, CHCT2;
    EditText name, size, freeprocess;
    Spinner mSpinner;
    Button create, allocation, free, show;
    static TextView mTextView;
    String type;
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DCT1 = findViewById(R.id.DCT1);
        DCT2 = findViewById(R.id.DCT2);
        DCT3 = findViewById(R.id.DCT3);
        DCT4 = findViewById(R.id.DCT4);
        DCT5 = findViewById(R.id.DCT5);
        COCT1 = findViewById(R.id.COCT1);
        COCT2 = findViewById(R.id.COCT2);
        COCT3 = findViewById(R.id.COCT3);
        CHCT1 = findViewById(R.id.CHCT1);
        CHCT2 = findViewById(R.id.CHCT2);
        name = findViewById(R.id.name);
        size = findViewById(R.id.size);
        freeprocess = findViewById(R.id.freeProcess);
        mSpinner = findViewById(R.id.spinner);
        create = findViewById(R.id.create);
        allocation = findViewById(R.id.allocation);
        free = findViewById(R.id.free);
        show=findViewById(R.id.show);
        mTextView = findViewById(R.id.textView);
        init();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=name.getText().toString();
                int size1=Integer.parseInt(size.getText().toString());
                if (run_head.hasNext()) {
                    PCB_3 pcb = new PCB_3(name1,size1,type);
                    ready_head.addToTail(pcb);
                }
                if (!ready_head.hasNext()) {
                    PCB_3 pcb = new PCB_3(name1,size1,type);
                    run_head.setNext(pcb);
                }
            }
        });

        allocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
                allocation(run_head.getNext());
            }
        });

        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
                String name1=freeprocess.getText().toString();
                PCB_3 pcb_3 = block_head.findPCB(name1);// 查找进程\
                //Log.d("666",pcb_3.name);
                if(run_head.next==null){
                    run_head.setNext(pcb_3);
                }else{
                    ready_head.addToTail(pcb_3);
                }

                DCT tDct = DCT_head.findDCT(pcb_3);// 查找设备
                if (tDct.process == pcb_3) {
                    // 回收设备
                    freeDCT(tDct);
                    // 回收控制器
                    COCT coct = (COCT) tDct.parent;
                    int flag;
                    flag = freeCOCT(coct, pcb_3);
                    if (flag == 2) {
                        CHCT chct = (CHCT) coct.parent;
                        if (chct.process == pcb_3) {
                            if (chct.waitinglist.size() == 0) {
                                chct.process = null;
                                chct.mSwitch.setChecked(false);
                                chct.mSwitch.setText("0");
                            } else {// 为第 1 个等待进程分配控制器、通道
                                //if(chct.waitinglist.get(0)!=chct.process){
                                int a=DCT_head.find(chct.waitinglist.get(0));
                                if(a==1) {
                                    chct.process = chct.waitinglist.get(0);
                                    chct.mSwitch.setText(chct.process.name);
                                }else{
                                    chct.process = null;
                                    chct.mSwitch.setChecked(false);
                                    chct.mSwitch.setText("0");
                                }
                                chct.waitinglist.remove(0);
                            }
                        }
                      // mTextView.setText("回收结束");
                    }
                } else {
                    mTextView.setText("出错");
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
                mTextView.append("执行队列：\n");
                if (run_head.getNext() == null) {
                   mTextView.append("无执行进程！\n");
                } else {
                   mTextView.append("PCB [name=" + run_head.getNext().getName() + ",所占大小=" + run_head.getNext().getSize()
                            + "类型=" + run_head.getNext().getType() + "]\n");
                }
                mTextView.append("就绪队列：\n");
                if (ready_head.getNext() == null) {
                    mTextView.append("就绪队列为空！\n");
                } else {
                    ready_head.show(mTextView);
                }
                mTextView.append("阻塞队列：\n");
                if (block_head.getNext() == null) {
                   mTextView.append("阻塞队列为空！\n");
                } else {
                    block_head.show(mTextView);
                }
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=list.get(position);
                Log.d("123",type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void init() {
        DCT dct = new DCT("设备1", "I",DCT1);
        DCT dct2 = new DCT("设备2", "I",DCT2);
        DCT dct3 = new DCT("设备3", "O",DCT3);
        DCT dct4 = new DCT("设备4", "O",DCT4);
        DCT dct5 = new DCT("设备5", "R",DCT5);
        DCT_head.next = dct;
        dct.next = dct2;
        dct2.next = dct3;
        dct3.next = dct4;
        dct4.next = dct5;
        dct5.next = null;
        COCT coct = new COCT("控制器1", "I",COCT1);
        COCT coct2 = new COCT("控制器2", "O",COCT2);
        COCT coct3 = new COCT("控制器3", "R",COCT3);
        COCT_head.next = coct;
        coct.next = coct2;
        coct2.next = coct3;
        coct3.next = null;
        dct.parent = (coct);
        dct2.parent = (coct);
        dct3.parent = (coct2);
        dct4.parent = (coct2);
        dct5.parent = (coct3);
        CHCT chct = new CHCT("通道1",CHCT1);
        CHCT chct2 = new CHCT("通道2",CHCT2);
        coct.parent = (chct);
        coct2.parent = (chct);
        coct3.parent = (chct2);
        CHCT_head.next = chct;
        ready_head.setNext(null);
        block_head.setNext(null);
        run_head.setNext(null);
        list.add("I");
        list.add("O");
        list.add("R");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.item, R.id.text, list);
        mSpinner.setAdapter(adapter);
    }

    public static int freeCOCT(COCT coct, PCB pcb_3) {
        if (coct.process == pcb_3) {
            if (coct.waitinglist.size() == 0) {
                coct.process = null;
                coct.mSwitch.setChecked(false);
                coct.mSwitch.setText("0");
            } else {// 为第 1 个等待进程分配控制器、通道
                coct.process = coct.waitinglist.get(0);
                coct.mSwitch.setText(coct.process.name);
                coct.waitinglist.remove(0);
                coct.addCHCT(CHCT_head, run_head, ready_head, block_head,mTextView,0);
            }
            return 2;
        } else {
            mTextView.setText("回收结束");
            return 1;
        }
    }

    public static void freeDCT(DCT tDct) {
        if (tDct.waitinglist.size() == 0) {
            tDct.process = null;
            tDct.mSwitch.setText("0");
            tDct.mSwitch.setChecked(false);
            // 寻找控制器
            mTextView.append("寻找控制器" + tDct.parent.name+"\n");
        } else {// 为第 1 个等待进程分配设备、控制器、通道
            tDct.process = tDct.waitinglist.get(0);
            tDct.mSwitch.setText(tDct.process.name);
            Log.d("1234",tDct.process.name);
            tDct.waitinglist.remove(0);
            COCT coct = tDct.addCOCT(COCT_head, run_head, ready_head, block_head,mTextView,0);
            if (coct != null) {
                coct.addCHCT(CHCT_head, run_head, ready_head, block_head,mTextView,0);
            }
        }
    }

    //分配设备、控制器、通道
    public static void allocation(PCB_3 pcb_3) {
        DCT dct = pcb_3.addDCT(DCT_head, run_head, ready_head, block_head,mTextView,1);
        if (dct != null) {
            COCT coct = dct.addCOCT(COCT_head, run_head, ready_head, block_head,mTextView,1);
            if (coct != null) {
                coct.addCHCT(CHCT_head, run_head, ready_head, block_head,mTextView,1);
            }
        }
    }

}
