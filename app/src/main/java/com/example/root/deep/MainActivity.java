package com.example.root.deep;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import com.example.root.deep.bubbleview.BubbleLayout;
import com.example.root.deep.bubbleview.BubbleView;

public class MainActivity extends AppCompatActivity {

    private static String[] labels = {"a23456781234567989812345678",  "b23456781234567812345678", "c234567812345678dfdf12","d2345dfdf678123", "e23453467dfdf8123", "f2345678134323"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BubbleLayout layout = (BubbleLayout) findViewById(R.id.bubble_layout);
        for (String label : labels) {
            BubbleView bubbleView = new BubbleView(this);
            bubbleView.setText(label);
            bubbleView.setGravity(Gravity.CENTER);
            bubbleView.setPadding(10, 10, 10, 10);
            bubbleView.setTextColor(Color.parseColor("#000000"));
            layout.addViewSortByWidth(bubbleView);
        }

    }
}
