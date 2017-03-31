package com.bvrith.snapdirect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bvrith.snapdirect.gson.CommentEntryJson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Alex on 2015-02-15.
 */
public class CommentListAdapter extends ArrayAdapter<CommentEntryJson> {
    private static String LOG_TAG = "CommentListAdapter";

    int resource;
    String response;
    Context context;
    ImageLoader mAvatarLoader;
    public ArrayList<CommentEntryJson> mCommentList;
    private Activity mContext;

    //Initialize adapter
    public CommentListAdapter(Context context, int resource, ArrayList<CommentEntryJson> items,
                              ImageLoader avatarLoader) {
        super(context, resource, items);
        this.resource=resource;
        this.mAvatarLoader = avatarLoader;
        this.mCommentList = items;
        this.mContext = (Activity)context;
    }

    static class ViewHolder {
        CircleImageView imgAvatar;
        TextView authorNameText;
        TextView commentText;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent)
    {
        LinearLayout view;
        CommentEntryJson comment = getItem(position);
        View rowView = convertView;
        ViewHolder holder = null;

        if(rowView == null) {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            rowView = vi.inflate(R.layout.item_comment, null);
            holder = new ViewHolder();
            holder.authorNameText = (TextView)rowView.findViewById(R.id.textAuthorNameComment);
            holder.commentText = (TextView)rowView.findViewById(R.id.textCommentComment);
            holder.imgAvatar = (CircleImageView)rowView.findViewById(R.id.imgAvatarComment);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        final String commentAuthorId = comment.author_id;

        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserProfileActivity.class);
                intent.putExtra("userId", commentAuthorId);
                mContext.startActivity(intent);
            }
        });

        holder.imgAvatar.setImageResource(R.drawable.avatar);
        if ((comment.author != null) && (comment.author.name != null))
            holder.authorNameText.setText(comment.author.name);
        if (comment.text != null)
            holder.commentText.setText(comment.text);
        holder.imgAvatar.setTag(position);

        if ((comment.author != null) && (comment.author.avatar != null)
                && (comment.author.avatar.isEmpty() == false)
                && (URLUtil.isValidUrl(comment.author.avatar))) {
            Log.d(LOG_TAG, "Requesting avatar for " + comment.author.avatar);
            mAvatarLoader.get(comment.author.avatar.toString(),
                    new ImageListener(position, holder.imgAvatar));
        }



        return rowView;
    }

    private class ImageListener implements ImageLoader.ImageListener {
        Integer pos = 0;
        CircleImageView imgAvatar;

        public ImageListener(Integer position, CircleImageView imgAvatar){
            this.pos = position;
            this.imgAvatar = imgAvatar;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
            if ((response.getBitmap() != null) && ((Integer) imgAvatar.getTag() == pos)) {
                imgAvatar.setImageResource(0);
                imgAvatar.setImageBitmap(response.getBitmap());
            }
        }
    }
}
