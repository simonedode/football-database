package it.unibo.database.utils;

import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

public final class Utils {
    private Utils() {}
    public static Optional<java.util.Date> convertStringDateToDate(final String date){
        final String[] birthSplit = date.split("/");
        return buildDate(Integer.parseInt(birthSplit[0]), Integer.parseInt(birthSplit[1]), Integer.parseInt(birthSplit[2]));
    }

    public static Optional<java.util.Date> buildDate(final int day, final int month, final int year) {
        try {
            final String dateFormatString = "dd/MM/yyyy";
            final String dateString = day + "/" + month + "/" + year;
            final java.util.Date date = new SimpleDateFormat(dateFormatString, Locale.ITALIAN).parse(dateString);
            return Optional.of(date);
        } catch (final ParseException e) {
            return Optional.empty();
        }
    }
}
