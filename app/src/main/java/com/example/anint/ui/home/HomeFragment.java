package com.example.anint.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoyz.widget.PullRefreshLayout;
import com.example.anint.R;
import com.example.anint.Task.activity.Task;
import com.example.anint.Task.activity.TaskAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {


    private List<Task> taskList=new ArrayList<>();
    private void initTask(){
        Task i_001=new Task("完成一个APP","体育",R.drawable.ic_global_menu_feed,9999,"DDL在八月之前");
        taskList.add(i_001);
        Task i_002=new Task("每天听网课不睡觉","体育",R.drawable.ic_global_menu_news,50);
        taskList.add(i_002);
        Task i_003=new Task("测试","艺术",R.drawable.ic_global_menu_likes,666);
        taskList.add(i_003);
        Task i_004=new Task("测试2","学习",R.drawable.ic_global_menu_popular,52);
        taskList.add(i_004);
        Task i_005=new Task("测试3","专业",R.drawable.ic_global_menu_direct,33);
        taskList.add(i_005);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTask();
        TaskAdapter adapter = new TaskAdapter(getActivity(), R.layout.task_main, taskList);
        final ListView listView = (ListView) getActivity().findViewById(R.id.tasklist);


//        Toast.makeText(getActivity(), "目前可以", Toast.LENGTH_SHORT).show();
        listView.setAdapter(adapter);


        PullRefreshLayout layout = (PullRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);


        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

// refresh complete
        layout.setRefreshing(false);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Task task=taskList.get(position);
////                ImageButton bt=view.findViewById(R.id.Task_menu);
////                bt.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent=new Intent(getActivity(), TaskDetail.class);
//////
//                        Bundle bundle = new Bundle();
//                        bundle.putString("taskName", task.getTaskName());
//                        bundle.putString("taskInfo", task.getTaskInfo());
//                        bundle.putString("taskType", task.getTasktype());
//                        bundle.putString("taskMoney",Integer.toString(task.getmoney()));
////                bundle.putString("uid",user.getUid());
//                        intent.putExtras(bundle);
////
////                        getActivity().startActivity(intent);
////                    }
////                });
//
//            }
//        });
    }

}