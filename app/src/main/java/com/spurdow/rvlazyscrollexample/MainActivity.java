package com.spurdow.rvlazyscrollexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.spurdow.rvlazyscroll.RVLazyScroll;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayoutManager mLinearLayoutManager = null;
    PersonAdapter mAdapter = null;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<Person> mock = new ArrayList<>();
        mock.add(new Person("Black Widow"));
        mock.add(new Person("Thor"));
        mock.add(new Person("Peter"));
        mock.add(new Person("John"));
        mock.add(new Person("Hulk"));
        mock.add(new Person("Captain America"));
        mock.add(new Person("Dead Pool"));
        mock.add(new Person("Hawk Eye"));

        mAdapter = new PersonAdapter(mock);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RVLazyScroll<Person>(mLinearLayoutManager) {
            @Override
            public List<Person> onLoadMore(int offset) {
                return new ArrayList<Person>(){{
                    add(new Person("Another Hulk"));
                    add(new Person("Another SpiderMan"));
                    add(new Person("Another Thor"));
                }};
            }

            @Override
            public void onDoneLoad(List<Person> newListOfdata) {
                if(newListOfdata != null && newListOfdata.size() >0) {
                    for (Person newP : newListOfdata) {
                        mAdapter.add(newP);
                    }
                }
            }
        });

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
