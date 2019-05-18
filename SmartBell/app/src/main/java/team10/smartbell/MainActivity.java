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

        MenuFirebaseMessagingService.init(this);

        ListView listView = findViewById(R.id.activity_main_listview);

        adapter = new MainAdapter(this);
        listView.setAdapter(adapter);

        adapter.setListener(this::setListener);

        init();
    }

    private void init() {
        adapter.setItems(Menu.sample(this));
    }


    @SuppressWarnings("all")
    private void setListener(Menu menu) {
        ApiManager.shared().order(menu, (error, response) ->
            alert(response != null && response.body(), menu)
        );
    }


    private void alert(boolean result, Menu menu) {
        String message = getString(result ? R.string.alert_order_success : R.string.alert_order_failure, menu.getName());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
