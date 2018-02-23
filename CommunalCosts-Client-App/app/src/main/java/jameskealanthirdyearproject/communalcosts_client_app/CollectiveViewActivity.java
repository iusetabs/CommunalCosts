package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.app.Fragment.*;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CollectiveViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView collectiveTransactionView;
    private TransactionAdaptor adaptor;
    private FloatingActionButton addTransactionBtn;
    private Intent addTransaction;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    public TransactionObj transactionObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collective_view_activity);

        db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference();
        transactionObj = new TransactionObj();

        TransactionObj transaction1 = new TransactionObj("test transaction", -99, "Bank");
        final ArrayList<TransactionObj> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transactionObj);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionList.remove(transactionObj);
                Toast.makeText(CollectiveViewActivity.this,"Should be updated", Toast.LENGTH_SHORT).show();
                transactionObj.updateValues(dataSnapshot); //new methoded added in the object class
                transactionList.add(transactionObj);
                adaptor = new TransactionAdaptor(CollectiveViewActivity.this, transactionList);
                collectiveTransactionView.setAdapter(adaptor);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value.", databaseError.toException());

            }
        });

        collectiveTransactionView = (ListView) findViewById(R.id.collectiveListView);
        addTransactionBtn = (FloatingActionButton) findViewById(R.id.addTransaction);
        addTransactionBtn.setOnClickListener(this);
        addTransaction = new Intent(CollectiveViewActivity.this, AddTransaction.class);
        Intent i = getIntent(); //FIXME START
        Bundle extras = i.getExtras();
        if(extras == null){
            Log.d("CREATION", "not working");
        }
        String pos = extras.getString("EXTRA_CURRENT_COLLECTIVE_ID");
        Log.d("CREATION", pos);
        addTransaction.putExtra("EXTRA_CURRENT_COLLECTIVE_ID", getIntent().getStringExtra("EXTRA_CURRENT_COLLECTIVE_ID"));*/
        final Context context = this; //FIXME END

        /*collectiveTransactionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionObj transactionObj1 = transactionList.get(position);
//                Intent detailIntent = new Intent(context, TransactionDetailActivity.class);
                //TransactionDetailActivity.class will tell the app what to do once an activity has been clicked on
                *//*detailIntent.putExtra("payee", transactionObj1.getPayee());
                detailIntent.putExtra("description", transactionObj1.getDescription());
                startActivity(detailIntent);*//*
            }
        });*/
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
    public void onClick(View v){
        if(v == addTransactionBtn){
            finish();
            startActivity(addTransaction);
        }
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
            ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.recipe_list_thumbnail); // thumbnail element

            TransactionObj transaction = (TransactionObj) getItem(position);

            titleTextView.setText(transaction.getDescription());
            subtitleTextView.setText(transaction.getPayee());
            detailTextView.setText(String.format("%d", transaction.getValue()).trim());

            Picasso.with(mContext).load("http://developer.android.com/studio/images/studio-icon.png").placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
            return rowView;
        }

    }
}
