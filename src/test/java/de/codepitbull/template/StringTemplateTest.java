package de.codepitbull.template;

import org.junit.Test;
import org.stringtemplate.v4.ST;

/**
 * Created by jmader on 22.05.15.
 */
public class StringTemplateTest {
    @Test
    public void testStringTemplate() {
        ST hello = new ST("<val1> and <val2> and <val3>");
        hello.add("val1", "value1");
        hello.add("val2", "value2");
        hello.add("val3", "value3");
        long start = System.currentTimeMillis();
        for (int count = 0; count < 10_000_000; count++) {
            if(hello.render().length()<0)
                throw new RuntimeException();
        }
        long stop = System.currentTimeMillis();
        System.out.println(StringTemplateTest.class.getSimpleName()+" elapsed " + (stop - start));
    }
}
