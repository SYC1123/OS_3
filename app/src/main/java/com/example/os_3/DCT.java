package com.example.os_3;

import android.widget.Switch;
import android.widget.TextView;

public class DCT extends IoNode {

    public DCT(String name, String i, Switch s) {
        super(name, i, s);
    }

    public COCT addCOCT(COCT coct_head, PCB_3 run_head, PCB_3 ready_head, PCB_3 block_head, TextView textView, int flag) {
        if (this.parent.process == null) {
            this.parent.process = this.process;
            textView.append("\t控制器分配成功！\n");
            System.out.println("控制器分配成功   " + this.parent.name);
            this.parent.mSwitch.setChecked(true);
            this.parent.mSwitch.setText(this.parent.process.name);
            return (COCT) this.parent;
        } else {
            this.parent.waitinglist.add(this.process);
            if (flag == 1) {
                block_head.addToTail(run_head.getNext());
                run_head.setNext(ready_head.deQueue());
            }
            textView.append("\t控制器分配失败" + ",等待队列大小=" + this.parent.waitinglist.size() + "\n");
            System.out.println("控制器分配失败   " + ",大小=" + this.parent.waitinglist.size());
            return null;
        }

    }

    public void deleteDCT(String name) {
        DCT t = (DCT) this.next;
        DCT be = this;
        while (t.hasNext()) {
            if (t.name.equals(name)) {
                be.next = t.next;
            }
            t = (DCT) t.next;
            be = (DCT) be.next;
        }
    }

    public int find(PCB_3 name) {
        DCT dct = (DCT) this.next;
        while (dct.hasNext()) {
            if (dct.process==name) {
                return 1;
            }
            dct = (DCT) dct.next;
            if (dct.next == null) {
                if (dct.process==name) {
                    return 1;
                }
            }
        }
        return 2;
    }

    public DCT findDCT(PCB_3 name) {
        DCT t = (DCT) this.next;
        while (t.hasnext()) {
            if (t.process == name) {
                return t;
            }
            t = (DCT) t.next;
            if (t.next == null) {
                if (t.process == name) {
                    return t;
                }
            }
        }
        return null;
    }

    public DCT findMinWaitingList(String type) {
        DCT t = (DCT) this.next;
        int min = 1000000;
        DCT m = t;
        while (t.hasnext()) {
            if (t.waitinglist.size() <= min && t.type.equals(type)) {
                min = t.waitinglist.size();
                m = t;
            }
            t = (DCT) t.next;
        }
        return m;
    }

}
