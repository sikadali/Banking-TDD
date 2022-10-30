package model;

import com.sikadali.banking.Clock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockShould {

    @Test
    public void returns_today_date_in_dd_MM_yyyy_format (){
        Clock clock = new TestableClock();
        String date = clock.todayAsString();
        assertThat(date).isEqualTo("24.04.2015");
    }

    private static class TestableClock extends Clock {
        @Override
        protected LocalDate today() {
            return LocalDate.of(2015, 4, 24);
        }
    }
}
