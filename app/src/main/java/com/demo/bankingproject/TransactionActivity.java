package com.demo.bankingproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    TextView tv_current_Amount;
    Button btn_add_money, btn_withdraw, btn_transaction_list;
    public static EditText et_date;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencyconvertor);

        this.sharedPreference = new SharedPreference();


        tv_current_Amount = findViewById(R.id.tv_current_Amount);
        btn_add_money = findViewById(R.id.btn_add_money);
        btn_withdraw = findViewById(R.id.btn_withdraw);
        btn_transaction_list = findViewById(R.id.btn_transaction_list);


        SharedPreferences settings = getSharedPreferences("prefs", 0);
        String walletamount = settings.getString("walletamount", "0");
        tv_current_Amount.setText(walletamount);

        btn_transaction_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TransactionActivity.this,TransactionListActivity.class);
                startActivity(intent);
            }
        });


        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(TransactionActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_withdrawmoney);

                Window window=(d).getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                window.setBackgroundDrawableResource(R.drawable.dialog_bg);


                final EditText et_money,et_description;
                Button btn_save;

                et_money = d.findViewById(R.id.et_money);
                et_date = d.findViewById(R.id.et_date);
                et_description=d.findViewById(R.id.et_description);
                btn_save=d.findViewById(R.id.btn_save);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validation())
                        {

                            int addmoney = Integer.parseInt(et_money.getText().toString());
                            int walletamount = Integer.parseInt(tv_current_Amount.getText().toString());
                            int amount=walletamount-addmoney;

                            if (amount<5)
                            {
                                Toast.makeText(TransactionActivity.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();



                                d.dismiss();
                            }
                            else {
                                SharedPreferences settings = getSharedPreferences("prefs", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("walletamount", String.valueOf(amount));
                                tv_current_Amount.setText(String.valueOf(amount));


                                BankDetails listItem=new BankDetails(addmoney+"",amount+"",et_date.getText().toString(),et_description.getText().toString(),"Amount Withdrawed");
                                sharedPreference.addTransactions(TransactionActivity.this, listItem);;
                                    Toast.makeText(TransactionActivity.this, "Amount Withdrawed Successfully", Toast.LENGTH_SHORT).show();



                                editor.apply();
                                d.dismiss();
                            }

                        }
                    }

                    private boolean validation() {

                        if (Integer.parseInt(tv_current_Amount.getText().toString())<5)
                        {
                            Toast.makeText(TransactionActivity.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (et_money.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Amount", Toast.LENGTH_SHORT).show();
                            return false;
                        }


                        if (et_money.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Amount", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (et_date.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Date", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (et_description.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Description", Toast.LENGTH_SHORT).show();
                            return false;
                        }



                        return true;
                    }
                });


                et_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showTruitonTimePickerDialog(view);
                        showTruitonDatePickerDialog(view);


                    }

                    private void showTruitonDatePickerDialog(View view) {

                        DialogFragment newFragment = new DatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }

                    private void showTruitonTimePickerDialog(View view) {
                        DialogFragment newFragment = new DatePickerFragment.TimePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "timePicker");

                    }
                });


                d.show();


            }
        });


        btn_add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(TransactionActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_addmoney);

                Window window=(d).getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                window.setBackgroundDrawableResource(R.drawable.dialog_bg);


                final EditText et_money,et_description;
                Button btn_save;

                et_money = d.findViewById(R.id.et_money);
                et_date = d.findViewById(R.id.et_date);
                et_description=d.findViewById(R.id.et_description);
                btn_save=d.findViewById(R.id.btn_save);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validation())
                        {

                            int addmoney = Integer.parseInt(et_money.getText().toString());
                            int walletamount = Integer.parseInt(tv_current_Amount.getText().toString());
                            int amount=addmoney+walletamount;

                            SharedPreferences settings = getSharedPreferences("prefs", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("walletamount", String.valueOf(amount));
                            tv_current_Amount.setText(String.valueOf(amount));

                            BankDetails listItem=new BankDetails(addmoney+"",amount+"",et_date.getText().toString(),et_description.getText().toString(),"Amount Added");
                            sharedPreference.addTransactions(TransactionActivity.this, listItem);;
                            Toast.makeText(TransactionActivity.this, "Amount Added Successfully", Toast.LENGTH_SHORT).show();




                            editor.apply();
                            d.dismiss();


                        }
                    }

                    private boolean validation() {

                        if (et_money.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Amount", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (et_date.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Date", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        if (et_description.getText().toString().equals(""))
                        {
                            Toast.makeText(TransactionActivity.this, "Please Enter Valid Description", Toast.LENGTH_SHORT).show();
                            return false;
                        }



                        return true;
                    }
                });


                et_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showTruitonTimePickerDialog(view);
                        showTruitonDatePickerDialog(view);


                    }

                    private void showTruitonDatePickerDialog(View view) {

                        DialogFragment newFragment = new DatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }

                    private void showTruitonTimePickerDialog(View view) {
                        DialogFragment newFragment = new DatePickerFragment.TimePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "timePicker");

                    }
                });


                d.show();


            }
        });
    }



    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog da = new DatePickerDialog(getActivity(), this,
                    year, month, day);
            Calendar c1 = Calendar.getInstance();

            c1.add(Calendar.DATE, 1);
            Date newDate = c1.getTime();
            da.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return da;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            et_date.setText(day + "/" + (month + 1) + "/" + year);
            et_date.setEnabled(true);

        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            et_date.setEnabled(true);

        }




        @SuppressLint("ValidFragment")
        public static class TimePickerFragment extends DialogFragment implements
                TimePickerDialog.OnTimeSetListener, TimePickerDialog.OnCancelListener {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                et_date.setText(et_date.getText() + " -" + hourOfDay + ":" + minute);
                et_date.setEnabled(true);
            }

            @Override
            public void onCancel(DialogInterface dialog) {
                super.onCancel(dialog);
                et_date.setEnabled(true);
            }
        }
    }
}
