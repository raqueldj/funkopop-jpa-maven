package io.robusta.funko.annotations;

import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Raquel on 05/04/2017.
 */
public class AnnotationApplication {
    Universe dc = new Universe("DC");
    Universe starWars = new Universe("Star Wars");

    @Fast(speed = 45)
    FunkoPop flash = new FunkoPop("Flash", dc);

    @Fast(speed = 30)
    FunkoPop superman = new FunkoPop("Superman", dc);

    @Fast("Batmobil")
    FunkoPop batman = new FunkoPop("Batman", dc);

    @Slow(dangerous = false)
    FunkoPop jabba = new FunkoPop("Jabba", starWars);


    public static void main(String[] args) {
        new AnnotationApplication().init();
    }

    public void init() {
        List<FunkoPop> pops = new ArrayList<>();
        Collections.addAll(pops, batman, flash, superman, jabba);

        Field[] fields = AnnotationApplication.class.getDeclaredFields();

        List<Field> fpFields = new ArrayList<>();
        for (Field f : fields) {
            if (f.getDeclaredAnnotation(Fast.class) != null) {
                System.out.println(f);
                fpFields.add(f);
            }
        }

        Comparator<Field> comparatorFields = new Comparator<Field>() {
            @Override
            public int compare(Field f1, Field f2) {
                int v1 = f1.getDeclaredAnnotation(Fast.class).speed();
                int v2 = f2.getDeclaredAnnotation(Fast.class).speed();
                return Integer.valueOf(v1).compareTo(v2);
            }
        };

        Collections.sort(fpFields, comparatorFields);
        System.out.println(fpFields);
    }
}
