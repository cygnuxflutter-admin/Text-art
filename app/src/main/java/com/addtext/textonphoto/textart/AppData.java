package com.addtext.textonphoto.textart;

import java.util.ArrayList;
import java.util.HashMap;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Category;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Quotes;


public class AppData {
    private static AppData sAppData;
    public ArrayList<TART_Category> categoryArrayList = new ArrayList<>();
    public HashMap<String, ArrayList<TART_Quotes>> quote_map = new HashMap<>();
    public ArrayList<TART_Quotes> favouriteArrayList = new ArrayList<>();
    public ArrayList<TART_Quotes> quoteDay = new ArrayList<>();

    private AppData() {
    }

    public static AppData getInstance() {
        if (sAppData == null) {
            sAppData = new AppData();
        }
        return sAppData;
    }
}
