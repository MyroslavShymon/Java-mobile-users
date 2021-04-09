package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.example.myapplication.MainActivity.equipments;

public class MainActivity2 extends AppCompatActivity {
    EditText eName, eRecipient, eDate, eIssued;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        eName = (EditText) findViewById(R.id.editTextTextMultiLine);
        eRecipient = (EditText) findViewById(R.id.editTextTextMultiLine2);
        eDate = (EditText) findViewById(R.id.editTextTextMultiLine3);
        eIssued = (EditText) findViewById(R.id.editTextTextMultiLine4);

        Button btn = (Button)  findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Equipment equipment = new MainActivity.Equipment(
                        eName.getText().toString(),
                        eRecipient.getText().toString(),
                        eDate.getText().toString(),
                        Boolean.parseBoolean(eIssued.getText().toString())
                );
                equipments.add(equipment);
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
        });

        Button btnChange = (Button)  findViewById(R.id.button6);
        btnChange.setOnClickListener(new View.OnClickListener() {
            EditText textNumber2 = (EditText) findViewById(R.id.editTextNumber2);
            @Override
            public void onClick(View v) {
                for (int i = 0; i < equipments.size(); i++){
                    if(i == Integer.parseInt(String.valueOf(textNumber2.getText())) - 1){
                        MainActivity.Equipment equipment = new MainActivity.Equipment(
                                eName.getText().toString() != "" ? eName.getText().toString() : equipments.get(i).getName(),
                                eRecipient.getText().toString() != "" ?  eRecipient.getText().toString() : equipments.get(i).getRecipient(),
                                eDate.getText().toString() != "" ? eDate.getText().toString() : equipments.get(i).getDate(),
                                eIssued.getText().toString() != "" ? Boolean.parseBoolean(eIssued.getText().toString()) : equipments.get(i).getIssued()
                        );
                        equipments.remove(equipments.get(i));
                        equipments.add(equipment);
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
                }
            }
        });
    }
}
