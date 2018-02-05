package subbook.subbook;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* AddSub class:
 * activity for adding a new subscription to an existing or non-existing subscription list
 * */
public class AddSub extends AppCompatActivity {

    private EditText subName;
    private TextView subDate;
    private EditText subPrice;
    private EditText subComment;

    private Button subSubmit;
    private Subscription subscription;
    private boolean isEdited;
    private int pos;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        isEdited = false;
        subscription = new Subscription();
        subName = findViewById(R.id.editText);
        subDate = findViewById(R.id.editText3);
        subPrice = findViewById(R.id.editText4);
        subComment = findViewById(R.id.editText5);
        subSubmit = findViewById(R.id.confirm_button);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getBoolean("edit")) {
                this.isEdited = true;
                subscription = (Subscription) bundle.getSerializable("sub");
                pos = bundle.getInt("pos");
                subName.setText(subscription.getName());

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                subPrice.setText(df.format(subscription.getPrice()));
                subComment.setText(subscription.getComment());
                subDate.setText(subscription.getDate());
                date = subscription.getDate();
            }
        }
    }

    /* restrictions on length of input has been specified in relative xml
    * other restrictions are specified in Validate*/
    public boolean Validate(View view) {

        Date testDate = null;
        String dateTestString;
        dateTestString = subDate.getText().toString();

        // name is empty
        if (subName.getText().toString().equals("")) {
            notFilled("Name", view);
            return false;
        }
        // date is empty
        if (subDate.getText().toString().equals("")) {
            notFilled("Date", view);
            return false;
        }
        // price is empty
        if (subPrice.getText().toString().equals("")) {
            notFilled("Price", view);
            return false;
        }

        // validate date format
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            testDate = sdf.parse(dateTestString);
            if (!dateTestString.equals(sdf.format(testDate))) {
                testDate = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (testDate == null) {
            Snackbar snackbar = Snackbar.make(view, getString(R.string.date_warning), Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        else{

        }
        return true;
    }

    /*create a subscription if validate check is passed*/
    public void createSubscription(View view) {
        if (!Validate(view)) {
            return;
        }
        subscription.setName(subName.getText().toString());
        subscription.setDate(subDate.getText().toString());
        subscription.setPrice(Float.parseFloat(subPrice.getText().toString()));
        subscription.setComment(subComment.getText().toString());

        returnToMain();
    }

    /*check for empty field*/
    public void notFilled(String field, View view) {
        Snackbar snackbar = Snackbar.make(view, getString(R.string.add_sub_error,field), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /* back to main if edited, otherwise back to previous activity*/
    public void returnToMain() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newSub", this.subscription);
        if (isEdited) {
            resultIntent.putExtra("pos",this.pos);
            setResult(4, resultIntent);
        }
        else {
            setResult(0,resultIntent);
        }
        finish();
    }

    /* back to previous activity */
    public void cancel(View view) {
        Intent resultIntent = new Intent();
        setResult(1,resultIntent);
        finish();
    }

    public void cancel() {
        cancel(null);
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

}
