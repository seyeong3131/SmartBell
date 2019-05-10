package team10.smartbell;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.activity_main_listview);

        adapter = new MainAdapter(this);
        listView.setAdapter(adapter);

        adapter.setListener(drink -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder .setTitle(R.string.app_name)
                    .setMessage(getString(R.string.alert_order, drink.getName()))
                    .setPositiveButton(R.string.ok, null);

            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        });

        init();
    }

    private void init() {
        adapter.setItems(Menu.sample(this));
    }
}
