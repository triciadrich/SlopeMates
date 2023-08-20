package com.example.slopemates.Services;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
public class DateUtility {


        private static SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        private static SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        public static Date parseDate(@NotNull LocalDate dateString) throws Exception {
            return inputFormat.parse(String.valueOf(dateString));
        }

        public static String formatDate(Date date) {
            return outputFormat.format(date);
        }
    }


