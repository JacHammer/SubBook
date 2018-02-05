package subbook.subbook;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class SubscriptionList extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);

        Button addButton = (Button) findViewById(R.id.button);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SubscriptionList.this, AddSub.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
