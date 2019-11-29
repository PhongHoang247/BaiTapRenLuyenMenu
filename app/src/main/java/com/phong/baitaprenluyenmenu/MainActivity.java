package com.phong.baitaprenluyenmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;

import com.phong.adapter.NhanVienAdapter;
import com.phong.model.NhanVien;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvNhanVien;
    NhanVienAdapter adapterNhanVien;
    NhanVien chonNhanVien = null;
    ArrayList<NhanVien> dsNguon = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chonNhanVien = adapterNhanVien.getItem(i);
            }
        });
        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                chonNhanVien = adapterNhanVien.getItem(i);
                return false;
            }
        });
    }

    private void addControls() {
        lvNhanVien = findViewById(R.id.lvNhanVien);
        adapterNhanVien = new NhanVienAdapter(MainActivity.this, R.layout.item);
        lvNhanVien.setAdapter(adapterNhanVien);
        //Đăng ký Context Menu cho List View:
        registerForContextMenu(lvNhanVien);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //Nạp context_menu lên:
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuEdit)
        {
            hienThiManHinhEdit();
        }
        else if (item.getItemId() == R.id.menuRemove)
        {
            xuLyXoa();
        }
        return super.onContextItemSelected(item);
    }

    private void xuLyXoa() {
        adapterNhanVien.remove(chonNhanVien);
    }

    private void hienThiManHinhEdit() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.detail_layout);

        final EditText edtMa = (EditText) dialog.findViewById(R.id.edtMa);
        final EditText edtTen = (EditText) dialog.findViewById(R.id.edtTen);
        RadioButton radNam = (RadioButton) dialog.findViewById(R.id.radNam);
        final RadioButton radNu = (RadioButton) dialog.findViewById(R.id.radNu);
        Button btnLuu = (Button) dialog.findViewById(R.id.btnLuu);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNhanVien.setMa(edtMa.getText().toString());
                chonNhanVien.setTen(edtTen.getText().toString());
                chonNhanVien.setLaNu(radNu.isChecked());

                adapterNhanVien.notifyDataSetChanged();
                dialog.dismiss();
                //Lưu lại danh sách:
                dsNguon.clear();
                for (int i = 0; i < adapterNhanVien.getCount(); i++)
                {
                    dsNguon.add(adapterNhanVien.getItem(i));
                }
            }
        });
        edtMa.setText(chonNhanVien.getMa());
        edtTen.setText(chonNhanVien.getTen());
        if (chonNhanVien.isLaNu())
        {
            radNu.setChecked(true);
        }
        else
        {
            radNam.setChecked(true);
        }
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Nạp option_menu lên:
        getMenuInflater().inflate(R.menu.option_menu, menu);
        //Menu Search
        MenuItem menuSearch = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Không đc dùng vì dữ liệu là các đối tượng:
                //adapterNhanVien.getFilter().filter(s);
                //Nhập rỗng (khoảng trắng) thì hiển thị lại toàn bộ ds:
                if (s.isEmpty())
                {
                    adapterNhanVien.clear();
                    adapterNhanVien.addAll(dsNguon);
                }
                else {
                ArrayList<NhanVien> dsTim = new ArrayList<NhanVien>();
                for (NhanVien nv : dsNguon) {
                    if (nv.getMa().contains(s) || nv.getTen().contains(s)) {
                        dsTim.add(nv);
                    }
                    //Gán:
                    adapterNhanVien.clear();
                    adapterNhanVien.addAll(dsTim);
                }
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuNew:
                hienThiManHinhNhapNV();
                break;
            case R.id.menuHelp:
                break;
            case R.id.menuAbout:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hienThiManHinhNhapNV() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.detail_layout);

        final EditText edtMa = (EditText) dialog.findViewById(R.id.edtMa);
        final EditText edtTen = (EditText) dialog.findViewById(R.id.edtTen);
        RadioButton radNam = (RadioButton) dialog.findViewById(R.id.radNam);
        final RadioButton radNu = (RadioButton) dialog.findViewById(R.id.radNu);
        Button btnLuu = (Button) dialog.findViewById(R.id.btnLuu);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien nv = new NhanVien();
                nv.setMa(edtMa.getText().toString());
                nv.setTen(edtTen.getText().toString());
                nv.setLaNu(radNu.isChecked());
                //Đẩy nv vào adapter:
                adapterNhanVien.add(nv);
                dialog.dismiss();
                //Lưu lại danh sách:
                dsNguon.clear();
                for (int i = 0; i < adapterNhanVien.getCount(); i++)
                {
                    dsNguon.add(adapterNhanVien.getItem(i));
                }
            }
        });
        dialog.show();
    }
}
