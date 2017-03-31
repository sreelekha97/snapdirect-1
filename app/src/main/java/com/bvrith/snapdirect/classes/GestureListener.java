package com.bvrith.snapdirect.classes;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.etsy.android.grid.StaggeredGridView;
import com.bvrith.snapdirect.common.Constants;

/**
 * Created by Alex on 2015-03-23.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener
        implements View.OnTouchListener
{
    private static String LOG_TAG = "GestureListener";

    Context context;
    GestureDetector mGestureDetector;
    AbsListView mAbsListView;
    BookmarkHandler bookmarkHandler;

    private Boolean boolPullingDown = false;
    private Boolean boolPullingUp = false;

    private float startY = 0;
    private float startX = 0;
    private int flingLen = 0;

    public GestureListener(Context context, AbsListView absListView,
            BookmarkHandler bookmarkHandler) {
        this.context = context;
        this.mAbsListView = absListView;
        this.mGestureDetector = new GestureDetector(context, this);
        this.bookmarkHandler = bookmarkHandler;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return super.onSingleTapConfirmed(e);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getRawX();
            startY = event.getRawY();
            flingLen = 0;
        } else if ((event.getAction() == MotionEvent.ACTION_MOVE) ) {
            float dY = event.getRawY() - startY;
            if (checkTopPosition() && (dY > 0) && (!bookmarkHandler.boolOpen)) {
                bookmarkHandler.setHeight(Math.min((int) dY / 2, Constants.BOOKMARKS_HEIGHT));
                updateFlingLen(event.getRawX(), event.getRawY());
                return true;
            } else if (checkTopPosition() && (dY < 0) && bookmarkHandler.boolOpen) {
                bookmarkHandler.setHeight(Math.max(Constants.BOOKMARKS_HEIGHT
                        + (int) dY / 2, 0));
                updateFlingLen(event.getRawX(), event.getRawY());
                return true;
            }
        } else if ((event.getAction() == MotionEvent.ACTION_UP) ) {
            if ((bookmarkHandler.getHeight() < Constants.BOOKMARKS_HEIGHT / 2)) {
                bookmarkHandler.close();
                if (flingLen >= Constants.BOOKMARKS_HEIGHT) return true;
            } else if ((bookmarkHandler.getHeight() >= Constants.BOOKMARKS_HEIGHT / 2)) {
                bookmarkHandler.open();
                if (flingLen >= Constants.BOOKMARKS_HEIGHT) return true;
            }
        }
        return mGestureDetector.onTouchEvent(event);
    }

    private void updateFlingLen (double x, double y) {
        int dist = (int) Math.sqrt((x - startX) * (x - startX) + (y - startY) * (y - startY));
        flingLen = Math.max(dist, flingLen);
    }

    private Boolean checkTopPosition (){
        if (mAbsListView instanceof StaggeredGridView)
            return ((((StaggeredGridView) mAbsListView).getDistanceToTop() == 0)
                    && (mAbsListView.getFirstVisiblePosition() == 0));
        else
            return ((mAbsListView.getScrollY() == 0)
                    && (mAbsListView.getFirstVisiblePosition() == 0));
    }

    private void setPullDirection (Boolean boolDown) {
        boolPullingDown = boolDown;
        boolPullingUp = !boolDown;
    }
}
