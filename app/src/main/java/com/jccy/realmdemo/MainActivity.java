package com.jccy.realmdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jccy.realmdemo.bean.Student;
import com.jccy.realmdemo.eventbus.EventBusManager;
import com.jccy.realmdemo.realm.RealmUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RealmUtils realmUtils;
    private RecyclerView recyclerView;
    private List<Student> students;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusManager.getEventBus().register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realmUtils = RealmUtils.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setName("HYY No." + students.size());
                realmUtils.insertData(student);
                long time = System.currentTimeMillis();
                students = realmUtils.queryAllStudent();
                mAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        students = realmUtils.queryAllStudent();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, null));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(students.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return students.size();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDeleteAllDataSuccessEvent(EventBusManager.OnDeleteAllDataSuccessEvent onDeleteAllDataSuccessEvent){
        students = realmUtils.queryAllStudent();
        mAdapter.notifyDataSetChanged();
    }


    private class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            realmUtils.deleteAllData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusManager.getEventBus().unregister(this);
    }
}
