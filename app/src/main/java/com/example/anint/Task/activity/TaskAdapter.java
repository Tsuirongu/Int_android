package com.example.anint.Task.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anint.MainActivity;
import com.example.anint.R;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {


    private List<Task> obj;
    private int resouseId;
    public TaskAdapter(Context context, int textviewresourceid, List<Task> obj){
        super(context,textviewresourceid,obj);
        resouseId=textviewresourceid;
        this.obj=obj;
    }

    @Override
    public int getCount() {
        return obj.size();
    }

    @Override
    public Task getItem(int i) {
        return obj.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View converView, ViewGroup parent){
        Task task=getItem(position);
        View view;
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        ViewHolder viewHolder;
        if(converView==null){
            view = LayoutInflater.from(getContext()).inflate(resouseId, parent, false);

            viewHolder=new ViewHolder();
            viewHolder.Like=(ShineButton) view.findViewById(R.id.po_image1);
            viewHolder.mark = (ShineButton) view.findViewById(R.id.po_image);
            viewHolder.taskImg=(ImageView) view.findViewById(R.id.taskImage);
            viewHolder.taskName=(TextView)view.findViewById(R.id.taskName);
            viewHolder.UserImg=(ImageView)view.findViewById(R.id.userImage) ;
            viewHolder.getTask=(Button)view.findViewById(R.id.getTask);
            viewHolder.money=(TextView)view.findViewById(R.id.money);
            viewHolder.taskIntro=(TextView)view.findViewById(R.id.taskIntro);


            view.setTag(viewHolder);
        }else {
            view=converView;
            viewHolder=(ViewHolder)view.getTag();
        }
//        viewHolder.detail.setOnClickListener(v -> {
//            Intent intent=new Intent(getContext(),TaskDetail.class);
//
//            Bundle bundle = new Bundle();
//            bundle.putString("taskName", task.getTaskName());
//            bundle.putString("taskInfo", task.getTaskInfo());
//            bundle.putString("taskType", task.getTasktype());
//            bundle.putString("taskMoney",Integer.toString(task.getmoney()));
////                bundle.putString("uid",user.getUid());
//            intent.putExtras(bundle);
//            getContext().startActivity(intent);
//        });
//
        viewHolder.taskName.setText(task.getTaskName());
        viewHolder.taskIntro.setText(task.getTaskInfo());
//        viewHolder.taskImg.setImageResource(task.getImageId());
//        viewHolder.UserImg.setImageResource(R.drawable.ic_activity_active);
        viewHolder.money.setText(Integer.toString(task.getmoney()));
        viewHolder.getTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"任务接受成功",Toast.LENGTH_SHORT).show();
            }
        });
//        viewHolder.taskType.setText(task.getTaskName());

        return view;


    }

//    private class MyListener implements View.OnClickListener {
//        int mPosition;
//        public MyListener(int inPosition){
//            mPosition= inPosition;
//        }
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            Toast.makeText(ListViewActivity.this, title[mPosition], Toast.LENGTH_SHORT).show();
//        }
//
//    }



    class ViewHolder{
        ImageView UserImg;
        TextView taskName;
        ImageButton detail;
        TextView money;
        ImageView taskImg;
        Button getTask;
        TextView taskIntro;

        //待完成
        ShineButton Like;
        ShineButton mark;
    }

}
