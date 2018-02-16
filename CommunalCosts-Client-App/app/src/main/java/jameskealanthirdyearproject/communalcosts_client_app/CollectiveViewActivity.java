/*
package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.app.Fragment.*;

public class CollectiveViewActivity extends ListActivity implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static CollectiveObj collective;
    SimpleCursorAdapter mAdapter;
    String mCurrentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEmptyText("Please choose a financial information input method");
        setHasOptionsMenu(true);
        collective = new CollectiveObj();
        setContentView(R.layout.activity_collective_view_activity);
        setListAdapter(new MyAdapter());
        */
/*mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.CONTACT_STATUS},
                );*//*

    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount(){
            return CollectiveViewActivity.collective.getTransactions().size();
        }

        @Override
        public TransactionObj getItem(int position){
            return CollectiveViewActivity.collective.getTransactions().get(position);
        }

        @Override
        public long getItemId(int position){
            return CollectiveViewActivity.collective.getTransactions().get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container){
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).description + "  :" + getItem(position).getValue());
            return convertView;
        }

    }
}
*/
