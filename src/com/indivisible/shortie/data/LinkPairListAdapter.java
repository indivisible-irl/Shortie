package com.indivisible.shortie.data;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.indivisible.shortie.R;


public class LinkPairListAdapter
        extends ArrayAdapter<LinkPair>
{

    private Context context;
    private List<LinkPair> linkPairs;

    private static final String TAG = "sho:LinkPairAdapter";

    public LinkPairListAdapter(Context context, List<LinkPair> linkPairList)
    {
        super(context, R.layout.row_linkpair, linkPairList);
        this.context = context;
        this.linkPairs = linkPairList;
        Log.d(TAG, "OnInit: Number of LinkPairs: " + linkPairs.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_linkpair, parent, false);
        TextView tvLongUrl = (TextView) rowView.findViewById(R.id.tvLinkLongUrl);
        TextView tvShortUrl = (TextView) rowView.findViewById(R.id.tvLinkShortUrl);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvLinkDate);

        LinkPair linkPair = linkPairs.get(position);
        //        if (linkPair == null)
        //        {
        //            Log.d(TAG, "linkPair null, pos: " + position);
        //        }
        //        Log.v(TAG, "id:     " + linkPair.getId());
        //        Log.v(TAG, "create: " + linkPair.getCreatedMillis());
        //        Log.v(TAG, "status: " + linkPair.getStatus().name());
        //        Log.v(TAG, "long:   " + linkPair.getLongUrl());
        //        Log.v(TAG, "short:  " + linkPair.getShortUrl());
        tvLongUrl.setText(linkPair.getLongUrl());
        //FIXME: revert back to showing short url after testing
        tvShortUrl.setText(linkPair.getId() + ":" + linkPair.getStatusOrShortUrl());
        tvDate.setText(linkPair.getPrintableDate());
        return rowView;
    }

}
