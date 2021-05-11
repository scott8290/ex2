package com.example.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.*;

public class MonthView extends AppCompatActivity {
    int year,month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.monthview);
        ArrayList days = new ArrayList();//날짜를 저장할 ArrayList days 선언

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                days); //어댑터 준비,배열 객체 이용, simple_list_item_1 리소스 사용

        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        month = intent.getIntExtra("month", -1);

        Calendar cal = Calendar.getInstance();
        if(year == -1 || month == -1) {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
        }

        cal.set(year,month,1);
        int startdate = cal.get(Calendar.DAY_OF_WEEK)-1;//시작일을 0으로

        for (int i = 0; i < startdate; i++) days.add("");//매달 1일인 요일보다 앞에 빈칸채우기
        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            days.add(i + 1);
        }

        TextView yearMonthTV = findViewById(R.id.year_month);
        yearMonthTV.setText(year + "년 "+ (month+1) + "월");

        Button prevBtn = findViewById(R.id.previous);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MonthView.class);
                if(month < 1){
                    intent.putExtra("year",year-1);
                    intent.putExtra("month",11);
                }
                else{
                    intent.putExtra("year",year);
                    intent.putExtra("month",(month-1));
                }
                startActivity(intent);
                finish();
            }
        });

        Button nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MonthView.class);
                if(month > 10){
                    intent.putExtra("year",year+1);
                    intent.putExtra("month",0);
                }
                else{
                    intent.putExtra("year",year);
                    intent.putExtra("month",month+1);
                }
                startActivity(intent);
                finish();
            }

        });

        GridView gridview = (GridView) findViewById(R.id.monthview);
        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position>=startdate)//매달 1일보다 앞에있는 그리드뷰 칸들은 반응하지 않도록
                    Toast.makeText(MonthView.this,
                            "" +year+ "."+(month+1)+"."+(position-startdate+1),//Toast 메세지 출력
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
}