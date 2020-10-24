package id.ac.unud1805551038.tugas1sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editNama,editJenisBarang,editID;
    Button btnAddData;
    Button btnView;
    Button btnUpdate;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editNama = (EditText)findViewById(R.id.editText_nama);
        editJenisBarang = (EditText)findViewById(R.id.editText_jenisbarang);
        editID = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnView = (Button)findViewById(R.id.button_view);
        btnUpdate = (Button)findViewById(R.id.button_edit);
        btnDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        View();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(editID.getText().toString());
                        if(deleteRows > 0) {
                            Toast.makeText(MainActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Data Gagal Dihapus",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editID.getText().toString(),
                                editNama.getText().toString(),
                                editJenisBarang.getText().toString());
                        if(isUpdate == true) {
                            Toast.makeText(MainActivity.this, "Data Berhasil Diperbaharui", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Data Gagal Diperbaharui",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editNama.getText().toString(),
                            editJenisBarang.getText().toString() );
                        if(isInserted =true)
                            Toast.makeText(MainActivity.this,"Data Berhasil Dimasukkan",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Gagal Dimasukkan",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void View(){
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("id :" + res.getString(0) + "\n");
                            buffer.append("nama :" + res.getString(1) + "\n");
                            buffer.append("jenis_barang :" + res.getString(2) + "\n\n");
                        }

                        //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
