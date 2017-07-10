package com.fei.root.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fei.processor.annotation.Router;

@Router(action = "moduleFragment", module = "module")
public class ModuleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.startActivityForResult(null, 0);

        return super.onCreateView(inflater, container, savedInstanceState);


    }
}
