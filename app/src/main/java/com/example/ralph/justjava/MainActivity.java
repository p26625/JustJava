package com.example.ralph.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    final int BASE_PRICE_PER_CUP = 5;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        createOrderSummary(calculatePrice());
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        int price = BASE_PRICE_PER_CUP;
        if (whippedCreamCheckBox.isChecked()) {
            price = price + 1;
        }
        if (chocolateCheckBox.isChecked()) {
            price = price + 2;
        }
        return price * quantity;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity != 0) quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method sends the order via email
     * @param address
     * @param subject
     * @param text
     */
    public void composeEmail(String address, String subject, String text) {
        String[] recipients = {address};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * This method displays the given price on the screen.
     */
    private void createOrderSummary(int price) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        String orderInfo = "Name: " + nameEditText.getText() + "\n"
                        + "Add whipped cream? " + (whippedCreamCheckBox.isChecked() ? "Yes" : "No") + "\n"
                        + "Add chocolate? " + (chocolateCheckBox.isChecked() ? "Yes" : "No") + "\n"
                        + "Quantity: " + quantity + "\n"
                        + "Total: " + NumberFormat.getCurrencyInstance().format(price) + "\n"
                        + "Thank You!";
        composeEmail("rkwhit4@cox.net", "Order: " + nameEditText.getText(), orderInfo);
        orderSummaryTextView.setText(orderInfo);
    }

}