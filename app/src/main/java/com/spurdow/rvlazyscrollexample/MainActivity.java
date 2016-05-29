package com.spurdow.rvlazyscrollexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.spurdow.rvlazyscroll.RVLazyScroll;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LinearLayoutManager mLinearLayoutManager = null;
    public StaggeredGridLayoutManager mStaggeredGridLayoutManager = null;
    public GridLayoutManager mGridLayoutManager = null;
    public PersonAdapter mAdapter = null;
    public RecyclerView mRecyclerView;

    public RVLazyScroll<Person> linearLazyScroll ;
    public RVLazyScroll<Person> staggeredLazyScroll;
    public RVLazyScroll<Person> gridLazyScroll ;
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


        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL);

        mGridLayoutManager = new GridLayoutManager(this , 2 , LinearLayoutManager.VERTICAL , false);


        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        linearLazyScroll = new RVLazyScroll<Person>(mLinearLayoutManager , 2) {
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
        };

        staggeredLazyScroll = new RVLazyScroll<Person>(mStaggeredGridLayoutManager) {
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
        };

        gridLazyScroll = new RVLazyScroll<Person>(mGridLayoutManager) {
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
        };

        mRecyclerView.addOnScrollListener(linearLazyScroll);

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
        removeOnScrollListener();
       switch(item.getItemId()){
           case R.id.ic_action_grid:

                mRecyclerView.setLayoutManager(mGridLayoutManager);
                mRecyclerView.addOnScrollListener(gridLazyScroll);
               break;
           case R.id.ic_action_staggered_grid:

               mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
               mRecyclerView.addOnScrollListener(staggeredLazyScroll);
               break;
           case R.id.ic_action_linear:

               mRecyclerView.setLayoutManager(mLinearLayoutManager);
               mRecyclerView.addOnScrollListener(linearLazyScroll);
               break;
       }
        return super.onOptionsItemSelected(item);
    }

    private void removeOnScrollListener(){
        if(mRecyclerView.getLayoutManager() instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            if(linearLayoutManager instanceof GridLayoutManager){
                mRecyclerView.removeOnScrollListener(gridLazyScroll);
            }else {
                mRecyclerView.removeOnScrollListener(linearLazyScroll);
            }
        }else if(mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
            mRecyclerView.removeOnScrollListener(staggeredLazyScroll);
        }
    }
}
