package com.mur.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateFormatter implements Formatter<Date> {
    
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateFormatter() {
        super();
    }

    @Override
    public String print(Date object, Locale locale) {
        return ymdHms.format(object);
    }


    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        try {
            return ymdHms.parse(text);
        } catch (ParseException e) {
            return ymd.parse(text);
        }
    }

}
