package com.bvrith.snapdirect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Alex on 2014-11-27.
 */
public class FriendsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,SQLiteHelper.COLUMN_NAME,
            SQLiteHelper.COLUMN_CONTACT_KEY, SQLiteHelper.COLUMN_USER_ID,
            SQLiteHelper.COLUMN_AVATAR, SQLiteHelper.COLUMN_STATUS};
    int idColIndex;
    int nameColIndex;
    int userIdColIndex;
    int avatarColIndex;
    int statusColIndex;
    int typeColIndex;
    int ContactKeyColIndex;

    public FriendsDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS, null, null, null, null, null, null);
        idColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_ID);
        nameColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME);
        userIdColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_USER_ID);
        avatarColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_AVATAR);
        statusColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_STATUS);
        typeColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_TYPE);
        ContactKeyColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_CONTACT_KEY);
        cursor.close();
    }

    public void close() {
        dbHelper.close();
    }

    public FriendEntry createFriend(String Name, String ContactKey, String UserId,
                String Avatar, int Status) {
        //Add new FriendEntry
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CONTACT_KEY,ContactKey);
        cv.put(dbHelper.COLUMN_NAME,Name);
        cv.put(dbHelper.COLUMN_USER_ID,UserId);
        cv.put(dbHelper.COLUMN_AVATAR,Avatar);
        cv.put(dbHelper.COLUMN_STATUS,Status);
        cv.put(dbHelper.COLUMN_TYPE,FriendEntry.INT_TYPE_PERSON);

        database.insert(dbHelper.TABLE_FRIENDS, null, cv);
        return null;
    }

    public FriendEntry createChannel(String Name, String ContactKey, String UserId,
                                    String Avatar, int Status) {
        //Add new FriendEntry
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CONTACT_KEY,ContactKey);
        cv.put(dbHelper.COLUMN_NAME,Name);
        cv.put(dbHelper.COLUMN_USER_ID,UserId);
        cv.put(dbHelper.COLUMN_AVATAR,Avatar);
        cv.put(dbHelper.COLUMN_STATUS,Status);
        cv.put(dbHelper.COLUMN_TYPE,FriendEntry.INT_TYPE_CHANNEL);

        database.insert(dbHelper.TABLE_FRIENDS, null, cv);
        return null;
    }

    public void deleteFriendEntry(long id) {
        database.delete(dbHelper.TABLE_FRIENDS,"_id = ?",new String[] {String.valueOf(id)});
    }

    private FriendEntry getFriendByColumnValue(String column, String value) {
        String selection = column + " = ? AND " + SQLiteHelper.COLUMN_TYPE + " = ?";

        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS,
                null, selection, new String[]{value,
                String.valueOf(FriendEntry.INT_TYPE_PERSON) } , null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        FriendEntry friend  = cursorToFriendEntry(cursor);
        cursor.close();
        return friend;
    }

    private FriendEntry getChannelByColumnValue(String column, String value) {
        String selection = column + " = ? AND " + SQLiteHelper.COLUMN_TYPE + " = ?";

        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS,
                null, selection, new String[]{value,
                        String.valueOf(FriendEntry.INT_TYPE_CHANNEL) } , null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        FriendEntry friend  = cursorToFriendEntry(cursor);
        cursor.close();
        return friend;
    }

    private FriendEntry getContactByColumnValue(String column, String value) {
        String selection = column + " = ?";

        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS,
                null, selection, new String[]{value} , null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        FriendEntry friend  = cursorToFriendEntry(cursor);
        cursor.close();
        return friend;
    }

    public FriendEntry getFriendByUserId(String userId) {
        return getFriendByColumnValue(dbHelper.COLUMN_USER_ID, userId);
    }

    public FriendEntry getChannelByChannelId(String channelId) {
        return getChannelByColumnValue(dbHelper.COLUMN_USER_ID, channelId);
    }

    public FriendEntry getContactByUserId(String userId) {
        return getContactByColumnValue(dbHelper.COLUMN_USER_ID, userId);
    }

    public FriendEntry getFriendByContactKey(String contactKey) {
        return getFriendByColumnValue(dbHelper.COLUMN_CONTACT_KEY, contactKey);
    }

    public ArrayList<FriendEntry> getFriendsByStatus(int StatusSet[]) {
        String selection = dbHelper.COLUMN_STATUS + " IN (?";
        String values[] = new String[StatusSet.length];
        values[0] = String.valueOf(StatusSet[0]);
        for (int i = 1; i < StatusSet.length; i++) {
            selection = selection + ",?";
            values[i] = String.valueOf(StatusSet[i]);
        }
        selection = selection + ") ";

        selection = selection + " AND "
                + SQLiteHelper.COLUMN_TYPE + " = " + FriendEntry.INT_TYPE_PERSON;

        String orderBy =  SQLiteHelper.COLUMN_NAME + " ASC";

        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS,
                null, selection, values, null, null, orderBy);

        ArrayList<FriendEntry> listFriends = new ArrayList<FriendEntry>();

        if (cursor == null) {
            return listFriends;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return listFriends;
        }

        do{
            listFriends.add(cursorToFriendEntry(cursor));
        }while (cursor.moveToNext());

        cursor.close();
        return listFriends;
    }

    public ArrayList<FriendEntry> getAllFriends() {
        String selection = SQLiteHelper.COLUMN_TYPE + " = " + FriendEntry.INT_TYPE_PERSON;

        String orderBy =  SQLiteHelper.COLUMN_NAME + " ASC";

        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS, null, selection,
                null , null, null, orderBy);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<FriendEntry> listFriends = new ArrayList<FriendEntry>();

        do{
            listFriends.add(cursorToFriendEntry(cursor));
        }while (cursor.moveToNext());

        cursor.close();
        return listFriends;
    }

    public ArrayList<FriendEntry> getAllChannels() {
        String selection = SQLiteHelper.COLUMN_TYPE + " = " + FriendEntry.INT_TYPE_CHANNEL;

        String orderBy =  SQLiteHelper.COLUMN_NAME + " ASC";

        Cursor cursor = database.query(dbHelper.TABLE_FRIENDS, null, selection,
                null , null, null, orderBy);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<FriendEntry> listFriends = new ArrayList<FriendEntry>();

        do{
            listFriends.add(cursorToFriendEntry(cursor));
        }while (cursor.moveToNext());

        cursor.close();
        return listFriends;
    }

    public int setUserId(String ContactKey, String UserId) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_USER_ID,UserId);

        int updCount = database.update(dbHelper.TABLE_FRIENDS, cv, "_id = ?",new String[] {ContactKey});
        //Update UserId using ContactKey
        return 0;
    }

    public int updateFriend(FriendEntry friend) {
        //Update existing friend using _ID

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CONTACT_KEY,friend.getContactKey());
        cv.put(dbHelper.COLUMN_NAME,friend.getName());
        cv.put(dbHelper.COLUMN_USER_ID,friend.getUserId());
        cv.put(dbHelper.COLUMN_AVATAR,friend.getAvatar());
        cv.put(dbHelper.COLUMN_STATUS,friend.getStatus());
        cv.put(dbHelper.COLUMN_TYPE,friend.getType());
        // обновляем по id
        int updCount = database.update(dbHelper.TABLE_FRIENDS, cv, "_id = ?",new String[] { String.valueOf(friend.getId())});
        //Log.d(Constants.LOG_TAG, "Updated friend with number "+friend.getContactKey());
        return updCount;
    }

    private FriendEntry cursorToFriendEntry(Cursor cursor) {

        //Log.d(Constants.LOG_TAG,""+cursor.getString(ContactKeyColIndex));
        FriendEntry friend = new FriendEntry();
        friend.setId(cursor.getInt(idColIndex));
        friend.setName(cursor.getString(nameColIndex));
        friend.setUserId(cursor.getString(userIdColIndex));
        friend.setAvatar(cursor.getString(avatarColIndex));
        friend.setStatus(cursor.getInt(statusColIndex));
        friend.setType(cursor.getInt(typeColIndex));
        friend.setContactKey(cursor.getString(ContactKeyColIndex));

        return friend;
    }
}
