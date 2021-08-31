package me.astero.asterohealth.util;

import android.content.Context;

public class Util {


    public static int getId(Context context, String name, String def)
    {


        return context.getResources().getIdentifier(name, def, context.getPackageName());
    }
}
