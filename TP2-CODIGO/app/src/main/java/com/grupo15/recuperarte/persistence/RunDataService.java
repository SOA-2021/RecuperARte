package com.grupo15.recuperarte.persistence;

import android.content.Context;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RunDataService {
    @NonNull
    private final Context context;

    public RunDataService(@NonNull Context c) { context = c; }

    public static class MtPerDay {
        private final LocalDate date;
        private final double meters;

        public MtPerDay(double meters) {
            this.date = LocalDate.now();
            this.meters = meters;
        }

        public MtPerDay(Map.Entry<LocalDate, Double> entry) {
            this.date = entry.getKey();
            this.meters = entry.getValue();
        }
        public LocalDate getDate() { return date; }
        public double getMeters() { return meters; }
    }

    public static class TimeRunning {
        private final DayOfWeek day;
        private final LocalTime time;

        public TimeRunning(DayOfWeek day, LocalTime time) {
            this.day = day;
            this.time = time;
        }
        public DayOfWeek getDay() { return day; }
        public LocalTime getTime() { return time; }
    }

    /**
     * Devuelve los metros recorridos *hoy*
     */
    public MtPerDay getMtToday() {
        List<MtPerDay> l = listMtPerDay();
        return l.stream()
                .filter(m -> m.getDate().equals(LocalDate.now()))
                .findFirst()
                .orElse(new MtPerDay(0));
    }

    /**
     * Devuelve datos de la primera estadistica: metros recorridos por dia.
     */
    public List<MtPerDay> listMtPerDay() {
        RunDataDAO dao = new RunDataDAO(this.context);
        List<RunData> data = dao.listAll();

        Map<LocalDate, Double> mtPerDayMap = data.stream().collect(Collectors.groupingBy(
           r -> r.getDate().toLocalDate(),
           Collectors.summingDouble(RunData::getMeters)
        ));

        List<MtPerDay> l = new ArrayList<>();
        for ( Map.Entry<LocalDate, Double> e : mtPerDayMap.entrySet() ) {
            l.add(new MtPerDay(e));
        }
        return l;
    }

    /**
     * Devuelve datos de la segunda estadistica: tiempo que pasa corriendo por dia de la semana.
     */
    public List<TimeRunning> listTimeRunning() {
        /* Creo el mapa agrupador de dias-de-la-semana/horario */
        Map<DayOfWeek, SortedSet<LocalTime>> groupMap = new HashMap<>();
        for ( DayOfWeek d : DayOfWeek.values() ) {
            groupMap.put(d, new TreeSet<>((t1, t2) -> {
                if ( t1.getHour() != t2.getHour() )
                    return t1.getHour() - t2.getHour();
                if ( t1.getMinute() != t2.getMinute() )
                    return t1.getMinute() - t2.getMinute();

                return 0;
            }));
        }

        /* Mapeo los timestamps a dia-de-la-semana/horario */
        RunDataDAO dao = new RunDataDAO(this.context);
        List<LocalDateTime> runningTimestamps = dao.listRunningHours();
        for ( LocalDateTime dt: runningTimestamps ) {
            DayOfWeek day = dt.getDayOfWeek();
            LocalTime time = dt.toLocalTime();
            groupMap.get(day).add(time);
        }

        /* Paso el mapa agrupador a una lista de TimeRunning */
        List<TimeRunning> l = new ArrayList<>();
        for ( Map.Entry<DayOfWeek, SortedSet<LocalTime>> e: groupMap.entrySet() ) {
            SortedSet<LocalTime> set = e.getValue();
            for ( LocalTime t: set ) {
                l.add(new TimeRunning(e.getKey(), t.truncatedTo(ChronoUnit.MINUTES)));
            }
        }

        return l;
    }
}
