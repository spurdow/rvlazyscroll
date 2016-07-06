package com.spurdow.rvlazyscrollexample;

// Static imports for assertion methods
import android.app.RobolectricActivityManager;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spurdow.rvlazyscroll.RVLazyScroll;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.Shadow;
import org.robolectric.shadows.ShadowObjectAnimator;

import java.util.Random;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
/**
 * Created by davidluvellejoseph on 3/16/16.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class EndlessTest {

    private MainActivity mainActivity;
    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RVLazyScroll<Person> lazyScroll;
    @Before
    public void setUp(){
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().postCreate(null).resume().get();
        mRecyclerView  = mainActivity.mRecyclerView;
        mAdapter = mainActivity.mAdapter;
        linearLayoutManager = mainActivity.mLinearLayoutManager;
        lazyScroll = mainActivity.linearLazyScroll;


    }

    @Test
    public void validateEndless(){

        int current_max_size = mAdapter.getItemCount();
        System.out.println(mAdapter.getItemCount() + " " + mRecyclerView.getChildCount() + " "  + linearLayoutManager.getChildCount() + " " + mRecyclerView.getScrollState());

        System.out.println(mAdapter.clone());
        lazyScroll.doLoadTest();

        mRecyclerView.measure(0 , 0);
        mRecyclerView.layout(0 , 0 , 100, 1000);

        System.out.println(mAdapter.getItemCount() + " " + mRecyclerView.getChildCount() + " "  + linearLayoutManager.getChildCount() + " " + mRecyclerView.getScrollState());
        assertEquals(current_max_size + 3 , mAdapter.getItemCount());



    }
}
