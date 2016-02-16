package com.spurdow.rvlazyscroll;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by davidluvellejoseph on 2/17/16.
 */
public abstract class RVLazyScroll<M> extends RecyclerView.OnScrollListener{
    private final LinearLayoutManager mLinearLayoutManager;
    private final AtomicBoolean isLoading = new AtomicBoolean(false);
    private LoadMoreAsync mLoadMoreAsync = new LoadMoreAsync();
    public RVLazyScroll(LinearLayoutManager linearLayoutManager) {
        super();
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        /**
         * Return nothing if it is still loading
         */
        if(isLoading.get()){
            return;
        }

        final int childrenSize = mLinearLayoutManager.getItemCount() - 1;

        if(canLoadMore()) {

            if(mLoadMoreAsync != null){
                if(mLoadMoreAsync.getStatus() == AsyncTask.Status.PENDING ||
                        mLoadMoreAsync.getStatus() == AsyncTask.Status.RUNNING){
                    mLoadMoreAsync.cancel(true);
                }
            }

            if(!isLoading.get()){
                mLoadMoreAsync = new LoadMoreAsync();
                mLoadMoreAsync.execute(childrenSize);
            }


        }else{
            isLoading.set(false);
        }


    }

    /**
     * Override this to your preferred way
     * @return true if visible last child is eq or gr than count size
     */
    public boolean canLoadMore(){
        final int lastChildPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        final int childrenSize = mLinearLayoutManager.getItemCount() - 1;

        return lastChildPosition >= childrenSize;
    }

    /**
     * On Load More
     * @param offset
     * @return the list of objects you want to add
     */
    public abstract List<M> onLoadMore(int offset);

    /**
     * On Done Load
     * @param newListOfdata
     */
    public abstract void onDoneLoad(List<M> newListOfdata);

    public boolean isLoading(){
        return isLoading.get();
    }

    public void stopLoading(){
        // stopping load
        if(mLoadMoreAsync != null && (mLoadMoreAsync.getStatus() == AsyncTask.Status.PENDING ||
                mLoadMoreAsync.getStatus() == AsyncTask.Status.RUNNING)){
            mLoadMoreAsync.cancel(true);
        }
        isLoading.set(false);
    }

    class LoadMoreAsync extends AsyncTask<Integer , Void , List<M>>{

        @Override
        protected List<M> doInBackground(Integer... offsets) {
            isLoading.set(true);
            return onLoadMore( offsets[0] + 1);
        }

        @Override
        protected void onPostExecute(List<M> list) {
            super.onPostExecute(list);
            isLoading.set(false);
            onDoneLoad(list);
        }

        @Override
        protected void onCancelled(List<M> ms) {
            isLoading.set(false);
        }


    }
}
