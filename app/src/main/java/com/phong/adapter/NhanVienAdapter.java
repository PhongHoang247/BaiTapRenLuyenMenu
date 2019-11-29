package com.phong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phong.baitaprenluyenmenu.R;
import com.phong.model.NhanVien;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    Activity context;
    int resource;
    public NhanVienAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Nạp item.xml lên giao diện:
        View view = this.context.getLayoutInflater().inflate(this.resource, null);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.imgHinh);
        TextView txtMa = (TextView) view.findViewById(R.id.txtMa);
        TextView txtTen = (TextView) view.findViewById(R.id.txtTen);
        //Lấy nhân viên:
        NhanVien nv = getItem(position);
        //Show lên:
        txtMa.setText(nv.getMa());
        txtTen.setText(nv.getTen());
        if (nv.isLaNu())
        {
            imgHinh.setImageResource(R.drawable.woman);
        }
        else
        {
            imgHinh.setImageResource(R.drawable.man);
        }
        return view;
    }
}
