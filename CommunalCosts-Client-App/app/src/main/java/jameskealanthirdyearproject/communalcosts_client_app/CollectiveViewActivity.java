package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.app.Fragment.*;


import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class CollectiveViewActivity extends AppCompatActivity {

    private ListView collectiveTransactionView;
    public static CollectiveObj collective;
    private TransactionAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransactionObj transaction1 = new TransactionObj("test transaction", -99, "Bank");
        ArrayList<TransactionObj> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        collectiveTransactionView = (ListView) findViewById(R.id.collectiveListView);
        adaptor = new TransactionAdaptor(this, transactionList);
        collectiveTransactionView.setAdapter(adaptor);

/*
        setEmptyText("Please choose a financial information input method");
        setHasOptionsMenu(true);
        collective = new CollectiveObj();
        setContentView(R.layout.activity_collective_view_activity);
        setListAdapter(new MyAdapter());
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.CONTACT_STATUS},
                );*/
    }

    private class TransactionAdaptor extends BaseAdapter{

        private Context mContext;
        private LayoutInflater mInflator;
        private ArrayList<TransactionObj> mDataSource;

        public TransactionAdaptor(Context context, ArrayList<TransactionObj> transactionList){
            mContext = context;
            mDataSource = transactionList;
            mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount(){
            return mDataSource.size();
        }

        @Override
        public TransactionObj getItem(int position){
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position){
            return mDataSource.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View rowView = mInflator.inflate(R.layout.list_item_recipe, parent, false);
            TextView titleTextView = (TextView) rowView.findViewById(R.id.recipe_list_title); // title element
            TextView subtitleTextView = (TextView) rowView.findViewById(R.id.recipe_list_subtitle); // subtitle element
            TextView detailTextView = (TextView) rowView.findViewById(R.id.recipe_list_detail);
//            ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.recipe_list_thumbnail); // thumbnail element

            TransactionObj transaction = (TransactionObj) getItem(position);

            titleTextView.setText(transaction.getDescription());
            subtitleTextView.setText(transaction.getPayee());
            detailTextView.setText(transaction.getValue());

            /*Picasso.with(mContext).load(image).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
            load image, would have to import picasso and add to maven and gradle*/
            /*if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).description + "  :" + getItem(position).getValue());
            return convertView;*/
            return rowView;
        }

    }
}
