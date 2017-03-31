package com.bvrith.snapdirect.common;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.bvrith.snapdirect.FeedFragmentTab;
import com.bvrith.snapdirect.FriendsFragmentTab;
import com.bvrith.snapdirect.GalleryFragmentTab;
import com.bvrith.snapdirect.ImageDetailsActivity;
import com.bvrith.snapdirect.MainActivity;
import com.bvrith.snapdirect.db.FriendsDataSource;
import com.bvrith.snapdirect.db.ImageEntry;
import com.bvrith.snapdirect.db.ImagesDataSource;
import com.bvrith.snapdirect.gson.FeedEntryJson;
import com.bvrith.snapdirect.utils.DiskLruBitmapCache;

import java.io.IOException;

/**
 * Created by Alex on 2014-11-30.
 */
public class Snapdirect extends Application {
    private static String LOG_TAG = "Snapdirect";

    private static FriendsDataSource friendsDataSource;
    private static ImagesDataSource imagesDataSource;
    private static FriendsFragmentTab mFriendsTab;
    private static GalleryFragmentTab mGalleryTab;
    private static FeedFragmentTab mFeedTab;
    private static MainActivity mActivity;
    private static ImageDetailsActivity mImageDetailsActivity;
    private static Preferences mPreferences;
    private static DiskLruBitmapCache mAvatarDiskLruCache;
    private static DiskLruBitmapCache mImageDiskLruCache;
    private static Boolean isFirstStart = true;
    private static FeedEntryJson mImageDetails;
    private static ImageEntry mGalleryImageDetails;

    public static String extraImageSource = "gallery_image";
    public static String extraImageID = "imageID";

    @Override
    public void onCreate() {
        super.onCreate();

        friendsDataSource = new FriendsDataSource(this);
        friendsDataSource.open();
        imagesDataSource = new ImagesDataSource(this);
        imagesDataSource.open();
        mPreferences = new Preferences(this);
        mPreferences.loadPreferences();
        try {
            mAvatarDiskLruCache = new DiskLruBitmapCache(this, "AvatarsDiskCache",
                    2000000, Bitmap.CompressFormat.JPEG, 100);
            mImageDiskLruCache = new DiskLruBitmapCache(this, "ImageDiskCache",
                    20000000, Bitmap.CompressFormat.JPEG, 100);
        } catch (IOException e) {
            mAvatarDiskLruCache = null;
            mImageDiskLruCache = null;
            Log.d(LOG_TAG, "Failed to initialize disk cache");
        }

        Snapdirect.getImagesDataSource().dropPendingUploads();
    }

    public static Boolean isFirstStart(){
        Boolean res = isFirstStart;
        isFirstStart = false;
        return res;
    }

    public final static FriendsDataSource getFriendsDataSource(){
        return friendsDataSource;
    }

    public final static ImagesDataSource getImagesDataSource(){
        return imagesDataSource;
    }

    public final static void setMainActivity(MainActivity activity){
        mActivity = activity;
    }

    public final static MainActivity getMainActivity(){
        return mActivity;
    }

    public final static void setFriendsFragmentTab(FriendsFragmentTab tab){
        mFriendsTab = tab;
    }

    public final static FriendsFragmentTab getFriendsFragmentTab(){
        return mFriendsTab;
    }

    public final static void setFeedFragmentTab(FeedFragmentTab tab){
        mFeedTab = tab;
    }

    public final static FeedFragmentTab getFeedFragmentTab(){
        return mFeedTab;
    }

    public final static void setGalleryFragmentTab(GalleryFragmentTab tab){
        mGalleryTab = tab;
    }

    public final static GalleryFragmentTab getGalleryFragmentTab(){
        return mGalleryTab;
    }

    public final static Preferences getPreferences() {return mPreferences;}

    public final static DiskLruBitmapCache getAvatarDiskLruCache() {return mAvatarDiskLruCache;}

    public final static DiskLruBitmapCache getImageDiskLruCache() {return mImageDiskLruCache;}

    public static void setImageDetails(FeedEntryJson imageDetails)
        {mImageDetails = imageDetails;}

    public static void setImageDetailsActivity(ImageDetailsActivity activity)
    {
        mImageDetailsActivity = activity;}

    public static void setGalleryImageDetails(ImageEntry imageDetails)
    {
        mGalleryImageDetails = imageDetails;}

    public static FeedEntryJson getImageDetails()
    {return mImageDetails;}

    public static ImageDetailsActivity getImageDetailsActivity()
    {return mImageDetailsActivity;}

    public static ImageEntry getGalleryImageDetails()
    {return mGalleryImageDetails;}

}
