package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CollectiveViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView collectiveTransactionView;
    private TransactionAdaptor adaptor;
    private FloatingActionButton addTransactionBtn;
    private Intent addTransaction, testIntent;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private TransactionObj transactionObj;
    private String collectiveid;
    public ArrayList<TransactionObj> transactionList;
    private final String TAG = CollectiveViewActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_collective_view_activity);
        collectiveid = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID");
        setTitle(collectiveid);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference();
        transactionObj = new TransactionObj();

        transactionList = new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // FIXME: 26/02/18 not refreshing after adding transaction
                CollectiveObj collectiveObj = dataSnapshot.child("collectives/" + collectiveid).getValue(CollectiveObj.class);
                try {
                    for (TransactionObj transactionObj : collectiveObj.getTransactions()) {
                        transactionList.add(transactionObj);
                    }
                } catch (NullPointerException e) {
                }
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
        addTransaction.putExtra("CURRENT_COLLECTIVE_ID", collectiveid);
        collectiveTransactionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionObj selectedTransaction = transactionList.get(position);
                Intent detailIntent = new Intent(CollectiveViewActivity.this, TransactionView.class);
                detailIntent.putExtra("CURRENT_COLLECTIVE_ID", collectiveid);
                Bundle args = new Bundle();
                args.putParcelable("THIS_TRANSACTION", selectedTransaction);
                detailIntent.putExtra("BUNDLE",args);
                detailIntent.putExtra("ARRAY_POSITION", position);
                startActivity(detailIntent);
            }
        });
    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void onClick(View v) {
        if (v == addTransactionBtn) {
            finish();
            startActivity(addTransaction);
        }
    }

    private class TransactionAdaptor extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflator;
        private ArrayList<TransactionObj> mDataSource;

        public TransactionAdaptor(Context context, ArrayList<TransactionObj> transactionList) {
            mContext = context;
            mDataSource = transactionList;
            mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public TransactionObj getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mDataSource.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mInflator.inflate(R.layout.list_item_recipe, parent, false);
            TextView titleTextView = (TextView) rowView.findViewById(R.id.recipe_list_title); // title element
            TextView subtitleTextView = (TextView) rowView.findViewById(R.id.recipe_list_subtitle); // subtitle element
            TextView detailTextView = (TextView) rowView.findViewById(R.id.recipe_list_detail);
            ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.recipe_list_thumbnail); // thumbnail element

            TransactionObj transaction = getItem(position);

            titleTextView.setText(transaction.getTitle());
            subtitleTextView.setText(transaction.getPayee());
            detailTextView.setText(String.format("%d", transaction.getValueOfT()).trim());

            Picasso.with(mContext).load("http://developer.android.com/studio/images/studio-icon.png").placeholder(R.drawable.trans_icon).into(thumbnailImageView);
            return rowView;
        }

    }
}
