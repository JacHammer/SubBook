package subbook.subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/* EditSub class:
 * activity for viewing, editing, and deleting a subscription from a list of subscription
 * */
public class EditSub extends AppCompatActivity {

    private Subscription subscription;
    private int position;
    private TextView name;
    private TextView price;
    private TextView date;
    private TextView comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);
        name = findViewById(R.id.editText);
        date = findViewById(R.id.editText3);
        price = findViewById(R.id.editText4);
        comment = findViewById(R.id.editText5);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subscription = (Subscription) bundle.getSerializable("sub");
            position = bundle.getInt("pos");
        }
        setTextViews();
    }

    /* set text views for subscriptions*/
    private void setTextViews() {

        this.name.setText(subscription.getName());
        this.date.setText(subscription.getDate());
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.price.setText(df.format(subscription.getPrice()));

        /* set hint for comment field*/
        if (subscription.getComment().equals("")) {
            comment.setText(R.string.comment_hint);
        }
        else {
            comment.setText(subscription.getComment());
        }
    }


    /* return to SubscriptionList to delete a subscription from view*/
    public void deleteSub(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("pos",position);
        setResult(3, resultIntent);
        finish();
    }

    /* return to AddSub to edit a subscription from view*/
    public void editSub(View view) {
        Intent newIntent = new Intent(EditSub.this, AddSub.class);
        newIntent.putExtra("edit",true);
        newIntent.putExtra("pos", position);
        newIntent.putExtra("sub", this.subscription);
        startActivityForResult(newIntent, 0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(resultCode,data);
        if (resultCode == 4)
            finish();
    }

    /*return to previous activity if back button is pressed*/
    @Override
    public void onBackPressed() {
        cancel();
    }

    public void cancel(View view) {
        Intent resultIntent = new Intent();
        setResult(1,resultIntent);
        finish();
    }
    public void cancel() {
        cancel(null);
    }

}
