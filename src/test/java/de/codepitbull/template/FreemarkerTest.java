package de.codepitbull.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_22;

/**
 * Created by jmader on 22.05.15.
 */
public class FreemarkerTest {
    @Test
    public void testFreemarker() throws Exception{
        int counter = 0;
        String templateStr="$val1 and $val2 and $val3";
        Template t = new Template("name", new StringReader(templateStr),
                new Configuration(VERSION_2_3_22));
        Map<String, Object> values = new HashMap<>();
        values.put("val1", "value1");
        values.put("val2", "value2");
        values.put("val3", counter);

        long start = System.currentTimeMillis();
        for (int count = 0; count < 10_000_000; count++) {
            counter++;
            values.put("val3", counter);
            StringWriter sw = new StringWriter();
            t.process(values, sw);
            if(sw.toString().length()<0)
                throw new RuntimeException();
        }
        long stop = System.currentTimeMillis();
        System.out.println(FreemarkerTest.class.getSimpleName() + " elapsed " + (stop - start));
    }
}
