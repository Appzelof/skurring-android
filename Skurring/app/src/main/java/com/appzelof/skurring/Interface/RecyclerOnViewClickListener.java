package com.appzelof.skurring.Interface;

import android.view.View;

/**
 * Created by daniel on 05/12/2017.
 */

public interface RecyclerOnViewClickListener extends View.OnClickListener {
    void onClick(View view, int position);
}
