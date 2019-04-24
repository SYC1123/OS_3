package com.example.os_3;

import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

public class COCT extends IoNode {

	public COCT(String name, String type,Switch s) {
		super(name, type,s);
	}


	public void addNewCOCT(COCT head) {
		COCT coct = (COCT) head.next;
		while (coct.hasnext()) {
			if (coct.type.equals(this.type)) {
				this.parent = coct.parent;
				break;
			}
		}
	}

	public CHCT addCHCT(CHCT chct_head, PCB_3 run_head, PCB_3 ready_head, PCB_3 block_head, TextView textView,int flag) {
		if (this.parent.process == null) {
			this.parent.process = this.process;
			System.out.println("通道分配成功！" + this.parent.name);
			textView.append("\t通道分配成功\n");
			this.parent.mSwitch.setChecked(true);
			this.parent.mSwitch.setText(this.process.name);
			Log.d("555",run_head.getNext().name);
			if(flag==1) {
				block_head.addToTail(run_head.getNext());
				run_head.setNext(ready_head.deQueue());
			}
			return (CHCT) this.parent;

		} else {
			this.parent.waitinglist.add(this.process);
			if(flag==1) {
				block_head.addToTail(run_head.getNext());
				run_head.setNext(ready_head.deQueue());
			}
			textView.append("\t通道分配失败" + ",等待队列大小=" + this.parent.waitinglist.size()+"\n");
			System.out.println("通道分配失败  " + ",大小=" + this.parent.waitinglist.size());
			return null;

		}

	}

}
