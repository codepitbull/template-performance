package de.codepitbull.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmader on 22.05.15.
 */
public class MustacheTest {
    @Test
    public void testMustache() throws Exception {
        int counter = 0;
        Map<String, Object> values = new HashMap<>();
        values.put("val1", "value1");
        values.put("val2", "value2");
        values.put("val3", counter);
        MustacheFactory mf = new DefaultMustacheFactory(s -> new StringReader("$val1 and $val2 and $val3"));
        Mustache mustache = mf.compile("template.mustache");

        long start = System.currentTimeMillis();
        for (int count = 0; count < 10_000_000; count++) {
            counter++;
            values.put("val3", counter);
            StringWriter sw = new StringWriter();
            mustache.execute(sw, values).flush();
            if(sw.toString().length()<0)
                throw new RuntimeException();
        }
        long stop = System.currentTimeMillis();
        System.out.println(MustacheTest.class.getSimpleName()+" elapsed " + (stop - start));
    }
}
