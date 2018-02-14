package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CollectiveViewActivity extends ListActivity {

    public static CollectiveObj collective;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collective = new CollectiveObj();
        setContentView(R.layout.activity_collective_view_activity);
        setListAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount(){
            return CollectiveViewActivity.collective.getTransactionList().size();
        }

        @Override
        public TransactionObj getItem(int position){
            return CollectiveViewActivity.collective.getTransactionList().get(position);
        }

        @Override
        public long getItemId(int position){
            return CollectiveViewActivity.collective.getTransactionList().get(position).hashCode();
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
