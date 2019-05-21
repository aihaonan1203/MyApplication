package com.gaotai.baselib.activity;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * 基础Activity 
 */
public class BaseFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
