package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<Equipment> equipments = new ArrayList<Equipment>();
    public ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Object[] equipmentArray = new Object[]{
//                new Equipment("vssdv1", "sdfb1", "fghfg1", true),
//                new Equipment("vssdv2", "sdfb2", "fghfg2", false),
//                new Equipment("vssdv3", "sdfb3", "fghfg3", false)
//        };
//
//        for(int i = 0; i < equipmentArray.length; i++){
//            equipments.add((Equipment) equipmentArray[i]);
//        }
        readFile();
        mainListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<Equipment> adapter = new EquipmentAdapter(this);
        mainListView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
//        RadioButton rb1 = findViewById(R.id.radioButton);
//        RadioButton rb2 = findViewById(R.id.radioButton2);
//
//        View.OnClickListener clicker = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean checked = ((RadioButton) v).isChecked();
//                TextView tv = (TextView) findViewById(R.id.textView10);
//                readFile();
//                switch(v.getId()) {
//                    case R.id.radioButton:tv.setText("1111");
//                        if (checked){
//                            //tv.setText("3333");
//
//                            ArrayList<Equipment> list = new ArrayList<Equipment>();
////                            for(Equipment equipmentItem : equipments){
//////                                if(equipmentItem.issued == true){
////////                                    //equipments.remove(equipmentItem);
////////                                    list.add(equipmentItem);
////                                    tv.setText("3333");
//////                                }
////                            }
//                            for (int i = 0; i < equipments.size(); i++){
//                                Equipment temp = equipments.get(i);
//                                //Boolean b = temp.issued;
//                                Toast.makeText(MainActivity.this, String.valueOf(temp.issued), Toast.LENGTH_SHORT).show();
////                                if(equipments.get(i).issued){
//                                    list.add(temp);
////                                }
//                            }
////                            equipments = list;
////                            mainListView = (ListView) findViewById(R.id.listView);
////                            ArrayAdapter<Equipment> adapter = new EquipmentAdapter(MainActivity.this);
////                            mainListView.setAdapter(adapter);
//                        }
//                        break;
////                    case R.id.radioButton2:
////                        if (checked){
////                            //tv.setText("4444");
////                            tv.setText("2222");
////                            ArrayList<Equipment> list = new ArrayList<Equipment>();
////                            for(Equipment equipmentItem : equipments){
////                                if(equipmentItem.issued == false){
////                                    //equipments.remove(equipmentItem);
////                                    list.add(equipmentItem);
////                                }
////                            }
//////                            equipments = list;
//////                            mainListView = (ListView) findViewById(R.id.listView);
//////                            ArrayAdapter<Equipment> adapter = new EquipmentAdapter(MainActivity.this);
//////                            mainListView.setAdapter(adapter);
////                        }
////                        break;
//                }
//            }
//        };
//        rb1.setOnClickListener(clicker);
//        rb2.setOnClickListener(clicker);

        Button btnDeleteLast = (Button) findViewById(R.id.button3);
        btnDeleteLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
                for (int i = 0; i < equipments.size(); i++){
                    if(i == equipments.size() - 1){
                        equipments.remove(equipments.get(i));
                    }
                }
                Toast.makeText(MainActivity.this, "Last element is delete", Toast.LENGTH_SHORT).show();
                writeFile();
                mainListView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<Equipment> adapter = new EquipmentAdapter(MainActivity.this);
                mainListView.setAdapter(adapter);
            }
        });
        Button btnDeleteByNumber = (Button) findViewById(R.id.button4);
        btnDeleteByNumber.setOnClickListener(new View.OnClickListener() {
            EditText textNumber = (EditText) findViewById(R.id.editTextNumber);
            @Override
            public void onClick(View v) {
                readFile();
                for (int i = 0; i < equipments.size(); i++){
                    if(i == Integer.parseInt(String.valueOf(textNumber.getText())) - 1){
                        equipments.remove(equipments.get(i));
                    }
                }
                Toast.makeText(MainActivity.this, "Object with number " + textNumber.getText() +" delete" , Toast.LENGTH_SHORT).show();
                writeFile();
                mainListView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<Equipment> adapter = new EquipmentAdapter(MainActivity.this);
                mainListView.setAdapter(adapter);
            }
        });

        Button btnRead = (Button) findViewById(R.id.button5);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
                mainListView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<Equipment> adapter = new EquipmentAdapter(MainActivity.this);
                mainListView.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "Read file" , Toast.LENGTH_SHORT).show();
            }
        });
        Button btnFindName = (Button) findViewById(R.id.button2);
        btnFindName.setOnClickListener(new View.OnClickListener() {
            EditText textName = (EditText) findViewById(R.id.editTextTextPersonName);
            @Override
            public void onClick(View v) {
                readFile();
                ArrayList<Equipment> list = new ArrayList<Equipment>();
                for(Equipment equipmentItem : equipments){
                    if(equipmentItem.name.equals(textName.getText().toString())){
                        list.add(equipmentItem);
                    }
                }
                Toast.makeText(MainActivity.this, "Object with name " + textName.getText() +" is find!!!" , Toast.LENGTH_SHORT).show();
                equipments = list;
                mainListView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<Equipment> adapter = new EquipmentAdapter(MainActivity.this);
                mainListView.setAdapter(adapter);
            }
        });
    }

    public void readFile(){
        try {
            FileInputStream fileInput = openFileInput("example.txt");
            try {
                ObjectInputStream inputStream = new ObjectInputStream(fileInput);
                equipments = (ArrayList<Equipment>) inputStream.readObject();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeFile() {
        try {
            FileOutputStream fileOutput = openFileOutput("example.txt", MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileOutput);
            objectStream.writeObject(equipments);
            fileOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<Equipment> adapter = new EquipmentAdapter(this);
        mainListView.setAdapter(adapter);
    }

    private class EquipmentAdapter extends ArrayAdapter<Equipment> {

        public EquipmentAdapter(Context context) {
            super(context, R.layout.base_adapter, equipments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Equipment equipment = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.base_adapter, null);
            }
            ((TextView) convertView.findViewById(R.id.textView))
                    .setText(equipment.name);
            ((TextView) convertView.findViewById(R.id.textView2))
                    .setText(String.valueOf(equipment.issued));
            ((TextView) convertView.findViewById(R.id.textView3))
                    .setText(String.valueOf(equipment.date));
            ((TextView) convertView.findViewById(R.id.textView4))
                    .setText(String.valueOf(equipment.recipient));
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public static class Equipment implements Serializable {
        private String name, recipient, date;
        private Boolean issued;

        public Equipment(){}
        public Equipment(String n){this.name = n;}
        public Equipment(String n, String r){this.name = n;this.recipient = r;}
        public Equipment(String n, String r, String d){this.name = n;this.recipient = r;this.date = d;}
        public Equipment(String n, String r, String d, Boolean i){this.name = n;this.recipient = r;this.date = d;this.issued = i;}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Boolean getIssued() {
            return this.issued;
        }

        public void setIssued(Boolean issued) {
            this.issued = issued;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}